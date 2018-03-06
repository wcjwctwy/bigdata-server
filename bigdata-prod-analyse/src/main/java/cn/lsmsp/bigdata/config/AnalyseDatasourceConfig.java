package cn.lsmsp.bigdata.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * @author WangCongJun
 * @date 2018/3/5
 * Created by WangCongJun on 2018/3/5.
 */
@Configuration
@PropertySources({@PropertySource("classpath:/config/solr.properties")})
public class AnalyseDatasourceConfig {
}
