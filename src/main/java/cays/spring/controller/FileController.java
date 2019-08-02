package cays.spring.controller;

import cays.spring.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件处理控制器
 *
 * @author Chai yansheng
 * @create 2019-07-25 11:28
 **/
@RestController
@RequestMapping("/file")
@Api(value = "文件上传下载接口")
public class FileController {
    /**
     * 上传文件处理机制
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "文件上传接口")
    public ResultVO uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        File uploadFile = new File("D:\\WorkSpace\\file\\" + file.getOriginalFilename());
        uploadFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(uploadFile);
        fout.write(file.getBytes());
        fout.close();
        return new ResultVO("0", "上传成功");
    }

    /**
     * 下载文件处理机制
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ApiOperation(value = "文件下载接口，暂无参数")
    public ResponseEntity<Object> downloadFile() throws IOException {
        String filename = "D:\\WorkSpace\\file\\3.jpg";
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<Object>
                responseEntity = ResponseEntity.ok().headers(headers).contentLength(
                file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);

        return responseEntity;
    }
}
