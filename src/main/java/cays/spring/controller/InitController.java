package cays.spring.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 初始化界面
 * @Controller 映射界面html
 *
 * @author Chai yansheng
 * @create 2019-07-25 11:21
 **/
@Controller
@Api(value = "界面转换控制器")
public class InitController {
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ApiOperation(value = "转到index初始界面")
    public String initView() {
        return "index";
    }
}
