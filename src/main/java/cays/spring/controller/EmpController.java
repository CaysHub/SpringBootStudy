package cays.spring.controller;

import cays.spring.exception.UserNotFoundException;
import cays.spring.service.EmpService;
import cays.spring.vo.Emp;
import cays.spring.vo.ResultVO;
import cays.spring.vo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@Api(value = "雇员信息接口")
public class EmpController {

    @Autowired
    private EmpService empService;

    @Autowired
    private User user;

    @GetMapping(value = "/get/{empno}")
    @ApiOperation(value = "根据雇员号获取雇员信息")
    ResultVO getByEmpno(@PathVariable("empno") String empno) {
        return new ResultVO("0", empService.getByEmpno(empno));
    }

    @GetMapping(value = "/user")
    @ApiOperation(value = "获取用户，测试用")
    ResultVO getUser() {
        return new ResultVO("0", user);
    }

    @GetMapping(value = "/user1")
    @ApiOperation(value = "获取用户错误处理，测试用")
    ResultVO getUserNull() {
        throw new UserNotFoundException("404", "未找到用户");
    }

    @GetMapping(value = "/sendmail")
    @ApiOperation(value = "发送邮件，测试用")
    public ResultVO sendMail() throws IOException, MessagingException {
        empService.sendmail();
        return new ResultVO("0", "发送成功");
    }

}
