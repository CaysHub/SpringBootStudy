package cays.spring.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 请求过滤之获取请求
 *
 * @author Chai yansheng
 * @create 2019-07-29 11:31
 **/
public class ParameterRequestWrapper extends HttpServletRequestWrapper {
    private Map<String, String[]> params = new HashMap<>();
    public static final Logger LOGGER = LoggerFactory.getLogger(ParameterRequestWrapper.class);

    public ParameterRequestWrapper(HttpServletRequest request) {
        super(request);
        Map<String, String[]> stringMap = request.getParameterMap();
        params.putAll(stringMap);
        LOGGER.info("修改前参数：" + JSONObject.toJSONString(params));
        this.modifyParams();
        LOGGER.info("修改后参数：" + JSONObject.toJSONString(params));

    }
    private void modifyParams() {
        // 修改参数，删除空格
        Set<String> keys = params.keySet();
        keys.stream().forEach(key -> {
            String[] values = params.get(key);
            values[0] = values[0].trim();
            params.put(key, values);
        });
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 不是application/json直接返回
        if (!super.getHeader(HttpHeaders.CONTENT_TYPE).equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            return super.getInputStream();
        }
        StringBuilder sb = new StringBuilder();
        int len = 0;
        ServletInputStream in = super.getInputStream();
        byte[] bytes = new byte[1024];
        while ((len = in.read(bytes)) > 0) {
            sb.append(new String(bytes, 0, len));
        }
        String s = sb.toString();
        // 内容为空直接返回
        if (s.isEmpty()) {
            return super.getInputStream();
        }
        LOGGER.info("参数列表：" + s);
        return new MyInputStream(new ByteArrayInputStream(s.getBytes()));
    }

    @Override
    public String getParameter(String name) {
        String[] values = params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    @Override
    public String[] getParameterValues(String name) {
        return params.get(name);
    }

    class MyInputStream extends ServletInputStream{
        private ByteArrayInputStream inputStream;

        public MyInputStream(ByteArrayInputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }
    }
}
