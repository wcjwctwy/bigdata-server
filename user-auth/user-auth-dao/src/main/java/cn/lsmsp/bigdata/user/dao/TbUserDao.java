package cn.lsmsp.bigdata.user.dao;

import cn.lsmsp.common.pojo.user.TbUser;
import cn.lsmsp.common.utils.SqlCondition;
import cn.lsmsp.common.utils.SqlProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TbUserDao {
    @SelectProvider(type = SqlProvider.class,method = "select2")
    List<TbUser> getUsers(SqlCondition sqlCondition);

    @InsertProvider(type = SqlProvider.class,method = "insert")
    void saveUser(TbUser user);
}
