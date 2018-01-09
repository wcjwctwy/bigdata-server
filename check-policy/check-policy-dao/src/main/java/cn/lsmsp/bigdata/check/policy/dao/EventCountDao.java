package cn.lsmsp.bigdata.check.policy.dao;

import cn.lsmsp.bigdata.check.policy.pojo.TbEventclassMin;
import cn.lsmsp.common.utils.SqlProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface EventCountDao {

    @InsertProvider(type=SqlProvider.class,method = "insert")
    void save(TbEventclassMin eventCount);

}
