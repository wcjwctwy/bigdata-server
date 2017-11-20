package cn.lsmsp.bigdata.solr;

import cn.lsmsp.bigdata.pojo.LsEvent;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.StatsPage;

public interface SolrFacetService {

    FacetPage<LsEvent> getFacetByEntidAndCusid();
    FacetPage<LsEvent> getFacetByEntidAndCusidAndCates(Integer days, Integer step);
    FacetPage<LsEvent> getFacetByEntidAndCusidAndCates(String query);
    StatsPage<LsEvent> getStatsByEntidAndCusid();
    void save2Mysql(String year,String month,String day);

}
