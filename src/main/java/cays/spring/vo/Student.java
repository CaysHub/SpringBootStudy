package cays.spring.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 学生类
 * Jpa测试与使用
 *
 * @author Chai yansheng
 * @create 2019-08-05 9:15
 **/
@Entity
@Table(name = "student")
public class Student {
    @Id
    private long id;
    @Column(length = 20)
    private String name;
    @Column(length = 20)
    private String nick;
    @Column(length = 20)
    private String password;
    private int age;
    @Column(length = 5)
    private String sex;
    private double score;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
