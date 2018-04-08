package cn.lsmsp.bigdata.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.hadoop.hbase.HbaseConfigurationFactoryBean;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.HbaseUtils;

/**
 * @author WangCongJun
 * @date 2018/3/5
 * Created by WangCongJun on 2018/3/5.
 */
@Configuration
@PropertySources({@PropertySource("classpath:/config/solr.properties")})
@Slf4j
public class AnalyseDatasourceConfig {

    @Autowired
    private HbaseConfig hbaseConfig;

    @Bean
    public HbaseTemplate getHbaseConfig(){
        log.info("【HBASE配置】HbaseConfig：{}",hbaseConfig);
        org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.property.clientPort", hbaseConfig.getClientPort());
        configuration.set("hbase.zookeeper.quorum", hbaseConfig.getQuorum());
        configuration.set("hbase.master", hbaseConfig.getMaster());

        return  new HbaseTemplate(configuration);
    }
}
