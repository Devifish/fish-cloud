package cn.devifish.cloud.message.server.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AliYunSmsConfiguration
 * 阿里云短信服务配置
 *
 * @author Devifish
 * @date 2020/8/9 16:42
 */
@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "sms.aliyun.access-key-id")
public class AliYunSmsConfiguration implements InitializingBean, DisposableBean {

    private final ShortMessageServiceProperties properties;

    private IAcsClient client;

    @Override
    public void afterPropertiesSet() {
        var config = properties.getAliyun();
        var profile = DefaultProfile.getProfile(
            config.getRegionId(), config.getAccessKeyId(), config.getAccessKeySecret());

        log.info("Initializing ALiYun SMS Client");
        client = new DefaultAcsClient(profile);
    }

    @Bean
    public IAcsClient client() {
        return client;
    }

    @Override
    public void destroy() {
        log.info("Shutting down ALiYun SMS Client");
        client.shutdown();
        client = null;
    }
}
