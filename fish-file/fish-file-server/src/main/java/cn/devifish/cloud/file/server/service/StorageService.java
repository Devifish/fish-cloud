package cn.devifish.cloud.file.server.service;

import cn.devifish.cloud.common.core.exception.BizException;
import cn.devifish.cloud.file.common.entity.Base64FileData;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * StorageService
 * 存储服务
 *
 * @author Devifish
 * @date 2020/8/3 16:37
 */
public interface StorageService {

    /**
     * 文件上传
     *
     * @param path 上传路径
     * @param inputStream 文件流
     * @return 服务端路径
     */
    String upload(String path, InputStream inputStream) throws IOException;

    /**
     * 文件上传
     *
     * @param path 上传路径
     * @param content 文件数据
     * @return 服务端路径
     */
    default String upload(String path, byte[] content) throws IOException {
        if (ArrayUtils.isEmpty(content))
            throw new BizException("内容不能为NULL");

        try (InputStream inputStream = new ByteArrayInputStream(content)) {
            return upload(path, inputStream);
        }
    }

    /**
     * 文件上传（表单方式）
     *
     * @param file 文件
     * @return 路径
     */
    default String uploadByMultipart(MultipartFile file) throws IOException {
        if (file.isEmpty()) throw new BizException("上传文件不能为空");

        try (InputStream inputStream = file.getInputStream()) {
            var originalFilename = file.getOriginalFilename();
            return upload(originalFilename, inputStream);
        }
    }

    /**
     * 文件上传（Base64方式）
     *
     * @param data Base64文件数据
     * @return 路径
     */
    default String uploadByBase64(Base64FileData data) throws IOException {
        var content = data.getContent();
        var bytes = Base64.decodeBase64(content);

        return upload(data.getFilename(), bytes);
    }

}
