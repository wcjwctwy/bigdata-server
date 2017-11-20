package cn.lsmsp.bigdata.protal.controller.ruler;

import cn.lsmsp.bigdata.check.policy.pojo.LsEventSubcategory;
import cn.lsmsp.check.policy.service.LsEventCategoryService;
import cn.lsmsp.check.policy.service.LsEventSubcategoryService;
import cn.lsmsp.common.pojo.BigdataResult;
import cn.lsmsp.common.pojo.EventCategoryHtml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ClassifyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassifyController.class);

    @Autowired
    private LsEventCategoryService lsEventCategoryService;

    @Autowired
    private LsEventSubcategoryService lsEventSubcategoryService;

    @RequestMapping("ruler/classify")
    public String page(Model model){
        List<EventCategoryHtml> eventCategories = lsEventCategoryService.getEventCategoryHtmls();
        LOGGER.debug(eventCategories.toString());
        model.addAttribute("eventCategories",eventCategories);
        return "ruler/classify";
    }


    @RequestMapping(value = "/ruler/classify/subcount",method = RequestMethod.GET)
    @ResponseBody
    public BigdataResult getSubCount(Integer categoryId){
        Integer count = 0;
        try {
             count = lsEventSubcategoryService.getSubCountByCategoryId(categoryId);
        }catch (Exception e){
            e.printStackTrace();
            return BigdataResult.build(407,e.getMessage());
        }
        return BigdataResult.ok(count);
    }

    @RequestMapping(value = "/ruler/classify/sub",method = RequestMethod.GET)
    @ResponseBody
    public BigdataResult getSubcategoriesByCid(Integer categoryId,Integer page,Integer rows){
        List<LsEventSubcategory> subcategories = lsEventSubcategoryService.getEventSubcategoriesByCategoryId(categoryId, page, rows);
        return BigdataResult.ok(subcategories);
    }

}
