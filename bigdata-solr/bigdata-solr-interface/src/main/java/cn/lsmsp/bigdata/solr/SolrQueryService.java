package cn.lsmsp.bigdata.solr;

import cn.lsmsp.bigdata.pojo.LsEvent;
import org.springframework.data.domain.Page;

public interface SolrQueryService {

    Page<LsEvent> getQueryResults(String query,int page,int rows);
}
