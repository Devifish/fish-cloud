package cn.devifish.cloud.file.server.service;

import cn.devifish.cloud.common.core.exception.UtilException;
import cn.devifish.cloud.file.server.config.CloudStorageProperties;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
@Service
@RequiredArgsConstructor
@ConditionalOnBean(OSS.class)
public class AliYunOssStorageService implements StorageService {

    private final OSS oss;
    private final CloudStorageProperties cloudStorageProperties;

    private CloudStorageProperties.ALiYunOSSConfig config;

    @PostConstruct
    public void init() {
        this.config = cloudStorageProperties.getAliyun();
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
        try {
            oss.putObject(config.getBucketName(), path, inputStream);
        } catch (OSSException | ClientException e){
            throw new UtilException("上传文件失败, 请检查配置信息");
        }
        return path;
    }

}
