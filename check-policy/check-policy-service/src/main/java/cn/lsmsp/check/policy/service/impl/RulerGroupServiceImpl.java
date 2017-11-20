package cn.lsmsp.check.policy.service.impl;

import cn.lsmsp.bigdata.check.policy.dao.RulerGroupDao;
import cn.lsmsp.bigdata.check.policy.pojo.TbRulerGroup;
import cn.lsmsp.check.policy.service.RulerGroupService;
import cn.lsmsp.common.utils.SqlCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
