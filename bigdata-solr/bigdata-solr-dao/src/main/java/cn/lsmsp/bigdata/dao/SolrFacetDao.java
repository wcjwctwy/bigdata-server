package cn.lsmsp.bigdata.dao;

import cn.lsmsp.bigdata.pojo.LsEvent;
import org.apache.solr.common.params.FacetParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.StatsPage;
import org.springframework.stereotype.Repository;

@Repository
public class SolrFacetDao {

    @Autowired
    private SolrTemplate solrTemplate;

    public FacetPage<LsEvent> getAllFacet(String[] query,Pageable pageable, Integer limit, FacetOptions.FacetSort facetSort, FacetOptions.FieldWithNumericRangeParameters rangeFields, String... facetFields) {
        query=query==null?new String[]{"*:*"}:query;
        FacetQuery facetQuery = new SimpleFacetQuery();
        for(String q:query){
            facetQuery.addCriteria(new SimpleStringCriteria(q));
        }
        FacetOptions facetOptions = new FacetOptions();
        facetOptions.setFacetMinCount(0);
        facetOptions.addFacetOnPivot(facetFields);

        if(rangeFields!=null) {
            facetOptions.addFacetByRange(rangeFields
                    .setHardEnd(true)
                    .setInclude(FacetParams.FacetRangeInclude.ALL));
        }
        if (limit != null) {
            facetOptions.setFacetLimit(limit);
        }

        if (pageable != null) {
            facetOptions.setPageable(pageable);
        }
        if (facetSort != null) {
            facetOptions.setFacetSort(facetSort);
        }
        if (facetFields != null & facetFields.length != 0) {
            facetQuery.setFacetOptions(facetOptions);
        }
        FacetPage<LsEvent> facetResult = solrTemplate.queryForFacetPage("eventlog-collection", facetQuery, LsEvent.class);
        return facetResult;
    }

    public FacetPage<LsEvent> getAllFacet(Integer limit, String... facetFields) {
        FacetPage<LsEvent> facetResult = getAllFacet(null,null, limit, null, null,facetFields);
        return facetResult;
    }

    public FacetPage<LsEvent> getAllFacet(String[] query,Integer limit, String... facetFields) {
        FacetPage<LsEvent> facetResult = getAllFacet(query,null, limit, null, null,facetFields);
        return facetResult;
    }

    public FacetPage<LsEvent> getAllFacet(Integer limit, FacetOptions.FieldWithNumericRangeParameters rangeFields,String... facetFields) {
        FacetPage<LsEvent> facetResult = getAllFacet(null,null, limit, null, rangeFields,facetFields);
        return facetResult;
    }


    public StatsPage<LsEvent> getAllStats(String[] statsFields, String[] facetFields) {
        // for distinct calculation
        StatsOptions statsOptions = new StatsOptions();
        for (String field : statsFields) {
            statsOptions.addField(field);
        }
        // for distinct calculation
        for (String field : facetFields) {
            statsOptions.addFacet(field);
        }
        statsOptions.setCalcDistinct(true);
        // for faceting
        // query
        SimpleQuery statsQuery = new SimpleQuery("*:*");
        statsQuery.setStatsOptions(statsOptions);
        StatsPage<LsEvent> statsPage = solrTemplate.queryForStatsPage("eventlog-collection",statsQuery, LsEvent.class);

        return statsPage;
    }

}
