package cays.spring.service;

import cays.spring.dao.DeptDao;
import cays.spring.vo.Dept;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 雇员部门信息服务类
 *
 * @author Chai yansheng
 * @create 2019-07-24 15:08
 **/
@Service
public class DeptService {
    @Resource
    private DeptDao deptDao;

    public Dept getByDeptno(String deptno) {
        return deptDao.findByDeptno(deptno);
    }

}
