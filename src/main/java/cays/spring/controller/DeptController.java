package cays.spring.controller;

import cays.spring.service.DeptService;
import cays.spring.vo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 雇员部门信息控制器
 *
 * @author Chai yansheng
 * @create 2019-07-24 14:57
 **/
@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @RequestMapping("/get/{deptno}")
    Dept getByDeptno(@PathVariable String deptno) {
        return deptService.getByDeptno(deptno);
    }

}
