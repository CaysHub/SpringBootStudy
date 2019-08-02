package cays.spring.dao;

import cays.spring.vo.Dept;
import org.apache.ibatis.annotations.*;

/**
 * Mybatis实现数据库增、删、查、改
 */
public interface DeptDao {

    @Select("select * from dept where deptno=#{deptno}")
    Dept findByDeptno(@Param("deptno") String deptno);

    @Insert("insert into dept (deptno, dname, loc) values (#{deptno}, #{dname}, #{loc})")
    int insertDept(Dept dept);

    @Update("<script>update dept" +
            "<set><if test='dname != null'>dname=#{dname},</if>" +
            "<if test='loc != null'>loc=#{loc}</if></set>" +
            "<where>deptno=#{deptno}</where>" +
            "</script>")
    int updateDeptByDeptno(Dept dept);

    @Delete("delete dept where deptno=#{deptno}")
    int deleteDeptByDeptnoByDeptno(@Param("deptno") String deptno);
}
