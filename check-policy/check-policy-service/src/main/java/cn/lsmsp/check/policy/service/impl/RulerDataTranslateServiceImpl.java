package cn.lsmsp.check.policy.service.impl;

import cn.lsmsp.bigdata.check.policy.dao.EventRulerMainDao;
import cn.lsmsp.bigdata.check.policy.utils.RulerUtils;
import cn.lsmsp.bigdata.check.policy.pojo.XmlRuler;
import cn.lsmsp.bigdata.check.policy.pojo.TbEventRulerMain;
import cn.lsmsp.check.policy.service.RulerDataTranslateService;
import cn.lsmsp.common.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class RulerDataTranslateServiceImpl implements RulerDataTranslateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RulerDataTranslateServiceImpl.class);

    @Autowired
    EventRulerMainDao eventRulerDao;

    /**
     * 将path下的规则数据导入数据库
     *
     * @param path
     */
    @Override
    public void xmlData2DB(String path) {
        Map<String, XmlRuler> stringRulerConfigMap = RulerUtils.loadRuler(path);
        stringRulerConfigMap.forEach((fileName, rulerConfig) -> {
            rulerConfig.getRulers().forEach(ruler -> {
                TbEventRulerMain tbEventRuler = new TbEventRulerMain();
                Date date = new Date();
                tbEventRuler.setRegex(ruler.getCondition());
                tbEventRuler.setCreatedTime(date);
                tbEventRuler.setUpdatedTime(date);
                tbEventRuler.setGroupName(fileName);
                tbEventRuler.setPluginCode(rulerConfig.getPluginCode());
                Map<String, String> content = ruler.getContent();
                tbEventRuler.setRulerContent(JsonUtils.objectToJson(content));
                tbEventRuler.setRulerName(content.get("EventName"));
                tbEventRuler.setStatus(2);
                eventRulerDao.saveRuler(tbEventRuler);
            });
        });
    }

    @Override
    public void dBData2XML(String path) {

    }
}
