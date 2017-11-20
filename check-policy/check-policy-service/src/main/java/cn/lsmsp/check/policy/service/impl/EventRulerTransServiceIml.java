package cn.lsmsp.check.policy.service.impl;

import cn.lsmsp.bigdata.check.policy.config.RulerCheckConfig;
import cn.lsmsp.bigdata.check.policy.dao.EventRulerMainDao;
import cn.lsmsp.bigdata.check.policy.dao.EventRulerTransDao;
import cn.lsmsp.bigdata.check.policy.pojo.XmlRuler;
import cn.lsmsp.bigdata.check.policy.pojo.TbEventRule;
import cn.lsmsp.bigdata.check.policy.pojo.TbEventRulerMain;
import cn.lsmsp.bigdata.check.policy.pojo.TranslationRuler;
import cn.lsmsp.bigdata.check.policy.utils.RulerUtils;
import cn.lsmsp.check.policy.service.EventRulerTransService;
import cn.lsmsp.check.policy.service.LsEventCategoryService;
import cn.lsmsp.check.policy.service.LsEventSubcategoryService;
import cn.lsmsp.check.policy.service.RulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EventRulerTransServiceIml implements EventRulerTransService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventRulerTransServiceIml.class);

    @Value("${RULER_PATH}")
    private String RULER_PATH;

    @Autowired
    private EventRulerTransDao eventRulerTransDao;

    @Autowired
    private EventRulerMainDao eventRulerMainDao;

    @Autowired
    private RulerService rulerService;

    @Autowired
    private LsEventSubcategoryService subcategoryService;

    @Autowired
    private LsEventCategoryService categoryService;

    @Override
    public List<TranslationRuler> getEventRulerTrans(Integer pluginId) {
        List<TbEventRule> eventRulerTrans = eventRulerTransDao.getEventRulerTrans(pluginId);
        List<TranslationRuler> translationRulers = RulerCheckConfig.transTranslationRuler(eventRulerTrans);
        return translationRulers;
    }

    @Override
    public void updateEventRulerTrans(TranslationRuler translationRuler, Integer rulerId) {
        TbEventRulerMain ruler = eventRulerMainDao.getRulerById(rulerId);
        Integer eventId = null;
        String value = translationRuler.getValue();
        try {

            eventId = Integer.valueOf(value);
        } catch (NumberFormatException e) {
            LOGGER.error(value + " can't parse to num!" + e.getMessage());
            return;
        }
        String eventCategory = translationRuler.getEventCategory();
        String eventCategoryTechnique = translationRuler.getEventCategoryTechnique();
        Integer pluginId = ruler.getPluginCode();
        String eventName = translationRuler.getEventName();
        String remark = translationRuler.getKey();
        Integer categoryId = categoryService.getCategoryIdByName(eventCategory);
        LOGGER.debug("categoryId: " + categoryId);
        Integer subcategoryId = subcategoryService.getEventSubcategoryByNameAndCategoryId(eventCategoryTechnique, categoryId);
        LOGGER.debug("subcategoryId: " + subcategoryId);
        //创建tbEventRule
        TbEventRule tbEventRule = new TbEventRule();
        tbEventRule.setCategoryId(categoryId);
        tbEventRule.setEventCategory(eventCategory);
        tbEventRule.setEventCategoryTechnique(eventCategoryTechnique);
        tbEventRule.setRemark(remark);
        tbEventRule.setEventName(eventName);
        tbEventRule.setSubCategoryId(subcategoryId);
        eventRulerTransDao.updateEventRulerTrans(tbEventRule, "event_id=" + eventId + " and plugin_id = " + pluginId);
    }

    @Override
    public void updateTranslationsRemark() {
        RulerUtils rulerDao = new RulerUtils();
        Map<String, XmlRuler> xmlRulers = rulerDao.loadRuler(RULER_PATH);
        xmlRulers.forEach((k, v) -> {
            Integer pluginId = v.getPluginCode();
            v.getTranslation().forEach(t -> {
                String remark = t.getKey();
                try {
                    Integer eventId = Integer.valueOf(t.getValue());
                    eventRulerTransDao.updateEventRulerTransRemark(remark, pluginId, eventId);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    LOGGER.warn(e.getMessage() + "k:" + k);
                }

            });
        });
    }

    @Override
    public void saveEventRulerTrans(TbEventRule tbEventRule) {
        String eventCategory = tbEventRule.getEventCategory();
        String eventCategoryTechnique = tbEventRule.getEventCategoryTechnique();
        Integer categoryId = categoryService.getCategoryIdByName(eventCategory);
        LOGGER.debug("categoryId: " + categoryId);
        Integer subcategoryId = subcategoryService.getEventSubcategoryByNameAndCategoryId(eventCategoryTechnique, categoryId);
        LOGGER.debug("subcategoryId: " + subcategoryId);
        tbEventRule.setSubCategoryId(subcategoryId);
        tbEventRule.setCategoryId(categoryId);
        eventRulerTransDao.saveEventRulerTrans(tbEventRule);
    }

    @Override
    public List<TbEventRule> getEventRulerTrans(TranslationRuler translationRuler, Integer rulerId) {
        List<TbEventRule> tbEventRules = new ArrayList<>();
        TbEventRule tbEventRule = new TbEventRule();
        tbEventRule.setEventCategory(translationRuler.getEventCategory());
        tbEventRule.setEventName(translationRuler.getEventName());
        tbEventRule.setEventCategoryTechnique(translationRuler.getEventCategoryTechnique());
        TbEventRulerMain ruler = rulerService.getRuler(rulerId);
        tbEventRule.setRemark(translationRuler.getKey());
        tbEventRule.setPluginId(ruler.getPluginCode());
        tbEventRules.add(tbEventRule);
        TbEventRule eventRule = eventRulerTransDao.getEventRule(tbEventRule);
        tbEventRules.add(eventRule);
        return tbEventRules;
    }
}
