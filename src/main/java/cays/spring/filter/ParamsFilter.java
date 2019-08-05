package cays.spring.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * 请求的空格过滤器
 * 过滤参数的前后空格的过滤器
 *
 * @author Chai yansheng
 * @create 2019-07-29 11:28
 **/
//@Component
public class ParamsFilter implements Filter {
    public static final Logger LOGGER = LoggerFactory.getLogger(ParamsFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.debug("过滤参数的前后空格的过滤器");
        /*ParameterRequestWrapper request = new ParameterRequestWrapper((HttpServletRequest) servletRequest);
        HttpSession session = request.getSession();
        LOGGER.info("session id:" + session.getId());
        if (session.getAttribute("name") == null) {
            session.setAttribute("name", "cays");
        } else {
            LOGGER.info("name:" + session.getAttribute("name"));
        }*/
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
