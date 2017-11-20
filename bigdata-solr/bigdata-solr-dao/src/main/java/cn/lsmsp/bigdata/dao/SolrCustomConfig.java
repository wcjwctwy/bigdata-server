package cn.lsmsp.bigdata.dao;

import cn.lsmsp.bigdata.pojo.LsEvent;
import org.apache.solr.client.solrj.SolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.result.FacetPage;

@Configuration
public class SolrCustomConfig {

    @Autowired
    private LsEventSolrRepository lsEventSolrRepository;

//    @Bean(name = "testFacet")
    public FacetPage<LsEvent> getTest(){
        PageRequest pageRequest = new PageRequest(1, 1);

        FacetPage<LsEvent> allFacet = lsEventSolrRepository.findAllFacet(pageRequest);
        return allFacet;
    }

    @Bean
    @Autowired
    public SolrTemplate getSolrTemplate(SolrClient solrClient){
        SolrTemplate solrTemplate = new SolrTemplate(solrClient);
        return solrTemplate;
    }

}
