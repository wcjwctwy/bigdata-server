package cn.lsmsp.bigdata.solr.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config/solr.properties")
//@PropertySource("file:/home/bdstat/config/solr.properties")
public class SolrConfig {

}
