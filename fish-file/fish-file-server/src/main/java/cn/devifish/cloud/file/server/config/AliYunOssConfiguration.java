package cn.devifish.cloud.file.server.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AliYunOssConfiguration
 * 阿里云OSS 配置
 *
 * @author Devifish
 * @date 2020/8/3 15:21
 */
@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "storage.aliyun.access-key-id")
public class AliYunOssConfiguration implements InitializingBean, DisposableBean {

    private final CloudStorageProperties cloudStorageProperties;

    private OSS ossClient;

    @Override
    public void afterPropertiesSet() {
        var config = cloudStorageProperties.getAliyun();
        var credentialProvider = new DefaultCredentialProvider(config.getAccessKeyId(), config.getAccessKeySecret());

        log.info("Initializing aliyun oss");
        this.ossClient = new OSSClientBuilder()
            .build(config.getEndPoint(), credentialProvider);
    }

    @Bean
    public OSS ossClientBean() {
        return ossClient;
    }

    @Override
    public void destroy() {
        log.info("Shutting down aliyun oss");
        ossClient.shutdown();
        ossClient = null;
    }
}
