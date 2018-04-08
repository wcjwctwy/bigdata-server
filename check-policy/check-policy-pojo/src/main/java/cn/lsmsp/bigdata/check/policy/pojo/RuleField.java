package cn.lsmsp.bigdata.check.policy.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 规则字段
 * @author WangCongJun
 * @date 2018/3/20
 * Created by WangCongJun on 2018/3/20.
 */
@Data
public class RuleField {

    @Getter
    public enum ParentEnum{
        YES(1,"是父节点"),NO(0,"不是父节点");
        private Integer code;
        private String desc;
        ParentEnum(Integer code,String desc){
            this.code=code;
            this.desc=desc;
        }
    }
    private Integer id;
    private String fieldName;
    /**
     * 字段状态2 为可用 1为不可用 0待删除
     */
    private Integer status;
    private String fieldType;
    private String fieldDesc;
    /**
     * 是否为父节点
     * 1：是的
     * 0；不是
     */
    private Integer isParent;
    private Integer parentId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;
}
