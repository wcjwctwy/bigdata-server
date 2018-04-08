package cn.lsmsp.bigdata.check.policy.pojo;

import lombok.Getter;

@Getter
public enum StatusEnum {
    USING(2, "使用中"), NOT_USING(1, "不使用"), WAITING_DELETE(0, "待删除");
    private Integer code;
    private String desc;

    StatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}