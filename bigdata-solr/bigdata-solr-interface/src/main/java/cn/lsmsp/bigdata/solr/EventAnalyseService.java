package cn.lsmsp.bigdata.solr;

import cn.lsmsp.bigdata.pojo.LsEvent;
import cn.lsmsp.common.pojo.analyse.TbEventAnalyse;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface EventAnalyseService {

    String entid="ent_id";
    String cusid="cus_id";
    String cate="event_category";
    String subcate="event_category_technique";
    String level="event_level";
    String device="category_device";

    List<TbEventAnalyse> getStatResult(String groupFields);
    List<TbEventAnalyse> getStatResult(TbEventAnalyse eventAnalyse,String groupFields)throws Exception;
    List<Map<String , Long>> getStatResults(TbEventAnalyse eventAnalyse);
    List<Map<String , Long>> getStatResults(TbEventAnalyse eventAnalyse, Date date);
    Map<Long , long[]> getTimeLineStatResultsByMonth(TbEventAnalyse eventAnalyse);

}
