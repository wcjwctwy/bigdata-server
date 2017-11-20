package cn.lsmsp.bigdata.protal.controller.ruler;

import cn.lsmsp.bigdata.check.policy.pojo.TbEventRulerMain;
import cn.lsmsp.check.policy.service.RulerService;
import cn.lsmsp.common.pojo.BigdataResult;
import cn.lsmsp.common.utils.UpDownLoadUtils;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
public class RulerListController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RulerListController.class);

    @Autowired
    private RulerService eventRulerServer;

    @Value("${RULER_TEMP_DOWNLOAD_PATH}")
    private String RULER_TEMP_DOWNLOAD_PATH;

    @RequestMapping(value = "/ruler/list",method = RequestMethod.GET)
    @ResponseBody
    public BigdataResult rulerList( int page, int rows){
        PageHelper.startPage(page,rows);
        List<TbEventRulerMain> allRulers = eventRulerServer.getAllRulers();
        return BigdataResult.ok(allRulers);
    }

    @RequestMapping(value = "/ruler/count",method = RequestMethod.GET)
    @ResponseBody
    public BigdataResult rulerCount(){
        Integer rulersCount = eventRulerServer.getRulersCount();
        return BigdataResult.ok(rulersCount);
    }

    @RequestMapping(value = "/ruler/download",method = RequestMethod.GET)
    @ResponseBody
    public BigdataResult downloadRulersAsXml(HttpServletResponse  res){
        //导出数据到xml文件中
        String exportXmlRulerPath = eventRulerServer.exportXmlRuler();
        //压缩xml文件夹
        String compressFilepath = eventRulerServer.compressXmlFiles(exportXmlRulerPath,true);
        //下载文件
        UpDownLoadUtils.download(compressFilepath,"rulers.zip",res,true);
        return BigdataResult.ok("下载完成!");
    }

    @RequestMapping("/ruler/rulersList")
    public String page(){
        return "ruler/rulersList";
    }
}
