package cn.lsmsp.bigdata.protal.controller.ruler;

import cn.lsmsp.bigdata.check.policy.pojo.RuleField;
import cn.lsmsp.check.policy.service.RuleFieldService;
import cn.lsmsp.common.pojo.BigdataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WangCongJun
 * @date 2018/3/20
 * Created by WangCongJun on 2018/3/20.
 */
@RestController
public class RuleFieldController {

    @Autowired
    private RuleFieldService ruleFieldService;

    /**
     * 查询所有的字段类别信息
     * @return
     */
    @RequestMapping("/field/category")
    public BigdataResult getFieldCategory() {
        List<RuleField> ruleFields = ruleFieldService.selectParentField();

        return BigdataResult.ok(ruleFields);
    }

    /**
     * 获取指定某一类别的所有子类信息
     * @return
     */
    @RequestMapping("/field/category/sub")
    public BigdataResult getFieldInfoByCategoryId(Integer categoryId) {
        List<RuleField> ruleFields = ruleFieldService.selectChildrenFieldByParentId(categoryId);

        return BigdataResult.ok(ruleFields);
    }
    /**
     * 获取指定某一类别的所有子类信息
     * @return
     */
    @RequestMapping("/field/category/count")
    public BigdataResult getFieldInfoByCategoryIdCount(Integer categoryId) {
        List<RuleField> ruleFields = ruleFieldService.selectChildrenFieldByParentId(categoryId);

        return BigdataResult.ok(ruleFields.size());
    }

    /**
     * 增加字段类别
     * @return
     */
    @PostMapping("/field")
    public BigdataResult saveFieldCategory(RuleField ruleField){
        if(ruleField.getParentId()==0){
            ruleField.setIsParent(1);
        }else {
            ruleField.setIsParent(0);
        }
        ruleFieldService.saveField(ruleField);
        return BigdataResult.ok();
    }

    /**
     * 删除字段类别
     * @param id
     * @return
     */
    @DeleteMapping("/field/category")
    public BigdataResult delFieldCategory(Integer id,Boolean isParent){
        RuleField ruleField = new RuleField();
        ruleField.setId(id);
        if(isParent){
            ruleField.setIsParent(RuleField.ParentEnum.YES.getCode());
        }
        ruleFieldService.deleteField(ruleField);
        return BigdataResult.ok();
    }

}
