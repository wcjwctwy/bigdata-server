package cn.lsmsp.bigdata.service.impl;

import cn.lsmsp.bigdata.dao.EventAnalyseDao;
import cn.lsmsp.bigdata.dto.ResolutionDTO;
import cn.lsmsp.bigdata.entity.EventAnalyse;
import cn.lsmsp.bigdata.service.EventclassService;
import cn.lsmsp.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author WangCongJun
 * @date 2018/3/5
 * Created by WangCongJun on 2018/3/5.
 */
@Service
@Slf4j
public class EventclassServiceImpl implements EventclassService {

    @Autowired
    private EventAnalyseDao eventAnalyseDao;

    @Override
    public List<EventAnalyse> getStatResult(String groupFields) throws Exception{

        return getStatResult(new EventAnalyse(),groupFields);
    }

    @Override
    public List<EventAnalyse> getStatResult(EventAnalyse eventAnalyse, String groupFields) throws Exception {
        log.info("【EventclassServiceImpl】 getStatResult:{}", JsonUtils.objectToJson(eventAnalyse));
        return eventAnalyseDao.getGroupResults(eventAnalyse,groupFields);
    }

    @Override
    public List<Map<String, Long>> getStatResults(EventAnalyse eventAnalyse) throws Exception {
        List<Map<String, Long>> result = new ArrayList<>();

        Map<String, Long> total = new HashMap<>();
        List<EventAnalyse> analyseDaoSum = eventAnalyseDao.getSum(eventAnalyse, "");
        if(ObjectUtils.isEmpty(analyseDaoSum)){
            //TODO 异常抛出 方便排查
            return null;
        }
        total.put("total", analyseDaoSum.get(0).getEventCount());
        result.add(total);

        List<EventAnalyse> allEnts = getStatResult(eventAnalyse, entid);
        Map<String, Long> ents = new HashMap<>();
        allEnts.forEach(e -> ents.put(e.getEntId() + "", e.getEventCount()));
        result.add(ents);

        List<EventAnalyse> allCuss = getStatResult(eventAnalyse, cusid);
        Map<String, Long> cuss = new HashMap<>();
        allCuss.forEach(e -> cuss.put(e.getAssetId() + "", e.getEventCount()));
        result.add(cuss);

        List<EventAnalyse> allCats = getStatResult(eventAnalyse, cate);
        Map<String, Long> cats = new HashMap<>();
        allCats.forEach(e -> cats.put(e.getEventCategory(), e.getEventCount()));
        result.add(cats);

        List<EventAnalyse> allSubs = getStatResult(eventAnalyse, subcate);
        Map<String, Long> subs = new HashMap<>();
        allSubs.forEach(e -> subs.put(e.getEventCategoryTechnique(), e.getEventCount()));
        result.add(subs);

        List<EventAnalyse> allLevs = getStatResult(eventAnalyse, level);
        Map<String, Long> levs = new HashMap<>();
        allLevs.forEach(e -> levs.put(e.getEventLevel(), e.getEventCount()));
        result.add(levs);

        List<EventAnalyse> allDevs = getStatResult(eventAnalyse, device);
        Map<String, Long> devs = new HashMap<>();
        allDevs.forEach(e -> devs.put(e.getCategoryDevice(), e.getEventCount()));
        result.add(devs);
        return result;
    }

    @Override
    public List<ResolutionDTO> getResolution(EventAnalyse eventAnalyse, String groupFields) {

        List<ResolutionDTO> resolution = eventAnalyseDao.getResolution(eventAnalyse, groupFields);

        return resolution;
    }

    @Override
    public Map<String, Long> getStatResults(EventAnalyse eventAnalyse, String groupField) throws Exception{
        List<EventAnalyse> allresults = eventAnalyseDao.getGroupResults(eventAnalyse,groupField);
        Map<String, Long> results = new HashMap<>();
        Method method = EventAnalyse.class.getMethod("get"+groupField.substring(0,1).toUpperCase()+groupField.substring(1));
        allresults.forEach(e -> {
            try {
                results.put(method.invoke(e).toString(), e.getEventCount());
            }catch (Exception e1){
                e1.printStackTrace();
            }
        });

        return results;
    }

    @Override
    public Map<Long, long[]> getTimeLineStatResultsByMonth(EventAnalyse eventAnalyse) {
        return null;
    }
}
