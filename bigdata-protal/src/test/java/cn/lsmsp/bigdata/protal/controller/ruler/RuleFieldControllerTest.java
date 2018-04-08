package cn.lsmsp.bigdata.protal.controller.ruler;

import cn.lsmsp.bigdata.check.policy.dao.RuleFieldDao;
import cn.lsmsp.bigdata.check.policy.pojo.RuleField;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author WangCongJun
 * @date 2018/3/20
 * Created by WangCongJun on 2018/3/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RuleFieldControllerTest {

    @Autowired
    private RuleFieldDao ruleFieldDao;

    @Test
    public void save(){
        RuleField ruleFiled = new RuleField();
        ruleFiled.setFieldName("test");
        ruleFiled.setFieldDesc("test");
        ruleFiled.setFieldType("String");
        ruleFiled.setIsParent(RuleField.ParentEnum.NO.getCode());
        ruleFiled.setCreatedTime(new Date());
        ruleFiled.setUpdatedTime(new Date());
        log.info("is_parent:"+ruleFiled.getIsParent());
        Integer save = ruleFieldDao.save(ruleFiled);
        Assert.assertNotNull(save);
        log.info("【RuleFiled保存测试】保存后的KEY：{}",ruleFiled.getId());

    }

    @Test
    public void deleteByName(){
        List<Integer> integers  = new ArrayList<>();
        integers.add(4);
        integers.add(7);

        ruleFieldDao.deleteBatch(new Integer[]{3,5});

    }

    @Test
    public void select(){
        RuleField ruleFiled = new RuleField();
        ruleFiled.setFieldName("test");
        List<RuleField> select = ruleFieldDao.select(ruleFiled);
        Assert.assertNotNull(select);
        log.info("【RuleFiled查询测试】查询结果：{}",select);

    }

    @Test
    public void update(){
        RuleField condition = new RuleField();
        condition.setId(4);
        RuleField ruleFiled = new RuleField();
        ruleFiled.setFieldName("test");
        ruleFiled.setFieldDesc("testsssss");
        ruleFieldDao.update(ruleFiled,condition);

    }
}