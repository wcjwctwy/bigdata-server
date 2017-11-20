package cn.lsmsp.bigdata.check.policy.dao;

import cn.lsmsp.bigdata.check.policy.pojo.LsEventCategory;
import cn.lsmsp.common.utils.SqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface LsEventCategoryDao {

    @Select("select * from ls_event_category where name=#{name}")
    LsEventCategory getEventCategoryByName(@Param("name") String name);

    @Select("select * from ls_event_category where id=#{id}")
    LsEventCategory getEventCategoryById(@Param("id")Integer id);

    @Select("select * from ls_event_category")
    List<LsEventCategory> getEventCategories();

    @InsertProvider(type = SqlProvider.class ,method = "insert")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void addEventCategory(LsEventCategory lsEventCategory);

    @UpdateProvider(type = SqlProvider.class ,method = "update")
    void updateEventCategory(@Param("obj") LsEventCategory lsEventCategory,@Param("condition") String condition);

    @Delete("delete from ls_event_category where id=#{id}")
    void dejEventCategory(@Param("id") Integer id);
}
