package cays.spring.controller;

import cays.spring.exception.UserNotFoundException;
import cays.spring.service.EmpService;
import cays.spring.vo.Emp;
import cays.spring.vo.ResultVO;
import cays.spring.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

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

    @GetMapping(value = "/get/{empno}")
    ResultVO getByEmpno(@PathVariable("empno") String empno) {
        return new ResultVO("0", empService.getByEmpno(empno));
    }
    @GetMapping(value = "/list")
    List<Emp> listEmp() {
        return empService.getAllEmp();
    }






    @GetMapping(value = "/user")
    User getUser() {
        return user;
    }

    @GetMapping(value = "/user1")
    void getUserNull() {
        throw new UserNotFoundException("404", "未找到用户");
    }

    @GetMapping(value = "/sendmail")
    public ResultVO sendMail() throws IOException, MessagingException {
        empService.sendmail();
        return new ResultVO("0", "发送成功");
    }

}
