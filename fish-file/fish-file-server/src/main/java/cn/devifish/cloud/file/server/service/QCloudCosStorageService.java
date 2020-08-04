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
@ConditionalOnBean(COSClient.class)
public class QCloudCosStorageService implements StorageService {

    private final CloudStorageProperties cloudStorageProperties;
    private final COSClient client;

    private QCloudCOSConfig config;

    @PostMapping
    private void init() {
        this.config = cloudStorageProperties.getQcloud();
    }

    /**
     * 文件上传
     *
     * @param path 上传路径
     * @param inputStream 文件流
     * @return 服务端路径
     */
    @Override
    public String upload(String path, InputStream inputStream) {
        var request = new PutObjectRequest(config.getBucketName(), path, inputStream, null);

        try {
            client.putObject(request);
        } catch (CosClientException exception) {
            log.error("QCloud COS 上传文件失败 path: {}", path, exception);
            throw new UtilException("上传文件失败");
        }
        return path;
    }
}
