package cn.lsmsp.common.pojo.analyse;

import java.io.Serializable;

public class TbEventAnalyse implements Serializable {
    private Long id;
    private String categoryDevice;
    private String eventLevel;
    private Long cusId;
    private Long entId;
    private String eventCategoryTechnique;
    private String eventCategory;
    private Long total;
    private Short year;
    private Short month;
    private Short day;
    

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    public Short getMonth() {
        return month;
    }

    public void setMonth(Short month) {
        this.month = month;
    }

    public Short getDay() {
        return day;
    }

    public void setDay(Short day) {
        this.day = day;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryDevice() {
        return categoryDevice;
    }

    public void setCategoryDevice(String categoryDevice) {
        this.categoryDevice = categoryDevice;
    }

    public String getEventLevel() {
        return eventLevel;
    }

    public void setEventLevel(String eventLevel) {
        this.eventLevel = eventLevel;
    }

    public Long getCusId() {
        return cusId;
    }

    public void setCusId(Long cusId) {
        this.cusId = cusId;
    }

    public Long getEntId() {
        return entId;
    }

    public void setEntId(Long entId) {
        this.entId = entId;
    }

    public String getEventCategoryTechnique() {
        return eventCategoryTechnique;
    }

    public void setEventCategoryTechnique(String eventCategoryTechnique) {
        this.eventCategoryTechnique = eventCategoryTechnique;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }
}
