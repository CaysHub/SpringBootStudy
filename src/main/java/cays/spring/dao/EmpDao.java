package cays.spring.dao;

import cays.spring.vo.Emp;
import org.apache.ibatis.annotations.*;

/**
 * Mybatis实现数据库增、删、查、改
 */
public interface EmpDao {

    @Select("select * from emp where empno=#{empno}")
    Emp fingByEmpno(@Param("empno") String empno);

    @InsertProvider(type = EmpSqlProvider.class, method = "insert")
    int insertEmp(Emp emp);

    @UpdateProvider(type = EmpSqlProvider.class, method = "update")
    int updateEmpByEmpno(Emp emp);

    @DeleteProvider(type = EmpSqlProvider.class, method = "delete2")
    int deleteEmpByEmpnoByEmpno(String empno);
}
