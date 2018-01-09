package cn.lsmsp.bigdata.check.policy.dao;

import cn.lsmsp.bigdata.check.policy.pojo.TbEventRulerMain;
import cn.lsmsp.bigdata.check.policy.utils.EventRulerSqlProvider;
import cn.lsmsp.common.utils.SqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface EventRulerMainDao {

    @InsertProvider(type = EventRulerSqlProvider.class,method = "insert")
    void saveRuler(TbEventRulerMain tbEventRuler);

    @Select("select * from tb_event_ruler_main order by plugin_code,priority")
    List<TbEventRulerMain> getAllRulers();

    @Select("select * from tb_event_ruler_main where id=#{id}")
    TbEventRulerMain getRulerById(@Param("id") Integer id);

    @UpdateProvider(type= EventRulerSqlProvider.class,method = "update")
    void updateRuler(@Param("obj") TbEventRulerMain tbEventRulerMain,@Param("condition") String condition);

    @Select("select count(id) from tb_event_ruler_main")
    Integer getRulerCount();

    @Select("select group_name , plugin_code from tb_event_ruler_main group by plugin_code,group_name")
    List<TbEventRulerMain> getRulerGroup();

    @DeleteProvider(type=SqlProvider.class,method = "delete")
    void delRuler(TbEventRulerMain tbEventRulerMain);


}
