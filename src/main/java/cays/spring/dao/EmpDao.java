package cays.spring.dao;

import cays.spring.vo.Emp;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface EmpDao {

    @Select("select * from emp where empno=#{empno}")
    Emp fingByEmpno(@Param("empno") String empno);

    @Select("select * from emp")
    List<Emp> findAllEmp();

    @InsertProvider(type = EmpSqlProvider.class, method = "insert")
    int insertEmp(Emp emp);

    @UpdateProvider(type = EmpSqlProvider.class, method = "update")
    int updateEmp(Emp emp);

    @DeleteProvider(type = EmpSqlProvider.class, method = "delete2")
    int deleteEmpByEmpno(String empno);
}
