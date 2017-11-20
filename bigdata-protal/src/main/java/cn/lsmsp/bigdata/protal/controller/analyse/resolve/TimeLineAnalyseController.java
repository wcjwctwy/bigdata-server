package cn.lsmsp.bigdata.protal.controller.analyse.resolve;

import cn.lsmsp.bigdata.solr.EventAnalyseService;
import cn.lsmsp.common.pojo.BigdataResult;
import cn.lsmsp.common.pojo.analyse.TbEventAnalyse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;


@Controller
@RequestMapping("/analyse")
public class TimeLineAnalyseController {

    @Autowired
    private EventAnalyseService eventAnalyseService;

    @GetMapping(value = "/timeLine")
    public String stats(Model model) {
        TbEventAnalyse tbEventAnalyse = new TbEventAnalyse();
        tbEventAnalyse.setYear(Short.valueOf("2017"));
        tbEventAnalyse.setMonth(Short.valueOf("11"));
        Map<Long, long[]> timeLineStatResultsByMonth = eventAnalyseService.getTimeLineStatResultsByMonth(tbEventAnalyse);
        model.addAttribute("timeLineStatResultsByMonth",timeLineStatResultsByMonth);
        return "analyse/timeLine";
    }
}
