package cn.lsmsp.bigdata.check.policy.dao;

import cn.lsmsp.bigdata.check.policy.pojo.RuleField;
import cn.lsmsp.common.utils.SqlUtils;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author WangCongJun
 * @date 2018/3/20
 * Created by WangCongJun on 2018/3/20.
 */
@Mapper
@Component
public interface RuleFieldDao {
    /**
     * 存储字段
     * @param ruleFiled
     * @return 是否成功
     */
    @InsertProvider(type = RuleFieldProvider.class,method = "insert")
    @Options(useGeneratedKeys = true,keyProperty = "ruleFiled.id")
    Integer save(@Param("ruleFiled") RuleField ruleFiled);

    /**
     * 根据字段ID删除
     * @param id
     */
    @Delete("delete from tb_rule_field where id = #{id}")
    void deleteById(@Param("id") Integer id);

    /**
     * 根据ID批量删除
     * @param ids
     */
    @DeleteProvider(type = RuleFieldProvider.class,method = "deleteBatch")
    void deleteBatch(@Param("ids") Object[] ids);
    /**
     * 更新字段
     * @param ruleFiled
     */
    @UpdateProvider(type = RuleFieldProvider.class,method = "update")
    void update(@Param("ruleFiled") RuleField ruleFiled, @Param("condition") RuleField condition);

    /**
     * 根据传入的条件查询
     * @param ruleFiled
     * @return
     */
    @SelectProvider(type = RuleFieldProvider.class,method = "select")
    List<RuleField> select(@Param("ruleFiled") RuleField ruleFiled);

    class RuleFieldProvider{

        private String tableName="tb_rule_field";
        public String insert(@Param("ruleFiled") RuleField ruleFiled){
            return new SQL(){{
                INSERT_INTO(tableName);
                INTO_COLUMNS("field_name","field_type","field_desc","is_parent","parent_id","status","created_time","updated_time");
                INTO_VALUES("#{ruleFiled.fieldName},#{ruleFiled.fieldType},#{ruleFiled.fieldDesc},#{ruleFiled.isParent}" +
                        ",#{ruleFiled.parentId},#{ruleFiled.status},#{ruleFiled.createdTime},#{ruleFiled.updatedTime}");
            }}.toString();
        }

        public String deleteBatch(@Param("ids") Object[] ids){
            return new SQL(){{
                DELETE_FROM(tableName);
                StringBuilder condition=new StringBuilder("id in (");
                for(int i = 0;i<ids.length;i++){
                    condition.append("#{ids["+i+"]},") ;
                }
                String substring = condition.substring(0, condition.length() - 1)+")";
                WHERE(substring);
            }}.toString();
        }


        public String update(@Param("ruleFiled") RuleField ruleFiled, @Param("condition") RuleField condition) throws Exception{
            return new SQL(){{
                UPDATE(tableName);
                SET(SqlUtils.getConditions(ruleFiled,"param1"));
                WHERE(SqlUtils.getConditions(condition,"param2"));
            }}.toString();
        }


        public String select(@Param("ruleFiled") RuleField ruleFiled) throws Exception{

            return new SQL(){{
                SELECT("*");
                FROM(tableName);
                WHERE(SqlUtils.getConditions(ruleFiled,"param1"));
            }}.toString();
        }




    }
}
