package cn.lsmsp.check.policy.service;

import cn.lsmsp.bigdata.check.policy.pojo.XmlRuler;
import cn.lsmsp.bigdata.check.policy.pojo.TbEventRulerMain;

import java.util.List;
import java.util.Map;

public interface RulerService {
    String getCondition(Integer rulerId);
    Map<String,String> analyseLog(String rawEvent,String condition,Integer rulerId);
    Map<String,String> analyseLogByUserCondition(String translation, String rulerContent,String rawEvent,String condition,Integer rulerId);
    Map<String,String> analyseLog(String rawEvent,Integer rulerId);
    TbEventRulerMain getRuler(Integer rulerId);
    List<TbEventRulerMain> getAllRulers();
    void refreshRuler();
    Map<String, Map<Integer,String>> getRulerClass();
    void updateRulerById(TbEventRulerMain tbEventRulerMain);
    Integer getRulersCount();
    Map<String, XmlRuler> readRulersFromDb2RulerConfig();
    String exportXmlRuler(String... fileNames);
    String compressXmlFiles(String exportXmlRulerPath,boolean isDel);
    void saveOrUpdateRuler(TbEventRulerMain tbEventRuler);
    void delRulerById(Integer id);

}
