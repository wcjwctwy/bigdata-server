package cn.lsmsp.bigdata.dao;

import cn.lsmsp.bigdata.pojo.LsEvent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.StatsPage;
import org.springframework.data.solr.repository.*;


public interface LsEventSolrRepository extends SolrCrudRepository<LsEvent, String> {

    @Query(value = "*:*")
    @Facet(fields = { "entid"},limit = -1)
    FacetPage<LsEvent> findAllFacetOnEntid(Pageable page);

    @Query(value = "*:*")
    @Facet(pivots = @Pivot({ "entid","cusid","eventcategory","eventcategorytechnique","eventlevel","deviceproduct" }),limit = -1)
    FacetPage<LsEvent> findAllFacet(Pageable page);

    @Query("*:*")
    @Stats(value = "_version_", facets = { "entid","cusid" })
    StatsPage<LsEvent> findAllByCusIdIsNull(Pageable page);

}
