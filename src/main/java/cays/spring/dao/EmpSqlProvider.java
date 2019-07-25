package cays.spring.dao;

import cays.spring.vo.Emp;
import org.apache.ibatis.jdbc.SQL;

/**
 * emp查询provider
 *
 * @author Chai yansheng
 * @create 2019-07-25 10:01
 **/
public class EmpSqlProvider {

    /**
     * 查询语句.使用SQL
     * @param emp
     * @return
     */
    public String select1(final Emp emp){
        return new SQL(){{
            SELECT("empno, ename, job");
            FROM("demo");
            if(emp.getMgr() != null){
                WHERE("mgr=#{mgr}");
            }
            if(emp.getComm() != null){
                WHERE("comm=#{comm}");
            }
        }}.toString();
    }

    public String insert(final Emp emp) {
        return new SQL() {{
            INSERT_INTO("emp");
            INTO_COLUMNS("empno, ename, job");
            INTO_VALUES("#{empno}");
            if (null != emp.getEname()) {
                VALUES("name", "#{ename}");
            }
            if (null != emp.getJob()) {
                VALUES("job", "#{job}");
            }
        }}.toString();
    }

    public String update(final Emp emp) {
        return new SQL() {{
            UPDATE("emp");
            if (null != emp.getEname()) {
                SET("ename=#{ename}");
            }
            if (null != emp.getJob()) {
                SET("job=#{job}");
            }
            WHERE("empno=#{empno}");
        }}.toString();
    }

    public String delete2(final String empno){
        return new SQL(){{
            DELETE_FROM("emp");
            WHERE("empno=#{empno}");
        }}.toString();
    }
}
