package cays.spring.config;

import cays.spring.vo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用户信息配置
 *
 * @author Chai yansheng
 * @create 2019-07-24 16:38
 **/
@Configuration
public class UserConfig {

    @Bean
    User getUser() {
        return new User();
    }

}
