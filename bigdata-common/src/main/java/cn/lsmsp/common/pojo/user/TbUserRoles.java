package cn.lsmsp.common.pojo.user;

import cn.lsmsp.common.utils.PojoUtils;

import java.io.Serializable;
import java.util.Date;

public class TbUserRoles implements Serializable {
    private Integer id;
    private String roleName;
    private String username;
    private Date createdTime;
    private Date updatedTime;

    public String getCreatedTime() {
        return PojoUtils.date2String(createdTime);
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
