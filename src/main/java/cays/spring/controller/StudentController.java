package cays.spring.controller;

import cays.spring.service.StudentService;
import cays.spring.vo.ResultVO;
import cays.spring.vo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 学生信息控制器
 *
 * @author Chai yansheng
 * @create 2019-08-05 10:50
 **/
@RestController
@RequestMapping(value = "/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping(value = "/list")
    ResultVO listStudent() {
        return new ResultVO("0", studentService.getAllStudent());
    }

    @GetMapping(value = "/get/{id}")
    ResultVO getStudentById(@PathVariable("id") long id) {
        return new ResultVO("0", studentService.getStudentById(id));
    }
}
