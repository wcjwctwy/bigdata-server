package cn.lsmsp.common.pojo.user;

import cn.lsmsp.common.utils.PojoUtils;

import java.io.Serializable;
import java.util.Date;

public class TbUser implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private String email;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
