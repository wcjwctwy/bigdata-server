package cn.lsmsp.bigdata.dao;

import cn.lsmsp.bigdata.entity.EventAnalyse;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

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

    @SelectProvider(type = Provider.class, method = "group")
    List<EventAnalyse> getGroupResults(@Param("eventAnalyse") EventAnalyse eventAnalyse, @Param("groupFileds") String groupFileds);

    @SelectProvider(type = Provider.class, method = "getSum")
    Long getSum(@Param("eventAnalyse")EventAnalyse eventAnalyse);

    @SelectProvider(type = Provider.class, method = "findOne")
    EventAnalyse findOne(EventAnalyse eventAnalyse);

    class Provider {
        public String group(@Param("eventAnalyse") EventAnalyse eventAnalyse, @Param("groupFileds") String groupFileds) {
            SQL sql = new SQL();
            sql.SELECT(groupFileds, "sum(eventCount) as eventCount");
            sql.FROM("tb_eventclass_min");
            sql = getWhereConditions(sql,eventAnalyse);
            sql.GROUP_BY(groupFileds);
            return sql.toString();
        }

        public String getSum(@Param("eventAnalyse")EventAnalyse eventAnalyse) {
            SQL sql = new SQL();
            sql.SELECT("sum(eventCount)");
            sql.FROM("tb_eventclass_min");
            sql = getWhereConditions(sql, eventAnalyse);
            return sql.toString() + " limit 1";
        }

        public String findOne(EventAnalyse eventAnalyse) {
            return new SQL() {{
                SELECT("*");
                FROM("tb_eventclass_min");
                WHERE("entId=#{entId}");

            }}.toString() + " limit 1";
        }

        private SQL getWhereConditions(SQL sql,Object eventAnalyse){
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
    }
}
