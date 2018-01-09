package cn.lsmsp.bigdata.protal.controller.analyse;

import cn.lsmsp.bigdata.check.policy.dao.analyse.EventAnalyseDao;
import cn.lsmsp.bigdata.dao.LsEventSolrRepository;
import cn.lsmsp.bigdata.pojo.EventCount;
import cn.lsmsp.bigdata.pojo.LsEvent;
import cn.lsmsp.bigdata.solr.SolrFacetService;
import cn.lsmsp.bigdata.solr.SolrQueryService;
import cn.lsmsp.common.pojo.BigdataResult;
import cn.lsmsp.common.pojo.analyse.TbEventAnalyse;
import cn.lsmsp.common.utils.SqlCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/analyse")
public class SolrController {

    @Autowired
    private SolrFacetService solrFacetService;

    @Autowired
    private EventAnalyseDao eventAnalyseDao;

    @Autowired
    private SolrQueryService solrQueryService;

    @Autowired
    private LsEventSolrRepository lsEventSolrRepository;

    @RequestMapping("/test")
    @ResponseBody
    public String test(String field) {
        FacetPage<LsEvent> facet = solrFacetService.getFacetByEntidAndCusid();
        Map<String, Map<String, String>> resultCusid = new HashMap<>();
        facet.getPivot("entid,cusid")
                .forEach(x -> {
                    Map<String, String> cusid = new HashMap<>();
                    resultCusid.put(x.getValue(), cusid);
                    x.getPivot().forEach(y -> {
                        cusid.put(y.getValue(), y.getValueCount() + "");
                    });
                });
        return resultCusid + "";
    }

    @RequestMapping("/test1")
    @ResponseBody
    public String test1() {
        Pageable page = new PageRequest(1, 1);
        StatsPage<LsEvent> allByCusIdIsNull = solrFacetService.getStatsByEntidAndCusid();
//        StatsPage<LsEvent> allByCusIdIsNull = lsEventSolrRepository.findAllByCusIdIsNull(page);
        FieldStatsResult eventstarttime = allByCusIdIsNull.getFieldStatsResult("_version_");
        Map<String, StatsResult> cusid = eventstarttime.getFacetStatsResult("cusid");
        Map<String, StatsResult> entid = eventstarttime.getFacetStatsResult("entid");
//        eventstarttime.getCount();
        return entid + "=========\n==========" + cusid;
    }

    @RequestMapping("/test2")
    @ResponseBody
    public String test2(String query) {

        FacetPage<LsEvent> facet = solrFacetService.getFacetByEntidAndCusidAndCates(query);
        List<FacetPivotFieldEntry> pivot = facet.getPivot("entid,cusid,eventcategory");
        pivot.forEach(x -> {

        });
        return null;
    }

    @RequestMapping("/test3")
    @ResponseBody
    public BigdataResult test3(String query) {
        Page<LsEvent> queryResults = solrQueryService.getQueryResults(query, 0, 10);
        List<LsEvent> content = queryResults.getContent();
        return BigdataResult.ok(content);
    }

    @RequestMapping("/test5")
    @ResponseBody
    public BigdataResult test5(String fields) {
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.setGroup(fields);
        sqlCondition.setTableName("tb_event_analyse");
        sqlCondition.setSum("sum(total) as total");
        List<TbEventAnalyse> ent_id = eventAnalyseDao.getGroupResults(sqlCondition);
        return BigdataResult.ok(ent_id);
    }


    @RequestMapping("/import/{year}")
    @ResponseBody
    public BigdataResult import2Mysql(@PathVariable String year) {
        solrFacetService.save2Mysql(year,"","");
        return BigdataResult.ok();
    }
    @RequestMapping("/import/{year}/{month}")
    @ResponseBody
    public BigdataResult import2Mysql(@PathVariable String year,@PathVariable String month) {
        solrFacetService.save2Mysql(year,month,"");
        return BigdataResult.ok();
    }

    @RequestMapping("/import/{year}/{month}/{day}")
    @ResponseBody
    public BigdataResult import2Mysql(@PathVariable String year,@PathVariable String month,@PathVariable String day) {
        solrFacetService.save2Mysql(year,month,day);
        return BigdataResult.ok();
    }

    @RequestMapping("/import/{year}/{month}/{day}/{hour}/{min}")
    @ResponseBody
    public BigdataResult import2Mysql(@PathVariable String year,@PathVariable String month,@PathVariable String day,@PathVariable String hour,@PathVariable String min) {
        solrFacetService.save2Mysql(year,month,day,hour,min);
        return BigdataResult.ok();
    }


}
