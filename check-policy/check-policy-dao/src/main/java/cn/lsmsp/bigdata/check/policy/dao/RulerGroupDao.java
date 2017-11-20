package cn.lsmsp.bigdata.check.policy.dao;

import cn.lsmsp.bigdata.check.policy.pojo.TbRulerGroup;
import cn.lsmsp.common.utils.SqlCondition;
import cn.lsmsp.common.utils.SqlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RulerGroupDao {

    @SelectProvider(type= SqlProvider.class,method = "select2")
    List<TbRulerGroup> getGroups(SqlCondition sqlCondition);

}
