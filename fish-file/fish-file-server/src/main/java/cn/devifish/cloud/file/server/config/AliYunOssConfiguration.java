package cn.devifish.cloud.file.server.config;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
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
@ConditionalOnClass(OSSClient.class)
@ConditionalOnProperty(name = "storage.aliyun.access-key-id")
public class AliYunOssConfiguration implements InitializingBean, DisposableBean {

    private final StorageProperties storageProperties;

    private OSSClient client;

    /**
     * 初始化相关参数
     * 来自阿里云OSS文档
     */
    @Override
    public void afterPropertiesSet() {
        var config = storageProperties.getAliyun();
        var credentialProvider = new DefaultCredentialProvider(config.getAccessKeyId(), config.getAccessKeySecret());
        var clientBuilderConfiguration = new ClientBuilderConfiguration();

        log.info("Initializing ALiYun OSS Client");
        this.client = new OSSClient(config.getEndPoint(), credentialProvider, clientBuilderConfiguration);
    }

    /**
     * 注册Bean{@link OSSClient}
     *
     * @return OSSClient
     */
    @Bean
    public OSSClient ossClientBean() {
        return client;
    }

    /**
     * Spring 框架停止时
     * 注销{@link OSSClient}
     */
    @Override
    public void destroy() {
        log.info("Shutting down ALiYun OSS Client");
        client.shutdown();
        client = null;
    }
}
