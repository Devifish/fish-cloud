package cn.devifish.cloud.file.server.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.nio.charset.StandardCharsets;

/**
 * TestController
 * 测试接口
 *
 * @author Devifish
 * @date 2020/7/17 11:53
 */
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * 测试异步文件下载
     *
     * @return ResponseEntity<StreamingResponseBody>
     */
    @GetMapping("/test")
    public ResponseEntity<StreamingResponseBody> test() {
        var filename = "test.txt";
        var contentDisposition = ContentDisposition.builder("attachment")
                .filename(filename, StandardCharsets.UTF_8)
                .build();

        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .body(outputStream -> {
                    outputStream.write("test".getBytes());
                });
    }

}
