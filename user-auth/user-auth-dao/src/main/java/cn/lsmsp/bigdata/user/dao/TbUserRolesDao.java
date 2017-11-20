package cn.lsmsp.bigdata.user.dao;

import cn.lsmsp.common.pojo.user.TbUserRoles;
import cn.lsmsp.common.utils.SqlCondition;
import cn.lsmsp.common.utils.SqlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;

import java.util.Set;

@Mapper
@Component
public interface TbUserRolesDao {

    @SelectProvider(type= SqlProvider.class,method = "select2")
    Set<TbUserRoles> getRoles(SqlCondition sqlCondition);

}
