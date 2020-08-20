package cn.devifish.cloud.file.server.service;

import cn.devifish.cloud.file.common.entity.UploadResult;
import cn.devifish.cloud.file.server.config.StorageProperties;
import cn.devifish.cloud.file.server.config.StorageProperties.LocalStorageConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * LocalStorageService
 * 本地存储
 *
 * @author Devifish
 * @date 2020/8/20 17:26
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnBean(StorageProperties.class)
public class LocalStorageService extends AbstractStorageService {

    private final StorageProperties storageProperties;

    private LocalStorageConfig config;
    private String pathPrefix;

    @PostConstruct
    private void init() {
        var config = storageProperties.getLocal();

        this.config = config;
        this.pathPrefix = config.getPrefix();
    }

    /**
     * 获取路径前缀
     * 用户自动拼接完整路径使用
     *
     * @return 路径前缀
     */
    @Override
    protected String pathPrefix() {
        return pathPrefix;
    }

    /**
     * 文件上传
     *
     * @param path 上传路径
     * @param inputStream 文件流
     * @return 服务端路径
     */
    @Override
    protected UploadResult upload(String path, InputStream inputStream) throws IOException {
        var filePath = Paths.get(config.getPath(), path);
        var outputStream = Files.newOutputStream(filePath);

        IOUtils.copy(inputStream, outputStream);
        return new UploadResult(config.getDomain(), path);
    }
}
