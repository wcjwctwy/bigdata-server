package cn.lsmsp.check.policy.service;

import cn.lsmsp.bigdata.check.policy.pojo.TbEventRule;
import cn.lsmsp.bigdata.check.policy.pojo.TranslationRuler;

import java.util.List;

public interface EventRulerTransService {
   List<TranslationRuler> getEventRulerTrans(Integer pluginId);
   void updateEventRulerTrans(TranslationRuler translationRuler,Integer rulerId);
   void updateTranslationsRemark();
   void saveEventRulerTrans(TbEventRule tbEventRule);
   List<TbEventRule> getEventRulerTrans(TranslationRuler translationRuler,Integer rulerId);

}
