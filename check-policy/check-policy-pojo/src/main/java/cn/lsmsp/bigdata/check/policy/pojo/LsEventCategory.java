package cn.lsmsp.bigdata.check.policy.pojo;

import cn.lsmsp.common.utils.PojoUtils;

import java.io.Serializable;
import java.util.Date;

public class LsEventCategory implements Serializable{
    private Integer id;
    private String name;
    private String zhName;
    private String description;
    private Integer entId;
    private Integer status;
    private Integer creatuser;
    private Integer updateuser;
    private Date createtime;
    private Date updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZhName() {
        return zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEntId() {
        return entId;
    }

    public void setEntId(Integer entId) {
        this.entId = entId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreatuser() {
        return creatuser;
    }

    public void setCreatuser(Integer creatuser) {
        this.creatuser = creatuser;
    }

    public Integer getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(Integer updateuser) {
        this.updateuser = updateuser;
    }

    public String getCreatetime() {
        return PojoUtils.date2String(createtime);
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return PojoUtils.date2String(updatetime);
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}
