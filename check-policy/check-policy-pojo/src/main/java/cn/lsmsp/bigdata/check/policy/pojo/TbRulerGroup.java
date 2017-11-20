package cn.lsmsp.bigdata.check.policy.pojo;

import cn.lsmsp.common.utils.PojoUtils;

import java.util.Date;

public class TbRulerGroup {
    private Integer id;
    private String groupName;
    private Integer pluginCode;
    private Date createdTime;
    private Date updatedTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getPluginCode() {
        return pluginCode;
    }

    public void setPluginCode(Integer pluginCode) {
        this.pluginCode = pluginCode;
    }

    public String getCreatedTime() {
        return  PojoUtils.date2String(createdTime);
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedTime() {
        return PojoUtils.date2String(updatedTime);
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}
