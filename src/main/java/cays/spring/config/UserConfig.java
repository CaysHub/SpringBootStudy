package cays.spring.config;

import cays.spring.filter.MyHttpFilter;
import cays.spring.filter.ParamsFilter;
import cays.spring.vo.User;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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

    /*@Bean
    public FilterRegistrationBean registerMyFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new MyHttpFilter());
        registration.addUrlPatterns("/*");
        registration.setName("MyHttpFilter");
        registration.setOrder(1);
        return registration;
    }*/

    @Bean
    public FilterRegistrationBean registerParamsFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ParamsFilter());
        registration.addUrlPatterns("/*");
        registration.setName("ParamsFilter");
        registration.setOrder(1);
        return registration;
    }

}
