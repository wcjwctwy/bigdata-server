package cn.lsmsp.bigdata.check.policy.pojo;

import java.io.Serializable;

public class TbEventRule implements Serializable {
    private Integer id;
    private Integer eventId;
    private String eventName;
    private Integer pluginId;
    private String eventCategory;
    private String eventCategoryTechnique;
    private String eventExp;
    private String remark;
    private Integer categoryId;
    private Integer subCategoryId;
    private Integer reliability;
    private Integer priority;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Integer getPluginId() {
        return pluginId;
    }

    public void setPluginId(Integer pluginId) {
        this.pluginId = pluginId;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getEventCategoryTechnique() {
        return eventCategoryTechnique;
    }

    public void setEventCategoryTechnique(String eventCategoryTechnique) {
        this.eventCategoryTechnique = eventCategoryTechnique;
    }

    public String getEventExp() {
        return eventExp;
    }

    public void setEventExp(String eventExp) {
        this.eventExp = eventExp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Integer subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public Integer getReliability() {
        return reliability;
    }

    public void setReliability(Integer reliability) {
        this.reliability = reliability;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
