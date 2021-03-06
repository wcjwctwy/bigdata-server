package cn.lsmsp.check.policy.service;

import cn.lsmsp.bigdata.check.policy.pojo.TbRulerGroup;

import java.util.List;

public interface RulerGroupService {

    List<TbRulerGroup> getAllGroups();


    TbRulerGroup getGroupByGroupName(String groupName);

    void addGroup(TbRulerGroup tbRulerGroup);

    void delGroupById(Integer id);

    void updateGroupById(TbRulerGroup tbRulerGroup);


}
