package cn.lsmsp.bigdata.check.policy.dao;

import cn.lsmsp.bigdata.check.policy.pojo.TbEventRule;
import cn.lsmsp.common.utils.SqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface EventRulerTransDao {

    @Select("select * from tb_event_rule where plugin_id = #{pluginId}")
    List<TbEventRule> getEventRulerTrans(@Param("pluginId") Integer pluginId);

    @Update("update tb_event_rule set remark=#{remark} where event_id=#{eventId} and plugin_id=#{pluginId}")
    void updateEventRulerTransRemark(@Param("remark") String remark,@Param("pluginId") Integer pluginId,@Param("eventId")Integer eventId);

    @UpdateProvider(type = SqlProvider.class,method = "update")
    void updateEventRulerTrans(@Param("obj") TbEventRule tbEventRule,@Param("condition") String condition);

    @Select("select * from tb_event_rule where event_id=#{eventId} and plugin_id=#{pluginId}")
    TbEventRule getEventRulerTransByEventIdAndPluginId(@Param("eventId") Integer eventId,@Param("pluginId") Integer pluginId);

    @SelectProvider(type=SqlProvider.class,method = "select")
    TbEventRule getEventRule(TbEventRule tbEventRule);

    @InsertProvider(type = SqlProvider.class,method = "insert")
    void saveEventRulerTrans(TbEventRule tbEventRule);

    @Select("select MAX(EVENT_ID) from tb_event_rule where plugin_id=#{pluginId}")
    Integer getMaxEventId(Integer pluginId);
}
