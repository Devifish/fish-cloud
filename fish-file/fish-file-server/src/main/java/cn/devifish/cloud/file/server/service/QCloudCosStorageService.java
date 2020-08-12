package cn.devifish.cloud.file.server.service;

import cn.devifish.cloud.common.core.exception.UtilException;
import cn.devifish.cloud.file.server.config.CloudStorageProperties;
import cn.devifish.cloud.file.server.config.CloudStorageProperties.QCloudCOSConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.InputStream;

/**
 * QCloudCosStorageService
 * 腾讯云COS存储服务
 *
 * @author Devifish
 * @date 2020/8/3 18:30
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnClass(COSClient.class)
@ConditionalOnBean(COSClient.class)
public class QCloudCosStorageService extends AbstractStorageService {

    private final CloudStorageProperties cloudStorageProperties;
    private final COSClient client;

    private QCloudCOSConfig config;
    private String pathPrefix;

    @PostMapping
    private void init() {
        var config = cloudStorageProperties.getQcloud();

        this.pathPrefix = config.getPrefix();
        this.config = config;
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
    protected String upload(String path, InputStream inputStream) {
        var request = new PutObjectRequest(config.getBucketName(), path, inputStream, null);

        try {
            client.putObject(request);
        } catch (CosClientException exception) {
            log.error("QCloud COS 上传文件失败 path: {}", path, exception);
            throw new UtilException("上传文件失败");
        }
        return joinPath(config.getDomain(), path);
    }
}
