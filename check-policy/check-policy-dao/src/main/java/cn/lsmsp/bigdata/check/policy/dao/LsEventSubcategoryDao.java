package cn.lsmsp.bigdata.check.policy.dao;

import cn.lsmsp.bigdata.check.policy.pojo.LsEventSubcategory;
import cn.lsmsp.common.utils.SqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface LsEventSubcategoryDao {

    @Select("select * from ls_event_subcategory where name=#{name}")
    LsEventSubcategory getEventSubcategoryByName(@Param("name") String name);

    @Select("select * from ls_event_subcategory where name=#{name} and category_id=#{categoryId}")
    LsEventSubcategory getEventSubcategoryByNameAndCategoryId(@Param("name") String name,@Param("categoryId") Integer categoryId);

    @Select("select * from ls_event_subcategory where id=#{id}")
    LsEventSubcategory getEventSubcategoryById(@Param("id")Integer id);

    @Select("select * from ls_event_subcategory")
    List<LsEventSubcategory> getEventSubcategories();

    @Select("select * from ls_event_subcategory where category_id=#{categoryId}")
    List<LsEventSubcategory> getEventSubcategoriesByCategoryId(@Param("categoryId") Integer categoryId);

    @InsertProvider(type = SqlProvider.class ,method = "insert")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void addEventSubcategory(LsEventSubcategory lsEventSubcategory);

    @UpdateProvider(type = SqlProvider.class ,method = "update")
    void updateEventSubcategory(@Param("obj") LsEventSubcategory lsEventSubcategory,@Param("condition") String condition);

    @Delete("delete from ls_event_subcategory where id=#{id}")
    void dejEventSubcategory(@Param("id") Integer id);

    @Select("select count(id) from ls_event_subcategory where category_id=#{categoryId}")
    Integer getSubCountByCateoryId(@Param("categoryId") Integer categoryId);
}
