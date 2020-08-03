package cn.devifish.cloud.file.server.controller;

import cn.devifish.cloud.common.core.exception.BizException;
import cn.devifish.cloud.file.common.entity.Base64FileData;
import cn.devifish.cloud.file.server.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * StorageController
 * 存储API
 *
 * @author Devifish
 * @date 2020/8/3 16:48
 */
@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    /**
     * 文件上传（表单方式）
     *
     * @param file 文件
     * @return 路径
     */
    @PostMapping("/upload")
    public String upload(@RequestPart MultipartFile file) throws IOException {
        if (!file.isEmpty())
            throw new BizException("上传文件不能为空");

        try (InputStream inputStream = file.getInputStream()) {
            return storageService.upload("/", inputStream);
        }
    }

    /**
     * 文件上传（表单方式）
     *
     * @param file 文件
     * @return 路径
     */
    @PostMapping("/upload/multipart")
    public String uploadByMultipart(@RequestPart MultipartFile file) throws IOException {
        return upload(file);
    }

    /**
     * 文件上传（Base64方式）
     *
     * @param data Base64文件数据
     * @return 路径
     */
    @PostMapping("/upload/base64")
    public String uploadByBase64(@RequestBody Base64FileData data) throws IOException {
        return null;
    }

}
