package cn.lsmsp.bigdata.protal.controller.ruler;

import cn.lsmsp.bigdata.check.policy.pojo.TbEventRulerMain;
import cn.lsmsp.bigdata.check.policy.pojo.TbRulerGroup;
import cn.lsmsp.check.policy.service.EventRulerTransService;
import cn.lsmsp.check.policy.service.RulerDataTranslateService;
import cn.lsmsp.check.policy.service.RulerGroupService;
import cn.lsmsp.check.policy.service.RulerService;
import cn.lsmsp.common.pojo.BigdataResult;
import cn.lsmsp.common.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class RulerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RulerController.class);

    @Autowired
    private RulerService rulerService;

    @Autowired
    private RulerGroupService rulerGroupService;

    @Autowired
    private EventRulerTransService eventRulerTransService;

    @Autowired
    private RulerDataTranslateService rulerDataTranslateService;

    @Value("${XML_RULER_PATH}")
    private String XML_RULER_PATH;

    /**
     * 通过文件解析日志
     *
     * @param rawEvent
     * @return
     */
    @RequestMapping("/ruler/file/analyse")
    @ResponseBody
    public BigdataResult analyseLogByFile(String rawEvent, Integer rulerId) {
        Map<String, String> map = null;
        try {
            map = rulerService.analyseLog(rawEvent, rulerId);
        } catch (Exception e) {
            e.printStackTrace();
            return BigdataResult.build(405, "解析失败原因：\n" + e.getMessage());
        }
        return BigdataResult.ok(map);
    }


    @RequestMapping("/ruler/analyse")
    @ResponseBody
    public BigdataResult analyseLog(String translation, String rulerContent, String rawEvent, String condition, Integer rulerId) {
        Map<String, String> map = null;
        LOGGER.debug("RulerController,analyseLog started.....");
        try {
            map = rulerService.analyseLogByUserCondition(translation, rulerContent, rawEvent, condition, rulerId);
        } catch (Exception e) {
            e.printStackTrace();
            return BigdataResult.build(405, "解析失败原因：\n" + e.getMessage());
        }
        return BigdataResult.ok(map);
    }

    @RequestMapping("/ruler/refresh")
    @ResponseBody
    public BigdataResult refreshRuler() {
        rulerService.refreshRuler();
        return BigdataResult.ok();
    }

    @RequestMapping("/ruler/import")
    @ResponseBody
    public BigdataResult importRuler(String password) {
        if (!password.equals("890610")) {
            return BigdataResult.build(403, "权限不足！");
        }
        try {
            rulerDataTranslateService.xmlData2DB(XML_RULER_PATH);
        } catch (Exception e) {
            e.printStackTrace();
            return BigdataResult.build(405, "导入数据失败！");
        }
        return BigdataResult.ok();
    }

    @RequestMapping("/ruler/trans/remark")
    @ResponseBody
    public BigdataResult updateRemark() {
        eventRulerTransService.updateTranslationsRemark();
        return BigdataResult.ok();
    }

    @RequestMapping("/page/ruler/ruler-add")
    public String addPage(Model model) {
        List<TbRulerGroup> allGroups = rulerGroupService.getAllGroups();
        model.addAttribute("allGroups",allGroups);
        return "ruler/ruler-add";
    }

    @RequestMapping(value = "/ruler",method = RequestMethod.POST)
    @ResponseBody
    public BigdataResult addRuler(TbEventRulerMain eventRulerMain) {
        TbRulerGroup groupByGroupName = rulerGroupService.getGroupByGroupName(eventRulerMain.getGroupName());
        eventRulerMain.setPluginCode(groupByGroupName.getPluginCode());
        Date date = new Date();
        eventRulerMain.setUpdatedTime(date);
        eventRulerMain.setCreatedTime(date);
        rulerService.saveOrUpdateRuler(eventRulerMain);
        return BigdataResult.ok();
    }

    @RequestMapping(value = "/ruler/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public BigdataResult delRuler(@PathVariable("id") Integer id) {
        if(id==null){
            throw new RuntimeException("删除id不可为空！");
        }
        rulerService.delRulerById(id);
        return BigdataResult.ok();
    }

    @RequestMapping(value = "/ruler/{id}",method = RequestMethod.GET)
    public RedirectView getRuler(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        if(id==null){
            throw new RuntimeException("删除id不可为空！");
        }
        RedirectView redirectView = new RedirectView("/page/ruler/ruler-add",true,false,false);
        TbEventRulerMain ruler = rulerService.getRuler(id);
        String rulerContent = ruler.getRulerContent();
        String prettyJson = JsonUtils.prettyJson(rulerContent);
        ruler.setRulerContent(prettyJson);
        redirectAttributes.addFlashAttribute("ruler",ruler);
        return redirectView;
    }


}
