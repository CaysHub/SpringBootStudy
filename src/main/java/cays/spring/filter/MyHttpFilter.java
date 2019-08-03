package cays.spring.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 过滤器是用于拦截应用程序的HTTP请求和响应
 * 1. 在将请求发送到控制器之前
 * 2. 在向客户发送响应之前。
 * @author Chai yansheng
 * @create 2019-07-25 11:16
 **/
@Component
public class MyHttpFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyHttpFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.debug("Remote host:" + servletRequest.getRemoteHost());
        LOGGER.debug("Remote address:" + servletRequest.getRemoteHost());
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String path = request.getRequestURI();
        LOGGER.info("请求路径：" + path);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
