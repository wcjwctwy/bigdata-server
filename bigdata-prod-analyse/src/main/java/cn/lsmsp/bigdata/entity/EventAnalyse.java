package cn.lsmsp.bigdata.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author WangCongJun
 * @date 2018/3/5
 * Created by WangCongJun on 2018/3/5.
 */
@Data
public class EventAnalyse {
    private String id;
    private Long entId;
    private Long assetId;
    private String eventCategory;
    private String eventCategoryTechnique;
    private String eventLevel;
    private String eventName;
    private String categoryDevice;
    private String deviceAddress;
    private Short isAnalyzerEvent;
    private Long eventCount;
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer hour;
    private Integer min;
}
