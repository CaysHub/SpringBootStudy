package cays.spring.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 用户类
 *
 * @author Chai yansheng
 * @create 2019-07-24 16:26
 **/
@Component
public class User {
    @Value("${user.userName:cays}")
    String userName;
    @Value("${user.password:123456}")
    String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
