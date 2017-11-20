package cn.lsmsp.bigdata.check.policy.dao.analyse;

import cn.lsmsp.common.pojo.analyse.TbEventAnalyse;
import cn.lsmsp.common.utils.SqlCondition;
import cn.lsmsp.common.utils.SqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EventAnalyseDao {

    @InsertProvider(type = SqlProvider.class,method = "insert")
    void saveEvent(TbEventAnalyse eventAnalyse);

    @SelectProvider(type = SqlProvider.class,method = "group")
    List<TbEventAnalyse> getGroupResults(SqlCondition condition);

    @Select("select cus_id , sum(total) as total from tb_event_analyse where year=#{year} and month=#{month} and day=#{day} group by cus_id ")
    List<TbEventAnalyse> getResultsGroupByCusId(Integer year,Integer month,Integer day);
    @Select("select event_category , sum(total) as total from tb_event_analyse where year=#{year} and month=#{month} and day=#{day} group by event_category ")
    List<TbEventAnalyse> getResultsGroupByCate(Integer year,Integer month,Integer day);
    @Select("select event_category_technique , sum(total) as total from tb_event_analyse where year=#{year} and month=#{month} and day=#{day} group by event_category_technique ")
    List<TbEventAnalyse> getResultsGroupBySubCate(Integer year,Integer month,Integer day);
    @Select("select ent_id , sum(total) as total from tb_event_analyse where year=#{year} and month=#{month} and day=#{day} group by ent_id ")
    List<TbEventAnalyse> getResultsGroupByEntId(Integer year,Integer month,Integer day);
    @Select("select event_level , sum(total) as total from tb_event_analyse where year=#{year} and month=#{month} and day=#{day} group by event_level ")
    List<TbEventAnalyse> getResultsGroupByLevel(Integer year,Integer month,Integer day);
    @Select("select category_device , sum(total) as total from tb_event_analyse where year=#{year} and month=#{month} and day=#{day} group by category_device ")
    List<TbEventAnalyse> getResultsGroupByDevice(Integer year,Integer month,Integer day);

    @Select("select ent_id,day,sum(total) as total " +
            "from tb_event_analyse where year=#{year} and month= #{month}  GROUP BY ent_id ,day")
    List<TbEventAnalyse> getTimeLineEntidResultsGroupByDay(@Param("year") Integer year,@Param("month") Integer month);
}
