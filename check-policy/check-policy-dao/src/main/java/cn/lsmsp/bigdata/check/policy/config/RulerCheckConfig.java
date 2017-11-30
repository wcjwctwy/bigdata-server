package cn.lsmsp.bigdata.check.policy.config;

import cn.lsmsp.bigdata.check.policy.dao.EventRulerMainDao;
import cn.lsmsp.bigdata.check.policy.dao.EventRulerTransDao;
import cn.lsmsp.bigdata.check.policy.utils.RulerUtils;
import cn.lsmsp.bigdata.check.policy.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
    @PropertySource("classpath:/config/resource-check.properties")
public class RulerCheckConfig {

    @Value("${RULER_PATH}")
    private String RULER_PATH;

    @Autowired
    private EventRulerMainDao eventRulerDao;

    @Autowired
    private EventRulerTransDao eventRulerTransDao;

    public EventRulerMainDao getEventRulerDao() {
        return eventRulerDao;
    }

    public void setEventRulerDao(EventRulerMainDao eventRulerDao) {
        this.eventRulerDao = eventRulerDao;
    }

//    @Bean(name = "DBRulers")
//    public Map<String, RulerConfig> getStringRulerConfigMapFromDb(){
//        Map<String, RulerConfig> stringRulerConfigMap = new HashMap<>();
//        List<TbEventRulerMain> allRulers = eventRulerDao.getAllRulers();
//        allRulers.forEach(r->{
//
//            r= TbEventRulerCodeC.decode(r);
//            String key = r.getGroupName();
//            if(stringRulerConfigMap.containsKey(key)){
//                //key在map中
//                RulerConfig rulerConfig = stringRulerConfigMap.get(key);
//                Ruler ruler = new Ruler();
//                ruler.setCondition(r.getRegex());
//                HashMap<String ,String> hashMap = JsonUtils.jsonToPojo(r.getRulerContent(), HashMap.class);
//                ruler.setContent(hashMap);
//                ruler.setRulerId(r.getId());
//                rulerConfig.getRulers().add(ruler);
//            }else {
//                //key不在map中
//                RulerConfig rulerConfig = new RulerConfig();
//                stringRulerConfigMap.put(key,rulerConfig);
//                rulerConfig.setPluginCode(r.getPluginCode());
//                rulerConfig.setTranslation(getEventRulerTrans(r.getPluginCode()));
//                List<Ruler> rulers = new ArrayList<>();
//                rulerConfig.setRulers(rulers);
//                Ruler ruler = new Ruler();
//                ruler.setCondition(r.getRegex());
//                HashMap<String ,String> hashMap = JsonUtils.jsonToPojo(r.getRulerContent(), HashMap.class);
//                ruler.setContent(hashMap);
//                ruler.setRulerId(r.getId());
//                rulers.add(ruler);
//            }
//        });
//        return stringRulerConfigMap;
//    }


    public List<TranslationRuler> getEventRulerTrans(Integer pluginId) {
        List<TbEventRule> eventRulerTrans = eventRulerTransDao.getEventRulerTrans(pluginId);
        List<TranslationRuler> translationRulers = transTranslationRuler(eventRulerTrans);
        return translationRulers;
    }

    public static List<TranslationRuler> transTranslationRuler(List<TbEventRule> eventRulerTrans){
        List<TranslationRuler> translationRulers = new ArrayList<>();
        eventRulerTrans.forEach(tr->{
            String remark = tr.getRemark();
            if(!StringUtils.isEmpty(remark)) {
                TranslationRuler translationRuler = new TranslationRuler();
                translationRuler.setEventCategory(tr.getEventCategory());
                translationRuler.setEventCategoryTechnique(tr.getEventCategoryTechnique());
                translationRuler.setEventName(tr.getEventName());
                translationRuler.setValue(tr.getEventId().toString());
                translationRuler.setKey(tr.getRemark());
                translationRulers.add(translationRuler);
            }
        });
        return translationRulers;
    }
}
