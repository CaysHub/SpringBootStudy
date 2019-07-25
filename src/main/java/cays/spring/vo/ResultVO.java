package cays.spring.vo;

/**
 * 返回码
 *
 * @author Chai yansheng
 * @create 2019-07-24 18:02
 **/
public class ResultVO {
    private String code;
    private Object data;

    public ResultVO(String code, Object data) {
        this.code = code;
        this.data = data;
    }

    public ResultVO() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
