package cn.lsmsp.bigdata.user.config;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

    @Bean
    public PasswordService getPasswordService(){
        return new DefaultPasswordService();
    }

}
