package cays.spring.interceptor;

import cays.spring.vo.Emp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 雇员请求拦截器
 *
 * @author Chai yansheng
 * @create 2019-07-25 8:52
 **/
@Component
public class EmpServiceInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpServiceInterceptor.class);

    /**
     * 这个方法是在访问接口之前执行的，我们只需要在这里写验证登陆状态的业务逻辑，
     * 就可以在用户调用指定接口之前验证登陆状态了
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOGGER.info("Pre handle method is calling");
        // 每一个项目对于登陆的实现逻辑都有所区别，我这里使用最简单的Session提取User来验证登陆
        HttpSession session = request.getSession();
        // 这里的User是登陆时放入session的
        Emp emp = (Emp) session.getAttribute("emp");
        // 如果session中没有user，表示没登陆
        if (emp == null) {
            // 这个方法返回false表示忽略当前请求，如果一个用户调用了需要登陆才能使用的接口，如果他没有登陆这里会直接忽略掉
            // 当然你可以利用response给用户返回一些提示信息，告诉他没登陆
            LOGGER.info("The emp does not login.");
            return false;
        }
        // 如果session里有user，表示该用户已经登陆，放行，用户即可继续调用自己需要的接口
        LOGGER.info("The emp has logined.");
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
