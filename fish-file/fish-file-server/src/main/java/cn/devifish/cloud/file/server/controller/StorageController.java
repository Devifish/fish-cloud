package cn.devifish.cloud.file.server.controller;

import cn.devifish.cloud.common.security.annotation.OpenApi;
import cn.devifish.cloud.file.common.entity.Base64FileData;
import cn.devifish.cloud.file.common.entity.UploadResult;
import cn.devifish.cloud.file.server.service.AbstractStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

/**
 * StorageController
 * 存储API
 *
 * @author Devifish
 * @date 2020/8/3 16:48
 */
@OpenApi
@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
public class StorageController {

    private final AbstractStorageService storageService;

    /**
     * 文件上传（表单方式）
     *
     * @param file 文件
     * @return 路径
     */
    @PostMapping("/upload")
    public UploadResult upload(@RequestPart MultipartFile file) throws IOException {
        return uploadByMultipart(file);
    }

    /**
     * 文件上传（表单方式）
     *
     * @param file 文件
     * @return 路径
     */
    @PostMapping("/upload/multipart")
    public UploadResult uploadByMultipart(@RequestPart MultipartFile file) throws IOException {
        return storageService.uploadByMultipart(file);
    }

    /**
     * 文件上传（Base64方式）
     *
     * @param data Base64文件数据
     * @return 路径
     */
    @PostMapping("/upload/base64")
    public UploadResult uploadByBase64(@Valid @RequestBody Base64FileData data) throws IOException {
        return storageService.uploadByBase64(data);
    }

}
