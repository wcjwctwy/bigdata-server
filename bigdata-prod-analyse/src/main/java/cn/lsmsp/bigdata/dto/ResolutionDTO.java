package cn.lsmsp.bigdata.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author WangCongJun
 * @date 2018/3/7
 * Created by WangCongJun on 2018/3/7.
 */
@Data
public class ResolutionDTO {
    /**
     * 查询解析率的分组字段
     */
    private String field;

    /**
     * 日志总数量
     */
    private Long sumCount;

    /**
     * 已解析日志数量
     */
    private Long resolveCount=0L;

    /**
     * 日志解析率
     */
    private Double resolution=0D;
}
