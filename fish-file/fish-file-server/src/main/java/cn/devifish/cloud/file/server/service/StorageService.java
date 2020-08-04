package cn.devifish.cloud.file.server.service;

import cn.devifish.cloud.common.core.exception.BizException;
import org.apache.commons.lang3.ArrayUtils;

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

}
