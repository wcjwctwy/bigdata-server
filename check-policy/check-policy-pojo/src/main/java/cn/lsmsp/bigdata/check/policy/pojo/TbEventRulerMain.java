package cn.lsmsp.bigdata.check.policy.pojo;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;

public class TbEventRulerMain implements Serializable {
    private Integer id;
    private String rulerContent;
    private Integer pluginCode;
    private String regex;
    private String rulerName;
    private String groupName;
    private Date createdTime;
    private Date updatedTime;
    private Integer status;
    private Integer priority;

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRulerContent() {
        return rulerContent;
    }

    public void setRulerContent(String rulerContent) {
        this.rulerContent = rulerContent;
    }

    public Integer getPluginCode() {
        return pluginCode;
    }

    public void setPluginCode(Integer pluginCode) {
        this.pluginCode = pluginCode;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getRulerName() {
        return rulerName;
    }

    public void setRulerName(String rulerName) {
        this.rulerName = rulerName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCreatedTime() {
        String format = null;
        if(createdTime!=null){
            format = DateFormatUtils.format(createdTime, "yyyy-MM-dd HH:mm:ss");
        }
        return format;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String  getUpdatedTime() {
        String format = null;
        if(updatedTime!=null){
            format = DateFormatUtils.format(updatedTime, "yyyy-MM-dd HH:mm:ss");
        }
        return format;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }


    @Override
    public String toString() {
        return "TbEventRuler{" +
                "id=" + id +
                ", rulerContent='" + rulerContent + '\'' +
                ", pluginCode=" + pluginCode +
                ", regex='" + regex + '\'' +
                ", rulerName='" + rulerName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", status=" + status +
                '}';
    }
}
