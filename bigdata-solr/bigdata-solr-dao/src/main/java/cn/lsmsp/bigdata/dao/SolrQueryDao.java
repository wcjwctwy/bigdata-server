package cn.lsmsp.bigdata.dao;

import cn.lsmsp.bigdata.pojo.LsEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.stereotype.Repository;

@Repository
public class SolrQueryDao {

    @Autowired
    private SolrTemplate solrTemplate;

    public Page<LsEvent> queryLogs(String[] conditions, Pageable pageable){
        SimpleQuery query = new SimpleQuery();
        for(String c:conditions){
            query.addCriteria(new SimpleStringCriteria(c));
        }
        query.setPageRequest(pageable);
        Page<LsEvent> events = solrTemplate.query("eventlog-collection", query, LsEvent.class);
        return events;
    }
}
