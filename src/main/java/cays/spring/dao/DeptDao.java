package cays.spring.dao;

import cays.spring.vo.Dept;
import org.apache.ibatis.annotations.*;

public interface DeptDao {

    @Select("select * from dept where deptno=#{deptno}")
    Dept findByDeptno(@Param("deptno") String deptno);

    @Update("<script>update dept" +
            "<set><if test='deptno != null'>dname=#{dname},</if>" +
            "<if test='loc != null'>loc=#{loc}</if></set>" +
            "<where>deptno=#{deptno}</where>" +
            "</script>")
    int updateDept(Dept dept);
}
