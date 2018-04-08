package cn.lsmsp.bigdata.solr.service.impl;

import cn.lsmsp.bigdata.pojo.LsEvent;
import cn.lsmsp.bigdata.solr.EventAnalyseService;
import cn.lsmsp.common.pojo.analyse.TbEventAnalyse;
import cn.lsmsp.common.utils.SqlCondition;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class EventAnalyseServiceImpl implements EventAnalyseService {


//    @Autowired
//    private EventAnalyseDao eventAnalyseDao;

    @Override
    public List<TbEventAnalyse> getStatResult(String fields) {
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.setGroup(fields);
        sqlCondition.setTableName("tb_event_analyse");
        sqlCondition.setSum("sum(total) as total");
//        List<TbEventAnalyse> groupResults = eventAnalyseDao.getGroupResults(sqlCondition);
        return null;
    }

    @Override
    public List<TbEventAnalyse> getStatResult(TbEventAnalyse eventAnalyse, String groupFields) {
        SqlCondition sqlCondition = null;
        try {
            sqlCondition = new SqlCondition(eventAnalyse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!StringUtils.isEmpty(groupFields)) {
            sqlCondition.setGroup(groupFields);
        }
        sqlCondition.setSum("sum(total) as total");
//        List<TbEventAnalyse> groupResults = eventAnalyseDao.getGroupResults(sqlCondition);
        return null;
    }

    @Override
    public List<Map<String, Long>> getStatResults(TbEventAnalyse eventAnalyse) {
        List<Map<String, Long>> result = new ArrayList<>();
        List<TbEventAnalyse> statResults = getStatResult(eventAnalyse, "");
        if(statResults==null){
            return result;
        }
        TbEventAnalyse statResult = statResults.get(0);
        if(statResult==null){
            return result;
        }
        Map<String, Long> total = new HashMap<>();
        total.put("total", statResult.getTotal());
        result.add(total);

        List<TbEventAnalyse> allEnts = getStatResult(eventAnalyse, entid);
        Map<String, Long> ents = new HashMap<>();
        allEnts.forEach(e -> ents.put(e.getEntId() + "", e.getTotal()));
        result.add(ents);

        List<TbEventAnalyse> allCuss = getStatResult(eventAnalyse, cusid);
        Map<String, Long> cuss = new HashMap<>();
        allCuss.forEach(e -> cuss.put(e.getCusId() + "", e.getTotal()));
        result.add(cuss);

        List<TbEventAnalyse> allCats = getStatResult(eventAnalyse, cate);
        Map<String, Long> cats = new HashMap<>();
        allCats.forEach(e -> cats.put(e.getEventCategory(), e.getTotal()));
        result.add(cats);

        List<TbEventAnalyse> allSubs = getStatResult(eventAnalyse, subcate);
        Map<String, Long> subs = new HashMap<>();
        allSubs.forEach(e -> subs.put(e.getEventCategoryTechnique(), e.getTotal()));
        result.add(subs);

        List<TbEventAnalyse> allLevs = getStatResult(eventAnalyse, level);
        Map<String, Long> levs = new HashMap<>();
        allLevs.forEach(e -> levs.put(e.getEventLevel(), e.getTotal()));
        result.add(levs);

        List<TbEventAnalyse> allDevs = getStatResult(eventAnalyse, device);
        Map<String, Long> devs = new HashMap<>();
        allDevs.forEach(e -> devs.put(e.getCategoryDevice(), e.getTotal()));
        result.add(devs);
        return result;
    }

    @Override
    public List<Map<String, Long>> getStatResults(TbEventAnalyse eventAnalyse, Date date) {
        String format = DateFormatUtils.format(date, "yyyy-MM-dd");
        String[] split = format.split("-");
        eventAnalyse.setYear(Short.valueOf(split[0]));
        eventAnalyse.setMonth(Short.valueOf(split[1]));
        eventAnalyse.setDay(Short.valueOf(split[2]));
        return getStatResults(eventAnalyse);
    }

    @Override
    public Map<Long, long[]> getTimeLineStatResultsByMonth(TbEventAnalyse eventAnalyse) {
        Map<Long, long[]> result = new HashMap<>();
//        List<TbEventAnalyse> timeLineEntidResultsGroupByDay = eventAnalyseDao.getTimeLineEntidResultsGroupByDay((int)eventAnalyse.getYear(), (int)eventAnalyse.getMonth());
//        timeLineEntidResultsGroupByDay.forEach(ea->{
//            Long entId = ea.getEntId();
//            Short day = ea.getDay();
//            if(!result.containsKey(entId)){
//                long[] longs = new long[31];
//                result.put(entId,longs);
//            }
//            result.get(entId)[day-1]=ea.getTotal();
//        });

        return result;
    }
}
