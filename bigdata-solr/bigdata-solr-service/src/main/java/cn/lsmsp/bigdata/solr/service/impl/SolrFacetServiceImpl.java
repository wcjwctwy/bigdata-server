package cn.lsmsp.bigdata.solr.service.impl;

//import cn.lsmsp.bigdata.check.policy.dao.EventCountDao;
//import cn.lsmsp.bigdata.check.policy.dao.analyse.EventAnalyseDao;
//import cn.lsmsp.bigdata.check.policy.pojo.TbEventclassMin;
import cn.lsmsp.bigdata.dao.SolrFacetDao;
import cn.lsmsp.bigdata.pojo.LsEvent;
import cn.lsmsp.bigdata.solr.SolrFacetService;
import cn.lsmsp.common.pojo.analyse.TbEventAnalyse;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.query.FacetOptions;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.StatsPage;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class SolrFacetServiceImpl implements SolrFacetService {

    @Autowired
    private SolrFacetDao solrFacetDao;

//    @Autowired
//    private EventCountDao eventCountDao;

    @Override
    public FacetPage<LsEvent> getFacetByEntidAndCusid() {
        FacetPage<LsEvent> allFacet = solrFacetDao.getAllFacet(-1, "entid", "cusid");
        return allFacet;
    }

    @Override
    public FacetPage<LsEvent> getFacetByEntidAndCusidAndCates(Integer days, Integer step) {
        if (days > 0) {
            days = 0 - days;
        }
        Date date = new Date();
        Long end = Long.valueOf(DateFormatUtils.format(date, "yyyyMMdd") + "235959");
        Date startTime = DateUtils.addDays(date, days);
        Long start = Long.valueOf(DateFormatUtils.format(startTime, "yyyyMMdd") + "000000");
        FacetOptions.FieldWithNumericRangeParameters field = new FacetOptions.FieldWithNumericRangeParameters("eventstarttime", start, end, step * 1000000);

        FacetPage<LsEvent> allFacet = solrFacetDao.getAllFacet(-1, field, "entid", "cusid", "eventcategory");
        return allFacet;
    }

    @Override
    public FacetPage<LsEvent> getFacetByEntidAndCusidAndCates(String query) {
        FacetPage<LsEvent> allFacet = solrFacetDao.getAllFacet(query.split(","), -1, "entid", "cusid", "eventcategory");
        return allFacet;
    }

    @Override
    public StatsPage<LsEvent> getStatsByEntidAndCusid() {
        StatsPage<LsEvent> allStats = solrFacetDao.getAllStats(new String[]{"_version_"}, new String[]{"entid", "cusid"});
        return allStats;
    }


//    @Autowired
//    private EventAnalyseDao eventAnalyseDao;
    @Override
    public void save2Mysql(String year,String month,String day) {
        String time=year+month+day+"*";
        String[] query=null;
        if(!time.equals("*")){
            query= new String[]{"eventstarttime:"+time};
        }
        FacetPage<LsEvent> all = solrFacetDao.getAllFacet(query, -1, "entid", "cusid", "eventcategory","eventcategorytechnique","eventlevel","categorydevice");
        all.getPivot("entid,cusid,eventcategory,eventcategorytechnique,eventlevel,categorydevice")
                .forEach(x -> x.getPivot().forEach(y->y.getPivot().forEach(z->z.getPivot().forEach(a->a.getPivot().forEach(b->b.getPivot().forEach(c->{
                    TbEventAnalyse tbEventAnalyse = new TbEventAnalyse();
                    tbEventAnalyse.setEntId(Long.valueOf(x.getValue()));
                    tbEventAnalyse.setCusId(Long.valueOf(y.getValue()));
                    tbEventAnalyse.setEventCategory(z.getValue());
                    tbEventAnalyse.setEventCategoryTechnique(a.getValue());
                    tbEventAnalyse.setEventLevel(b.getValue());
                    tbEventAnalyse.setCategoryDevice(c.getValue());
                    tbEventAnalyse.setTotal(c.getValueCount());
                    if(!StringUtils.isEmpty(year))tbEventAnalyse.setYear(Short.valueOf(year));
                    if(!StringUtils.isEmpty(month))tbEventAnalyse.setMonth(Short.valueOf(month));
                    if(!StringUtils.isEmpty(day))tbEventAnalyse.setDay(Short.valueOf(day));
//                    eventAnalyseDao.saveEvent(tbEventAnalyse);
                }))))));
    }

    @Override
    public void save2Mysql(String year, String month, String day, String hour, String min) {
        String time=year+""+month+""+day+""+hour+""+min+"*";
        String[] query=null;
        if(!time.equals("*")){
            query= new String[]{"eventstarttime:"+time};
        }
        FacetPage<LsEvent> all = solrFacetDao.getAllFacet(query, -1, "entid", "cusid", "eventcategory","eventcategorytechnique","eventlevel","categorydevice","eventname","deviceaddress");
        all.getPivot("entid,cusid,eventcategory,eventcategorytechnique,eventlevel,categorydevice,eventname,deviceaddress")
                .forEach(x -> x.getPivot().forEach(y->y.getPivot().forEach(z->z.getPivot().forEach(a->a.getPivot().forEach(b->b.getPivot().forEach(c->c.getPivot().forEach(d->d.getPivot().forEach(f->{
//                    TbEventclassMin eventCount = new TbEventclassMin();
//                    eventCount.setEntId(Long.valueOf(x.getValue()));
//                    eventCount.setAssetId(Long.valueOf(y.getValue()));
//                    eventCount.setEventCategory(z.getValue());
//                    eventCount.setEventCategoryTechnique(a.getValue());
//                    eventCount.setEventLevel(b.getValue());
//                    eventCount.setCategoryDevice(c.getValue());
//                    eventCount.setEventCount(f.getValueCount());
//                    eventCount.setEventName(d.getValue());
//                    eventCount.setDeviceAddress(f.getValue());
//                    if(!StringUtils.isEmpty(year))eventCount.setYear(year);
//                    if(!StringUtils.isEmpty(month))eventCount.setMonth(month);
//                    if(!StringUtils.isEmpty(day))eventCount.setDay(day);
//                    if(!StringUtils.isEmpty(hour))eventCount.setHour(hour);
//                    if(!StringUtils.isEmpty(min))eventCount.setMin(min);
//                    eventCountDao.save(eventCount);
                }))))))));

    }
}
