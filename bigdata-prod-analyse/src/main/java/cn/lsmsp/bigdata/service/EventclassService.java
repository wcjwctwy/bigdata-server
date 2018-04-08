package cn.lsmsp.bigdata.service;

import cn.lsmsp.bigdata.dto.ResolutionDTO;
import cn.lsmsp.bigdata.entity.EventAnalyse;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/3/5
 * Created by WangCongJun on 2018/3/5.
 */
public interface EventclassService {
    String entid="entId";
    String cusid="assetId";
    String cate="eventCategory";
    String subcate="eventCategoryTechnique";
    String level="eventLevel";
    String device="categoryDevice";

    List<EventAnalyse> getStatResult(String groupFields) throws Exception;
    List<EventAnalyse> getStatResult(EventAnalyse eventAnalyse,String groupFields)throws Exception;
    List<Map<String , Long>> getStatResults(EventAnalyse eventAnalyse)  throws Exception;
    List<ResolutionDTO> getResolution(EventAnalyse eventAnalyse, String groupFields);
    Map<String , Long> getStatResults(EventAnalyse eventAnalyse, String groupField)throws Exception;
    Map<Long , long[]> getTimeLineStatResultsByMonth(EventAnalyse eventAnalyse);
}
