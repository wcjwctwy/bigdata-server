package cn.lsmsp.bigdata.controller;

import cn.lsmsp.bigdata.service.HbaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangCongJun
 * @date 2018/3/23
 * Created by WangCongJun on 2018/3/23.
 */
@RestController
public class HbaseController {

    @Autowired
    private HbaseService hbaseService;

    @RequestMapping("/hbase/{rowKey}")
    public String test(@PathVariable("rowKey") String rowKey){
        return hbaseService.getByRowKey(rowKey);
    }
}
