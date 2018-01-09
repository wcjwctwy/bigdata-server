package cn.lsmsp.check.policy.service.impl;

import cn.lsmsp.bigdata.check.policy.dao.EventRulerMainDao;
import cn.lsmsp.bigdata.check.policy.dao.RulerGroupDao;
import cn.lsmsp.bigdata.check.policy.pojo.*;
import cn.lsmsp.bigdata.check.policy.utils.RulerUtils;
import cn.lsmsp.bigdata.check.policy.utils.TbEventRulerCodeC;
import cn.lsmsp.check.policy.service.EventRulerTransService;
import cn.lsmsp.check.policy.service.RulerService;
import cn.lsmsp.check.policy.utils.AnalyseUtils;
import cn.lsmsp.common.utils.CompressUtils;
import cn.lsmsp.common.utils.JsonUtils;
import cn.lsmsp.common.utils.SqlCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class RulerServiceImpl implements RulerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RulerServiceImpl.class);

    @Autowired
    private EventRulerMainDao eventRulerDao;

    @Autowired
    private RulerGroupDao rulerGroupDao;

    @Autowired
    private EventRulerTransService eventRulerTransService;

    @Autowired
    EventRulerTransServiceIml eventRulerTransServiceIml;

    @Value("${RULER_TEMP_DOWNLOAD_PATH}")
    private String RULER_TEMP_DOWNLOAD_PATH;

    @Value("${RULER_EXPORT_PATH}")
    private String RULER_EXPORT_PATH;

    @Value("${RULER_PATH}")
    private String RULER_PATH;

    @Override
    public String getCondition(Integer rulerId) {
        TbEventRulerMain ruler = getRuler(rulerId);
        return ruler.getRegex();
    }

    @Override
    public TbEventRulerMain getRuler(Integer rulerId) {
        TbEventRulerMain rulerById = eventRulerDao.getRulerById(rulerId);
        TbEventRulerMain tbEventRuler = TbEventRulerCodeC.decode(rulerById);
        return tbEventRuler;
    }

    @Override
    public void refreshRuler() {


    }

    @Override
    public void updateRulerById(TbEventRulerMain tbEventRulerMain) {
        Integer id = tbEventRulerMain.getId();
        eventRulerDao.updateRuler(tbEventRulerMain, "id=" + id);
    }

    @Override
    public List<TbEventRulerMain> getAllRulers() {
        List<TbEventRulerMain> result = new ArrayList<>();
        List<TbEventRulerMain> allRulers = eventRulerDao.getAllRulers();
        allRulers.forEach(r -> {
            TbEventRulerMain tbEventRuler = TbEventRulerCodeC.decode(r);
            result.add(tbEventRuler);
        });
        return result;
    }


    @Override
    public Integer getRulersCount() {
        Integer rulerCount = eventRulerDao.getRulerCount();
        return rulerCount;
    }


    @Override
    public Map<String, XmlRuler> readRulersFromDb2RulerConfig() {
        Map<String, XmlRuler> stringRulerConfigMap = new HashMap<>();
        List<TbEventRulerMain> allRulers = eventRulerDao.getAllRulers();
        allRulers.forEach(r -> {
            if (r.getStatus() == 2) {
                r = TbEventRulerCodeC.decode(r);
                String key = r.getGroupName();
                if (stringRulerConfigMap.containsKey(key)) {
                    //key在map中
                    XmlRuler rulerConfig = stringRulerConfigMap.get(key);
                    Ruler ruler = new Ruler();
                    ruler.setCondition(r.getRegex());
                    HashMap<String, String> hashMap = JsonUtils.jsonToPojo(r.getRulerContent(), HashMap.class);
                    ruler.setContent(hashMap);
                    ruler.setRulerId(r.getId());
                    rulerConfig.getRulers().add(ruler);
                } else {
                    //key不在map中
                    XmlRuler rulerConfig = new XmlRuler();
                    stringRulerConfigMap.put(key, rulerConfig);
                    rulerConfig.setPluginCode(r.getPluginCode());
                    rulerConfig.setTranslation(eventRulerTransServiceIml.getEventRulerTrans(r.getPluginCode()));
                    List<Ruler> rulers = new ArrayList<>();
                    rulerConfig.setRulers(rulers);
                    Ruler ruler = new Ruler();
                    ruler.setCondition(r.getRegex());
                    HashMap<String, String> hashMap = JsonUtils.jsonToPojo(r.getRulerContent(), HashMap.class);
                    ruler.setContent(hashMap);
                    ruler.setRulerId(r.getId());
                    rulers.add(ruler);
                }
            }
        });
        return stringRulerConfigMap;
    }

    /**
     * 导出xml文件
     *
     * @param fileNames 导出的文件名多个文件名用','隔开
     *                  不传表示导出所有
     */
    @Override
    public String exportXmlRuler(String... fileNames) {
        //导出数据到xml文件中
        Map<String, XmlRuler> stringRulerConfigMap = readRulersFromDb2RulerConfig();
        //为每个线程创建一个目录
        String path = "/" + UUID.randomUUID().toString().replace("-", "");
        String xmlFilePath = RULER_EXPORT_PATH + path;
        LOGGER.debug("export path :" + xmlFilePath);

        if (fileNames == null || fileNames.length == 0) {
            LOGGER.debug("export all xml-files ");
            stringRulerConfigMap.forEach((k, v) -> {
                RulerUtils.exportRulersXmlFile(v, xmlFilePath, k);
            });
        } else {
            List<String> names = Arrays.asList(fileNames);
            LOGGER.debug("export files：" + names);
            stringRulerConfigMap.forEach((k, v) -> {
                if (names.contains(k)) {
                    RulerUtils.exportRulersXmlFile(v, xmlFilePath, k);
                }
            });
        }
        return xmlFilePath;
    }

    /**
     * 压缩文件
     *
     * @param exportXmlRulerPath
     * @param isDel              压缩完是否删除源文件
     * @return
     */
    @Override
    public String compressXmlFiles(String exportXmlRulerPath, boolean isDel) {
        //压缩xml文件夹
        //读取需要压缩的文件
        String tempFilename = "/" + UUID.randomUUID().toString();
        File f = new File(exportXmlRulerPath);
        File tempF = new File(RULER_TEMP_DOWNLOAD_PATH);
        if (!tempF.exists()) {
            boolean mkdirs = tempF.mkdirs();
            if (!mkdirs) {
                LOGGER.error("create path: " + RULER_TEMP_DOWNLOAD_PATH + " Failed!!");
            }
        }
        String targetFilePath = RULER_TEMP_DOWNLOAD_PATH + "/" + tempFilename;

        File target = new File(targetFilePath);
        CompressUtils.setTargetFile(target);
        CompressUtils.zipFiles(f);
        if (isDel) {
            LOGGER.debug("delete exportXmlRulerPath");
            Arrays.asList(f.listFiles()).forEach(file -> f.delete());
            f.delete();
        }
        return targetFilePath;
    }

    @Override
    public Map<String, Map<Integer, String>> getRulerClass() {
        Map<String, Map<Integer, String>> rulerClass = new TreeMap<>(
                new MapKeyComparator());
        //查询出所有分组
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.setObj(new TbRulerGroup());
        List<TbRulerGroup> groups = rulerGroupDao.getGroups(sqlCondition);
        groups.forEach(g -> rulerClass.put(g.getGroupName(), new HashMap<>()));
        //查询规则
        List<TbEventRulerMain> allRulers = eventRulerDao.getAllRulers();
        allRulers.forEach((r) -> {
            //遍历规则名称
            r = TbEventRulerCodeC.decode(r);
            String s = r.getRulerName();
            Integer id = r.getId();
            //组合规则名文件名
            String groupName = r.getGroupName();
            if (rulerClass.containsKey(groupName)) {
                rulerClass.get(groupName).put(id, s);
            }

        });
        return rulerClass;
    }

    private class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {
            int len1 = str1.split("-")[0].length();
            int len2 = str2.split("-")[0].length();
            if (len1 > len2) {
                int i = len1 - len2;
                for (int j = 0; j < i; j++) {
                    str2 = 0 + str2;
                }
            } else {
                int i = len2 - len1;
                for (int j = 0; j < i; j++) {
                    str1 = 0 + str1;
                }
            }
            return str1.compareTo(str2);
        }
    }


    /**
     * 通过文件中的规则解析，但condition来自用户
     *
     * @param rawEvent
     * @param condition
     * @return
     */
    @Override
    public Map<String, String> analyseLog(String rawEvent, String condition, Integer rulerId) {
        //查找对应的规则
        TbEventRulerMain ruler = getRuler(rulerId);

        Integer pluginCode = ruler.getPluginCode();
        //获取translate
        List<TranslationRuler> translations = eventRulerTransService.getEventRulerTrans(pluginCode);
        //解析日志
        Map<String, String> result = AnalyseUtils.analyseLog(ruler, rawEvent, translations, condition);
        return result;
    }


    @Override
    public Map<String, String> analyseLogByUserCondition(String translation, String rulerContent, String rawEvent, String condition, Integer rulerId) {
        List<TranslationRuler> translationRulers = JsonUtils.jsonToList(translation, TranslationRuler.class);
        HashMap content = JsonUtils.jsonToPojo(rulerContent, HashMap.class);
        LOGGER.debug("content: " + content.toString());
        Map<String, String> result = AnalyseUtils.analyseLog(content, rawEvent, translationRulers, condition);
        return result;
    }

    /**
     * 通过文件中的规则解析 所有条件均来自文件
     *
     * @param rawEvent
     * @return
     */
    @Override
    public Map<String, String> analyseLog(String rawEvent, Integer rulerId) {
        //查找对应的规则
        TbEventRulerMain ruler = getRuler(rulerId);
        Integer pluginCode = ruler.getPluginCode();
        //获取translate
        List<TranslationRuler> translations = eventRulerTransService.getEventRulerTrans(pluginCode);
        //解析日志
        Map<String, String> result = AnalyseUtils.analyseLog(ruler, rawEvent, translations);
        return result;
    }


    @Override
    public void saveOrUpdateRuler(TbEventRulerMain tbEventRuler) {
        Integer id = tbEventRuler.getId();
        if (id != null) {
            eventRulerDao.updateRuler(tbEventRuler, "id=" + id);
        } else {
            eventRulerDao.saveRuler(tbEventRuler);
        }
    }

    @Override
    public void delRulerById(Integer id) {
        TbEventRulerMain tbEventRuler = new TbEventRulerMain();
        tbEventRuler.setId(id);
        eventRulerDao.delRuler(tbEventRuler);
    }
}
