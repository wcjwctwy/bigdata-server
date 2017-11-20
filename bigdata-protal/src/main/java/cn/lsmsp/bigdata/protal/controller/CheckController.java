package cn.lsmsp.bigdata.protal.controller;

import cn.lsmsp.check.policy.service.RulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CheckController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckController.class);

    @Autowired
    private RulerService rulerService;

    @RequestMapping("/ruler/all")
    @ResponseBody
    public String getRulers(){
        String allRuler = rulerService.getAllRulers().toString();
        return allRuler;
    }



}
