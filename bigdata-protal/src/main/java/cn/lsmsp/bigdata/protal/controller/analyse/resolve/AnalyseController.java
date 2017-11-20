package cn.lsmsp.bigdata.protal.controller.analyse.resolve;

import cn.lsmsp.bigdata.pojo.LsEvent;
import cn.lsmsp.bigdata.solr.EventAnalyseService;
import cn.lsmsp.bigdata.solr.SolrQueryService;
import cn.lsmsp.common.pojo.BigdataResult;
import cn.lsmsp.common.pojo.analyse.TbEventAnalyse;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/analyse")
public class AnalyseController {

    @Autowired
    private EventAnalyseService eventAnalyseService;

    @Autowired
    private SolrQueryService solrQueryService;


    @RequestMapping("/resolve")
    public String facet(Model model) {
//        List<TbEventAnalyse> total = eventAnalyseService.getStatResult(null);
//        model.addAttribute("total",total.get(0));
        Date date = DateUtils.addDays(new Date(), -1);
        List<Map<String, Long>> statResults = eventAnalyseService.getStatResults(new TbEventAnalyse(),date);
        model.addAttribute("statResults",statResults);
        String format = DateFormatUtils.format(date, "yyyy-MM-dd");
        String[] split = format.split("-");
        model.addAttribute("time",split);
        return "analyse/resolve";
    }

    @RequestMapping(value = "logs",method = RequestMethod.GET)
    public String logs(){
        return "analyse/logs";
    }

    @RequestMapping(value = "logs",method = RequestMethod.POST)
    @ResponseBody
    public BigdataResult getLogs(String entid,String cusid,String cate,String subcate,String level,String device,int page,int rows,String time){
        String query = "";
        query+=StringUtils.isEmpty(entid)?"":",entid:"+entid;
        query+=StringUtils.isEmpty(cusid)?"":",cusid:"+cusid;
        query+=StringUtils.isEmpty(cate)?"":",eventcategory:"+cate;
        query+=StringUtils.isEmpty(subcate)?"":",eventcategorytechnique:"+subcate;
        query+=StringUtils.isEmpty(level)?"":",eventlevel:"+level;
        query+=StringUtils.isEmpty(device)?"":",categorydevice:"+device;
        query+=StringUtils.isEmpty(time)?"":",eventstarttime:"+time;
        query=StringUtils.isEmpty(query)?"*:*":query.substring(1);
        Page<LsEvent> queryResults = solrQueryService.getQueryResults(query, page, rows);
        int totalPages = queryResults.getTotalPages();
        List<LsEvent> content = queryResults.getContent();
        return BigdataResult.build(200,totalPages+"",content);
    }


    @RequestMapping(value = "/stats",method = RequestMethod.GET)
    @ResponseBody
    public BigdataResult stats(TbEventAnalyse eventAnalyse){
        List<Map<String, Long>> statResults = eventAnalyseService.getStatResults(eventAnalyse);
        return BigdataResult.ok(statResults);
    }




}
