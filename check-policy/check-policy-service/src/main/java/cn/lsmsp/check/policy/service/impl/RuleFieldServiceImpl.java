package cn.lsmsp.check.policy.service.impl;

import cn.lsmsp.bigdata.check.policy.dao.RuleFieldDao;
import cn.lsmsp.bigdata.check.policy.pojo.RuleField;
import cn.lsmsp.check.policy.service.RuleFieldService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WangCongJun
 * @date 2018/3/21
 * Created by WangCongJun on 2018/3/21.
 */
@Service
@Slf4j
public class RuleFieldServiceImpl implements RuleFieldService {

    @Autowired
    private RuleFieldDao ruleFieldDao;

    @Override
    public void saveField(RuleField ruleFiled) {
        Assert.isNull(ruleFiled.getId(), "Id不能有值");
        log.info("【存储字段】字段parentId：{}",ruleFiled.getParentId());
        if (RuleField.ParentEnum.NO.getCode().equals(ruleFiled.getIsParent())&&ruleFiled.getParentId()!=0) {
            Assert.notNull(ruleFiled.getParentId(), "子节点必须有父节点");
        }
        Date date = new Date();
        ruleFiled.setCreatedTime(date);
        ruleFiled.setUpdatedTime(date);
        ruleFieldDao.save(ruleFiled);
    }

    @Override
    public void deleteField(RuleField ruleField) {
        //判断是否为父节点
        if (RuleField.ParentEnum.YES.getCode().equals(ruleField.getIsParent())) {
            log.info("【删除字段类别】ruleField类别信息:{}",ruleField);
            ruleFieldDao.deleteById(ruleField.getId());
            //删除所有子节点
            List<Integer> collect = selectChildrenFieldByParentId(ruleField.getId()).stream().map(r -> {
                        Integer id = r.getId();
                        return id;
                    }
            ).collect(Collectors.toList());
            //字段信息为空则不处理
            if(!ObjectUtils.isEmpty(collect)) {
                Object[] objects = collect.toArray();
                log.info("【删除字段类别】{}包含的字段信息:{}", ruleField.getFieldName(), Arrays.toString(objects));
                ruleFieldDao.deleteBatch(objects);
            }
        }else {
            log.info("【删除字段信息】ruleField信息:{}",ruleField);
            //直接删除子节点字段
            ruleFieldDao.deleteById(ruleField.getId());
        }
    }

    @Override
    public void UpdateFieldById(Integer id,RuleField ruleFiled) {
        RuleField condition = new RuleField();
        condition.setId(id);
        ruleFieldDao.update(ruleFiled,condition);
    }

    @Override
    public List<RuleField> selectParentField() {
        RuleField ruleFiled = new RuleField();
        ruleFiled.setIsParent(RuleField.ParentEnum.YES.getCode());
        return ruleFieldDao.select(ruleFiled);
    }

    @Override
    public List<RuleField> selectChildrenFieldByParentId(Integer parentId) {
        RuleField ruleFiled = new RuleField();
        ruleFiled.setParentId(parentId);
        return ruleFieldDao.select(ruleFiled);
    }
}
