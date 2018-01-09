package cn.lsmsp.bigdata.protal.controller.ruler;

import cn.lsmsp.bigdata.check.policy.pojo.TbRulerGroup;
import cn.lsmsp.check.policy.service.RulerGroupService;
import cn.lsmsp.common.pojo.BigdataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
public class RulerGroupController {

    @Autowired
    private RulerGroupService rulerGroupService;


    @GetMapping("ruler/rulerGroup")
    public String page(Model model){
        List<TbRulerGroup> allGroups = rulerGroupService.getAllGroups();
        model.addAttribute("allGroups",allGroups);
        return "ruler/rulerGroup";
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

    @DeleteMapping("ruler/rulerGroup")
    @ResponseBody
    public BigdataResult delRulerGroup(Integer id){
        rulerGroupService.delGroupById(id);
        return BigdataResult.ok();
    }

}
