package cays.spring.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 雇员请求拦截器
 *
 * @author Chai yansheng
 * @create 2019-07-25 8:52
 **/
@Component
public class EmpServiceInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpServiceInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOGGER.debug("Pre handle method is calling");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LOGGER.debug("Post handle method is calling");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //LOGGER.info("After handle method is calling");
    }
}
