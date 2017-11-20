package cn.lsmsp.common.pojo.user;

import cn.lsmsp.common.utils.PojoUtils;

import java.io.Serializable;
import java.util.Date;

public class TbRolesPermissions implements Serializable{
    private Integer id;
    private String roleName;
    private String permisson;
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

    public String getPermisson() {
        return permisson;
    }

    public void setPermisson(String permisson) {
        this.permisson = permisson;
    }
}
