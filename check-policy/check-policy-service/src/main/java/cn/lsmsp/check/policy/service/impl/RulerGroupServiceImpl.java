package cn.lsmsp.check.policy.service.impl;

import cn.lsmsp.bigdata.check.policy.dao.RulerGroupDao;
import cn.lsmsp.bigdata.check.policy.pojo.TbRulerGroup;
import cn.lsmsp.check.policy.service.RulerGroupService;
import cn.lsmsp.common.utils.SqlCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class RulerGroupServiceImpl implements RulerGroupService {

    @Autowired
    private RulerGroupDao rulerGroupDao;
    @Override
    public List<TbRulerGroup> getAllGroups() {
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.setObj(new TbRulerGroup());
        sqlCondition.setOrder("order by plugin_code");
        List<TbRulerGroup> groups = rulerGroupDao.getGroups(sqlCondition);
        return groups;
    }

    @Override
    public TbRulerGroup getGroupByGroupName(String groupName) {
        SqlCondition sqlCondition = new SqlCondition();
        TbRulerGroup tbRulerGroup = new TbRulerGroup();
        tbRulerGroup.setGroupName(groupName);
        sqlCondition.setObj(tbRulerGroup);
        List<TbRulerGroup> groups = rulerGroupDao.getGroups(sqlCondition);
        return groups.get(0);
    }

    @Override
    public void addGroup(TbRulerGroup tbRulerGroup) {
        if(StringUtils.isEmpty(tbRulerGroup.getGroupName())){
            throw new RuntimeException("GroupName can't be null!!!");
        }
        if(StringUtils.isEmpty(tbRulerGroup.getPluginCode())){
            throw new RuntimeException("PluginCode can't be null!!!");
        }
        Date date = new Date();
        tbRulerGroup.setCreatedTime(date);
        tbRulerGroup.setUpdatedTime(date);
        rulerGroupDao.addGroup(tbRulerGroup);
    }

    @Override
    public void delGroupById(Integer id) {
        TbRulerGroup tbRulerGroup = new TbRulerGroup();
        tbRulerGroup.setId(id);
        rulerGroupDao.delGroup(tbRulerGroup);
    }

    @Override
    public void updateGroupById(TbRulerGroup tbRulerGroup) {
        Date date = new Date();
        tbRulerGroup.setUpdatedTime(date);
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.setObj(tbRulerGroup);
        sqlCondition.setWhere("id="+tbRulerGroup.getId());
        rulerGroupDao.updataGroup(sqlCondition);
    }
}
