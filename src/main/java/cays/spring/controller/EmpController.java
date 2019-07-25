package cays.spring.controller;

import cays.spring.exception.UserNotFoundException;
import cays.spring.service.EmpService;
import cays.spring.vo.Emp;
import cays.spring.vo.ResultVO;
import cays.spring.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * 用户信息控制器
 *
 * @author Chai yansheng
 * @create 2019-07-24 14:36
 **/
@RestController
@RequestMapping("/emp")
public class EmpController {

    @Autowired
    private EmpService empService;

    @Autowired
    private User user;

    @RequestMapping(value = "/get/{empno}", method = RequestMethod.GET)
    Emp getByEmpno(@PathVariable("empno") String empno) {
        return empService.getByEmpno(empno);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    User getUser() {
        return user;
    }

    @RequestMapping(value = "/user1", method = RequestMethod.GET)
    void getUserNull() {
        throw new UserNotFoundException("404", "未找到用户");
    }

    @RequestMapping(value = "/sendmail")
    public ResultVO sendMail() throws IOException, MessagingException {
        empService.sendmail();
        return new ResultVO("0", "发送成功");
    }

}
