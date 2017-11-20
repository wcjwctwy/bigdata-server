package cn.lsmsp.bigdata.solr.service.impl;

import cn.lsmsp.bigdata.check.policy.dao.analyse.EventAnalyseDao;
import cn.lsmsp.bigdata.dao.SolrFacetDao;
import cn.lsmsp.bigdata.pojo.EventCount;
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

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SolrFacetServiceImpl implements SolrFacetService {

    @Autowired
    private SolrFacetDao solrFacetDao;

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


    @Autowired
    private EventAnalyseDao eventAnalyseDao;
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
                    eventAnalyseDao.saveEvent(tbEventAnalyse);
                }))))));
    }


}
