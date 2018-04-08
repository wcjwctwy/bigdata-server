package cn.lsmsp.bigdata.controller;

import cn.lsmsp.bigdata.entity.EventAnalyse;
import cn.lsmsp.bigdata.service.EventclassService;
import cn.lsmsp.common.pojo.BigdataResult;
import cn.lsmsp.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/3/6
 * Created by WangCongJun on 2018/3/6.
 */
@Controller
@Slf4j
@RequestMapping("/analyse")
public class AnalyseController {

    @Autowired
    private EventclassService eventclassService;

    @RequestMapping(value = "/stats",method = RequestMethod.GET)
    @ResponseBody
    public BigdataResult stats(EventAnalyse eventAnalyse){
        try {
            List<Map<String, Long>> statResults = eventclassService.getStatResults(eventAnalyse);
            return BigdataResult.ok(statResults);
        }catch (Exception e){
            log.error("【AnalyseController】stats 统计出错了！！ ");
            e.printStackTrace();
        }
        return BigdataResult.build(601,"统计出错了！！");

    }

    @RequestMapping(value = "/stats/{field}",method = RequestMethod.GET)
    @ResponseBody
    public BigdataResult stats(EventAnalyse eventAnalyse,@PathVariable("field") String field){
        try {
            Map<String, Long> statResults = eventclassService.getStatResults(eventAnalyse,field);
            return BigdataResult.ok(statResults);
        }catch (Exception e){
            log.error("【AnalyseController】stats 统计出错了！！ ");
            e.printStackTrace();
        }
        return BigdataResult.build(601,"统计出错了！！");

    }

}
