package cays.spring.controller;

import cays.spring.exception.UserNotFoundException;
import cays.spring.vo.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常处理控制器
 *
 * @author Chai yansheng
 * @create 2019-07-24 18:01
 **/
@ControllerAdvice
public class ExceptionController {
    /**
     * 全局错误处理机制
     * @param e
     * @return
     */
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResultVO handleUserNotFoundException(UserNotFoundException e) {
        return new ResultVO(e.getCode(), e.getData());
    }
}
