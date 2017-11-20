package cn.lsmsp.bigdata.solr.service.impl;

import cn.lsmsp.bigdata.dao.SolrQueryDao;
import cn.lsmsp.bigdata.pojo.LsEvent;
import cn.lsmsp.bigdata.solr.SolrQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SolrQueryServiceImpl implements SolrQueryService {

    @Autowired
    private SolrQueryDao solrQueryDao;

    @Override
    public Page<LsEvent> getQueryResults(String query, int page, int rows) {
        String[] conditions = query.split(",");
        Pageable pageable = new PageRequest(page,rows);
        Page<LsEvent> lsEvents = solrQueryDao.queryLogs(conditions, pageable);
        return lsEvents;
    }
}
