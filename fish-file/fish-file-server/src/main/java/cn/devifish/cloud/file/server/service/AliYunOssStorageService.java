package cn.devifish.cloud.file.server.service;

import cn.devifish.cloud.common.core.exception.UtilException;
import cn.devifish.cloud.file.common.entity.UploadResult;
import cn.devifish.cloud.file.server.config.CloudStorageProperties;
import cn.devifish.cloud.file.server.config.CloudStorageProperties.ALiYunOSSConfig;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;

/**
 * AliYunOssStorageService
 * 阿里云OSS存储服务
 *
 * @author Devifish
 * @date 2020/8/3 15:52
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnClass(OSSClient.class)
@ConditionalOnBean(OSSClient.class)
public class AliYunOssStorageService extends AbstractStorageService {

    private final CloudStorageProperties cloudStorageProperties;
    private final OSSClient client;

    private ALiYunOSSConfig config;
    private String pathPrefix;

    @PostConstruct
    private void init() {
        var config = cloudStorageProperties.getAliyun();

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
    protected UploadResult upload(String path, InputStream inputStream) {
        var request = new PutObjectRequest(
            config.getBucketName(), path, inputStream, null);

        try {
            client.putObject(request);
        } catch (OSSException | ClientException exception){
            log.error("AliYun OSS 上传文件失败 path: {}", path, exception);
            throw new UtilException("上传文件失败");
        }
        return new UploadResult(config.getDomain(), path);
    }
}
