package cn.devifish.cloud.file.server.controller;

import cn.devifish.cloud.common.core.exception.BizException;
import cn.devifish.cloud.file.server.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
    public String upload(@RequestPart MultipartFile file) {
        if (!file.isEmpty()) {
            try (InputStream inputStream = file.getInputStream()) {
                return storageService.upload("/", inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        throw new BizException("上传文件失败");
    }

}
