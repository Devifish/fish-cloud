package cn.devifish.cloud.file.server.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.region.Region;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * QCloudCosConfiguration
 * 腾讯云Cos配置
 *
 * @author Devifish
 * @date 2020/8/4 10:00
 */
@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "storage.qcloud.secret-id")
public class QCloudCosConfiguration implements InitializingBean, DisposableBean {

    private final CloudStorageProperties cloudStorageProperties;

    private COSClient client;

    /**
     * 初始化相关参数
     * 来自腾讯云COS文档
     */
    @Override
    public void afterPropertiesSet() {
        var config = cloudStorageProperties.getQcloud();
        var credentials = new BasicCOSCredentials(config.getSecretId(), config.getSecretKey());
        var clientConfig = new ClientConfig(new Region(config.getRegion()));

        log.info("Initializing QCloud COS Client");
        this.client = new COSClient(credentials, clientConfig);
    }

    /**
     * 注册Bean{@link COSClient}
     *
     * @return COSClient
     */
    @Bean
    public COSClient cosClientBean() {
        return client;
    }

    /**
     * Spring 框架停止时
     * 注销{@link COSClient}
     */
    @Override
    public void destroy() {
        log.info("Shutting down QCloud COS Client");
        client.shutdown();
        client = null;
    }

}
