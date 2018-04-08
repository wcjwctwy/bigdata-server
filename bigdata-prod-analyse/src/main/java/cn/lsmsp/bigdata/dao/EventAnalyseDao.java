package cn.lsmsp.bigdata.dao;

import cn.lsmsp.bigdata.dto.ResolutionDTO;
import cn.lsmsp.bigdata.entity.EventAnalyse;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.util.List;

/**
 * @author WangCongJun
 * @date 2018/3/6
 * Created by WangCongJun on 2018/3/6.
 */
@Mapper
@Component
public interface EventAnalyseDao {


    /**
     * 根据传入的条件和分组字段来进行分组查询
     *
     * @param eventAnalyse
     * @param groupFileds
     * @return
     */
    @SelectProvider(type = Provider.class, method = "group")
    List<EventAnalyse> getGroupResults(@Param("eventAnalyse") EventAnalyse eventAnalyse, @Param("groupFileds") String groupFileds);

    /**
     * 查询特定条件下的事件总数量
     *
     * @param eventAnalyse
     * @return
     */
    @SelectProvider(type = Provider.class, method = "getSum")
    List<EventAnalyse> getSum(@Param("eventAnalyse") EventAnalyse eventAnalyse, @Param("groupFileds") String groupFileds);

    /**
     * 查询指定的一条数据
     *
     * @param eventAnalyse
     * @return
     */
    @SelectProvider(type = Provider.class, method = "findOne")
    EventAnalyse findOne(EventAnalyse eventAnalyse);


    /**
     * 查询解析率
     */
    @SelectProvider(type = Provider.class, method = "getResolution")
    List<ResolutionDTO> getResolution(@Param("eventAnalyse") EventAnalyse eventAnalyse, @Param("field") String field);

    class Provider {

        public String getResolution(@Param("eventAnalyse") EventAnalyse eventAnalyse, @Param("field") String field) {

            String condition = getWhereConditions(eventAnalyse);
            StringBuilder builder = new StringBuilder("select sum_count.");
            builder.append(field).append(" field,sum_count.a sumCount,sum_jx.b resolveCount,sum_jx.b/sum_count.a resolution from")
                    .append(" (select ")
                    .append(field).append(", sum(eventCount) a from tb_eventclass_min where ")
                    .append(condition).append(" group by ").append(field).append(") sum_count ")
                    .append("left join (select ").append(field).append(", sum(eventCount) b from tb_eventclass_min where ")
                    .append(condition).append(" and eventCategory!='Others' ").append(" group by ").append(field).append(")sum_jx ")
                    .append("on sum_count.").append(field).append("=sum_jx.").append(field).append(" order by resolution desc");

            return builder.toString();
        }

        public String group(@Param("eventAnalyse") EventAnalyse eventAnalyse, @Param("groupFileds") String groupFileds) {
            SQL sql = new SQL();
            sql.SELECT(groupFileds, "sum(eventCount) as eventCount");
            sql.FROM("tb_eventclass_min");
            sql = getWhereConditions(sql, eventAnalyse);
            sql.GROUP_BY(groupFileds);
            return sql.toString();
        }

        public String getSum(@Param("eventAnalyse") EventAnalyse eventAnalyse, @Param("groupFileds") String groupFileds) {
            SQL sql = new SQL();
            sql.SELECT("sum(eventCount) eventCount");
            sql.FROM("tb_eventclass_min");
            sql = getWhereConditions(sql, eventAnalyse);
            if(!StringUtils.isEmpty(groupFileds)) {
                sql.GROUP_BY(groupFileds);
            }
            return sql.toString();
        }

        public String findOne(EventAnalyse eventAnalyse) {
            return new SQL() {{
                SELECT("*");
                FROM("tb_eventclass_min");
                WHERE("entId=#{entId}");

            }}.toString() + " limit 1";
        }

        private SQL getWhereConditions(SQL sql, Object eventAnalyse) {
            PropertyDescriptor[] propertyDescriptors = BeanUtilsBean.getInstance().getPropertyUtils().getPropertyDescriptors(eventAnalyse);
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                try {
                    Object invoke = descriptor.getReadMethod().invoke(eventAnalyse);
                    String name = descriptor.getName();

                    if (invoke != null && !ObjectUtils.isEmpty(invoke) && !"class".equals(name)) {
                        sql.WHERE(name + " = #{eventAnalyse." + name + "}");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return sql;
        }

        private String getWhereConditions(Object eventAnalyse) {
            PropertyDescriptor[] propertyDescriptors = BeanUtilsBean.getInstance().getPropertyUtils().getPropertyDescriptors(eventAnalyse);
            StringBuilder builder = new StringBuilder();
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                try {
                    Object invoke = descriptor.getReadMethod().invoke(eventAnalyse);
                    String name = descriptor.getName();

                    if (invoke != null && !ObjectUtils.isEmpty(invoke) && !"class".equals(name)) {
                        builder.append("and ")
                                .append(name)
                                .append(" = #{eventAnalyse.")
                                .append(name)
                                .append("}");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (builder.length() > 0) {
                return builder.substring(3);
            }

            return null;
        }
    }
}
