package cn.lsmsp.bigdata.controller;

import cn.lsmsp.bigdata.pojo.LsEvent;
import cn.lsmsp.bigdata.solr.SolrQueryService;
import cn.lsmsp.common.pojo.BigdataResult;
import cn.lsmsp.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(value = "logs",method = RequestMethod.GET)
    @ResponseBody
    public String  getLogs(String entid, String cusid, String cate, String subcate, String level, String device, int page, int rows, String time,String callback){
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
        return callback+"("+ JsonUtils.objectToJson(BigdataResult.build(200,totalPages+"",content))+")";
    }
}
