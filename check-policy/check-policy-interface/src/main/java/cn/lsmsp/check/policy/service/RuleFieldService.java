package cn.lsmsp.check.policy.service;

import cn.lsmsp.bigdata.check.policy.pojo.RuleField;

import java.util.List;

/**
 * @author WangCongJun
 * @date 2018/3/21
 * Created by WangCongJun on 2018/3/21.
 */
public interface RuleFieldService {

    /**
     * 保存字段
     * @param ruleFiled
     */

    void saveField(RuleField ruleFiled);

    /**
     * 删除字段
     * @param ruleFiled
     */

    void deleteField(RuleField ruleFiled);

    /**
     * 通过更新字段
     * @param ruleFiled
     */

    void UpdateFieldById(Integer id,RuleField ruleFiled);

    /**
     * 查询父节点字段
     *
     */

    List<RuleField> selectParentField();

    /**
     * 查询某个父节点大的子节点字段
     * @return
     */
    List<RuleField> selectChildrenFieldByParentId(Integer parentId);
}
