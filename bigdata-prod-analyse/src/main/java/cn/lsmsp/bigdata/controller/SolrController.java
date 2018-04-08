package cn.lsmsp.bigdata.controller;

import cn.lsmsp.bigdata.pojo.LsEvent;
import cn.lsmsp.bigdata.service.HbaseService;
import cn.lsmsp.bigdata.solr.SolrQueryService;
import cn.lsmsp.common.pojo.BigdataResult;
import cn.lsmsp.common.utils.JsonUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author WangCongJun
 * @date 2018/3/6
 * Created by WangCongJun on 2018/3/6.
 */
@Controller
@RequestMapping("/analyse")
public class SolrController {

    @Autowired
    private SolrQueryService solrQueryService;
    @Autowired
    private HbaseService hbaseService;

    @RequestMapping(value = "logs",method = RequestMethod.GET)
    @ResponseBody
    public BigdataResult  getLogs(String entid, String cusid, String cate, String subcate, String level, String device, int page, int rows, String time){
        String query = "";
        query+= StringUtils.isEmpty(entid)?"":",entid:"+entid;
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

    @RequestMapping(value = "/eventName",method = RequestMethod.GET)
    @ResponseBody
    public BigdataResult  getLogs(String eventName, int page, int rows){
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = yyyyMMdd.format(new Date());
        Date date = DateUtils.addDays(new Date(), -6);
        String query = "eventname2:\""+eventName+"\",eventstarttime:["+yyyyMMdd.format(date)+" TO "+format+"]";
        Page<LsEvent> queryResults = solrQueryService.getQueryResults(query, page, rows);
        int totalPages = queryResults.getTotalPages();
        List<LsEvent> content = queryResults.getContent();
        if(content.size()>0){
            LsEvent lsEvent = content.get(0);
            String sid = lsEvent.getSid();
            String byRowKey = hbaseService.getByRowKey(sid);
            return BigdataResult.build(200,totalPages+"",byRowKey);
        }
        return BigdataResult.build(603,"Error");
    }

}
