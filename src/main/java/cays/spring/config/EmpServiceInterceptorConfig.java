package cays.spring.config;

import cays.spring.interceptor.EmpServiceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(empServiceInterceptor);
    }
}
