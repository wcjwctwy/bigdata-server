package cn.lsmsp.bigdata.protal.controller.ruler;

import cn.lsmsp.bigdata.check.policy.dao.EventRulerTransDao;
import cn.lsmsp.bigdata.check.policy.pojo.TbEventRule;
import cn.lsmsp.bigdata.check.policy.pojo.TbEventRulerMain;
import cn.lsmsp.bigdata.check.policy.pojo.TranslationRuler;
import cn.lsmsp.check.policy.service.EventRulerTransService;
import cn.lsmsp.check.policy.service.RulerService;
import cn.lsmsp.common.exception.BigException;
import cn.lsmsp.common.pojo.BigdataResult;
import cn.lsmsp.common.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class RulerValidateController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RulerValidateController.class);
    @Autowired
    private RulerService rulerService;

    @Autowired
    private EventRulerTransDao eventRulerTransDao;

    @Autowired
    private EventRulerTransService eventRulerTransService;

    @RequestMapping("/ruler/validate")
    public String page(Model model){
        Map<String, Map<Integer,String>> rulerClasses = rulerService.getRulerClass();
        model.addAttribute("rulerClasses",rulerClasses);
        return "ruler/validate";
    }

    @RequestMapping("/ruler/content")
    @ResponseBody
    public BigdataResult getCondition(Integer rulerId){
        LOGGER.debug("rulerId: "+rulerId);
        TbEventRulerMain ruler = rulerService.getRuler(rulerId);
        Integer pluginId = ruler.getPluginCode();
        String translations = JsonUtils.objectToJson(eventRulerTransService.getEventRulerTrans(pluginId));
        return BigdataResult.build(200,translations,ruler);
//        return BigdataResult.build(200,ruler.getCondition(),ruler.getContent());
    }

    @RequestMapping(value="/ruler/regex",method = RequestMethod.POST)
    @ResponseBody
    public BigdataResult saveRegex(Integer rulerId,String condition){
        TbEventRulerMain tbEventRulerMain = new TbEventRulerMain();
        tbEventRulerMain.setId(rulerId);
        tbEventRulerMain.setRegex(condition);
        tbEventRulerMain.setUpdatedTime(new Date());
        try {
            rulerService.updateRulerById(tbEventRulerMain);
        }catch (Exception e){
            BigdataResult.build(406,e.getMessage());
        }
        return BigdataResult.ok();
    }

    @RequestMapping(value="/ruler/content",method = RequestMethod.POST)
    @ResponseBody
    public BigdataResult saveContent(Integer rulerId,String rulerContent){
        TbEventRulerMain tbEventRulerMain = new TbEventRulerMain();
        tbEventRulerMain.setId(rulerId);
        tbEventRulerMain.setRulerContent(rulerContent);
        tbEventRulerMain.setUpdatedTime(new Date());
        try {
            rulerService.updateRulerById(tbEventRulerMain);
        }catch (Exception e){
            BigdataResult.build(406,e.getMessage());
        }
        return BigdataResult.ok();
    }

    /**
     * 更新trans
     * @return
     */
    @RequestMapping(value="/ruler/translation",method = RequestMethod.PUT)
    @ResponseBody
    public BigdataResult updateTranslation(String translation,Integer rulerId){
        List<TranslationRuler> translationRuler = JsonUtils.jsonToList(translation, TranslationRuler.class);
        LOGGER.debug("TranslationRuler: "+translationRuler);
        try {
            translationRuler.forEach(tr->{
                if(StringUtils.isEmpty(tr.getValue())){
                    //如果tr中的value为空，
                    // 并且用plugin_id、event_id(key)、 大类、 子类数据库中查询不到
                    List<TbEventRule> eventRulerTrans = eventRulerTransService.getEventRulerTrans(tr, rulerId);
                    if(eventRulerTrans.get(1)==null){
                       //数据库中没有对应的trans  将现有的trans插入数据库
                        Integer maxEventId = eventRulerTransDao.getMaxEventId(rulerService.getRuler(rulerId).getPluginCode());
                        maxEventId = maxEventId==null? 1:++maxEventId;
                        TbEventRule tbEventRule = eventRulerTrans.get(0);
                        tbEventRule.setEventId(maxEventId);
                        eventRulerTransService.saveEventRulerTrans(tbEventRule);
                    }else{
                        //数据库中已经有了返回eventId
                        throw new BigException(eventRulerTrans.get(1).getRemark()+" 数据库中已经存在key: "+eventRulerTrans.get(1).getEventId());
                    }

                }else {
                    eventRulerTransService.updateEventRulerTrans(tr, rulerId);
                }
            });

        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return BigdataResult.build(407,e.getMessage());
        }
        return BigdataResult.ok();
    }

}
