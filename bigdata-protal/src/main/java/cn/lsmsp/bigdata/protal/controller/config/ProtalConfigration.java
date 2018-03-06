package cn.lsmsp.bigdata.protal.controller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author WangCongJun
 * @date 2018/3/6
 * Created by WangCongJun on 2018/3/6.
 */
@Configuration
public class ProtalConfigration {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
