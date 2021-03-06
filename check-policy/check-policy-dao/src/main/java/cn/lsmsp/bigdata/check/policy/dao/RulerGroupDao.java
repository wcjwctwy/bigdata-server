package cn.lsmsp.bigdata.check.policy.dao;

import cn.lsmsp.bigdata.check.policy.pojo.TbRulerGroup;
import cn.lsmsp.common.utils.SqlCondition;
import cn.lsmsp.common.utils.SqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RulerGroupDao {

    @SelectProvider(type= SqlProvider.class,method = "select2")
    List<TbRulerGroup> getGroups(SqlCondition sqlCondition);

    @InsertProvider(type= SqlProvider.class,method = "insert")
    void addGroup(TbRulerGroup tbRulerGroup);

    @UpdateProvider(type= SqlProvider.class,method = "update2")
    void updataGroup(SqlCondition sqlCondition);

    @Delete("delete from tb_ruler_group where id=#{tbRulerGroup.id}")
    void delGroup(@Param("tbRulerGroup") TbRulerGroup tbRulerGroup);
}
