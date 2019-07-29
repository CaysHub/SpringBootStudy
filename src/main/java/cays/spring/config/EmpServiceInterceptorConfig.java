package cays.spring.config;

import cays.spring.interceptor.EmpServiceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 雇员请求拦截器配置
 *
 * @author Chai yansheng
 * @create 2019-07-25 8:53
 **/
@Component
public class EmpServiceInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private EmpServiceInterceptor empServiceInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 这个方法是用来配置静态资源的，比如html，js，css，等等
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns("/**") 表示拦截所有的请求，
        // excludePathPatterns("/login", "/register") 表示除了登陆与注册之外，因为登陆注册不需要登陆也可以访问
        // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
        // registry.addInterceptor(empServiceInterceptor).addPathPatterns("/**").excludePathPatterns("/login", "/register");
    }
}
