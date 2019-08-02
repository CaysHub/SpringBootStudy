package cays.spring.controller;

import cays.spring.service.DeptService;
import cays.spring.vo.Dept;
import cays.spring.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 雇员部门信息控制器
 *
 * @author Chai yansheng
 * @create 2019-07-24 14:57
 **/
@RestController
@RequestMapping("/dept")
@Api(value = "部门信息接口")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping("/get/{deptno}")
    @ApiOperation(value = "获取部门信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptno", value = "部门号", dataType = "String", required = true)
    })
    ResultVO getByDeptno(@PathVariable String deptno) {
        return new ResultVO("0", deptService.getByDeptno(deptno));
    }

    @PostMapping("/insert")
    @ApiOperation(value = "添加部门信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptno", value = "部门号", dataType = "String", required = true),
            @ApiImplicitParam(name = "dname", value = "部门名称", dataType = "String", required = true),
            @ApiImplicitParam(name = "loc", value = "部门地址", dataType = "String", required = true)
    })
    ResultVO insertDept(@RequestBody Dept dept) {
        return new ResultVO("0", deptService.insertDept(dept));
    }

    @PostMapping("/update")
    @ApiOperation(value = "根据部门号修改部门信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptno", value = "部门号", dataType = "String", required = true),
            @ApiImplicitParam(name = "dname", value = "要修改的部门名称", dataType = "String", required = false),
            @ApiImplicitParam(name = "loc", value = "要修改的部门地址", dataType = "String", required = false)
    })
    ResultVO updateDeptByDeptno(@RequestBody Dept dept) {
        return new ResultVO("0", deptService.updateDeptByDeptno(dept));
    }

    @GetMapping("/delete/{deptno}")
    @ApiOperation(value = "根据部门号删除部门信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptno", value = "要删除的部门号", dataType = "String", required = true)
    })
    ResultVO deleteDeptByDeptno(@PathVariable String deptno) {
        return new ResultVO("0", deptService.deleteDeptByDeptno(deptno));
    }


}
