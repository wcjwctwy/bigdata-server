package cn.lsmsp.bigdata.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author WangCongJun
 * @date 2018/3/30
 * Created by WangCongJun on 2018/3/30.
 */
@ConfigurationProperties(prefix = "hbase")
@Component
@Data
public class HbaseConfig {
    private String clientPort;
    private String quorum;
    private String master;
}
