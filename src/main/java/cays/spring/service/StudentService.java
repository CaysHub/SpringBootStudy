package cays.spring.service;

import cays.spring.dao.StudentDao;
import cays.spring.vo.Student;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 学生业务类
 *
 * @author Chai yansheng
 * @create 2019-08-05 10:30
 **/
@Service
public class StudentService {
    @Resource
    private StudentDao studentDao;

    public Optional<Student> getStudentById(long id) {
        return studentDao.findById(id);
    }

    public List<Student> getAllStudent() {
        return studentDao.findAll();
    }

    public Student saveStudent(Student student) {
        return studentDao.save(student);
    }
}
