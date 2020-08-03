package cn.devifish.cloud.file.server.service;

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
    String upload(String path, InputStream inputStream);

}
