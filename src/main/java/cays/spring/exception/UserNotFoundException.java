package cays.spring.exception;

/**
 * 用户没有找到异常类
 *
 * @author Chai yansheng
 * @create 2019-07-24 17:57
 **/
public class UserNotFoundException extends RuntimeException {
    private String code;
    private String data;

    public UserNotFoundException(String code, String data) {
        this.code = code;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
