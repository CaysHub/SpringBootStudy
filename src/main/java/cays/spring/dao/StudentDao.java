package cays.spring.dao;

import cays.spring.vo.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Jpa的Dao层接口
 * Student的增删查改
 */
@Repository
public interface StudentDao extends JpaRepository<Student, Long> {
}
