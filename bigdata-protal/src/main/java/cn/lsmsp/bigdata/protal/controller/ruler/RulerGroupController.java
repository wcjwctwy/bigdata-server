package cn.lsmsp.bigdata.protal.controller.ruler;

import cn.lsmsp.bigdata.check.policy.pojo.TbRulerGroup;
import cn.lsmsp.check.policy.service.RulerGroupService;
import cn.lsmsp.common.pojo.BigdataResult;
import cn.lsmsp.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@Slf4j
public class RulerGroupController {

    @Autowired
    private RulerGroupService rulerGroupService;


    @GetMapping("ruler/rulerGroup")
    @ResponseBody
    public BigdataResult page(){
        List<TbRulerGroup> allGroups = rulerGroupService.getAllGroups();
        return BigdataResult.ok(allGroups);
    }

    @PostMapping("ruler/rulerGroup")
    @ResponseBody
    public BigdataResult addRulerGroup(TbRulerGroup tbRulerGroup){
        rulerGroupService.addGroup(tbRulerGroup);
        return BigdataResult.ok();
    }

    @PutMapping("ruler/rulerGroup")
    @ResponseBody
    public BigdataResult UpdateRulerGroup(TbRulerGroup tbRulerGroup){
        rulerGroupService.updateGroupById(tbRulerGroup);
        return BigdataResult.ok();
    }

    @DeleteMapping("ruler/rulerGroup/{id}")
    @ResponseBody
    public BigdataResult delRulerGroup(@PathVariable("id") Integer id){
        log.info("【规则分组操作】删除分组ID：{}",id);
        rulerGroupService.delGroupById(id);
        return BigdataResult.ok();
    }

}
