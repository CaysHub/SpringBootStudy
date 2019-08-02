package cays.spring.controller;

import cays.spring.exception.UserNotFoundException;
import cays.spring.vo.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 全局异常处理控制器
 *
 * @author Chai yansheng
 * @create 2019-07-24 18:01
 **/
@ControllerAdvice
public class ExceptionController {
    /**
     * 自定义错误处理机制
     * @param e
     * @return
     */
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResultVO handleUserNotFoundException(UserNotFoundException e) {
        return new ResultVO(e.getCode(), e.getData());
    }

    /**
     * 错误处理全局接口
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResultVO handleException(Exception e) {
        return new ResultVO("CAYS000001", parseException(e));
    }

    /**
     * 将异常信息以字符串形式输出，并控制4000字符以内
     * @param e
     * @return
     */
    public String parseException(Exception e) {
        if (null == e) {
            return "";
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.flush();sw.flush();
        String errorStr = sw.toString();
        if (errorStr.length() >  4000) {
            errorStr = errorStr.substring(0, 4000);
        }
        return errorStr;
    }
}
