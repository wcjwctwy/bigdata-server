package cn.lsmsp.bigdata.protal.controller.ruler;

import cn.lsmsp.bigdata.check.policy.pojo.TbRulerGroup;
import cn.lsmsp.check.policy.service.RulerGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

@Controller
public class RulerGroupController {

    @Autowired
    private RulerGroupService rulerGroupService;


    @RequestMapping("ruler/rulerGroup")
    public String page(Model model){
        List<TbRulerGroup> allGroups = rulerGroupService.getAllGroups();
        model.addAttribute("allGroups",allGroups);
        return "ruler/rulerGroup";
    }

}
