package cays.spring.exception;

import cays.spring.vo.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局错误处理类
 *
 * @author Chai yansheng
 * @create 2019-07-25 12:19
 **/
@ControllerAdvice
public class MyHandleException {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResultVO handleException(Exception e) {
        e.printStackTrace();
        return new ResultVO(e.getCause().toString(), e.getMessage());
    }
}
