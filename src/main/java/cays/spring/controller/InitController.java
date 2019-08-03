package cays.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 初始化界面
 *
 * @author Chai yansheng
 * @create 2019-07-25 11:21
 **/
@Controller

public class InitController {
    @RequestMapping(value = "/index")
    public String initView() {
        return "index";
    }

    @GetMapping(value = "/home")
    public String home() {
        return "houduan/home";
    }
    @GetMapping(value = "/manager")
    public String manager() {
        return "houduan/manager";
    }
    @GetMapping(value = "/login")
    public String login() {
        return "houduan/login";
    }

}
