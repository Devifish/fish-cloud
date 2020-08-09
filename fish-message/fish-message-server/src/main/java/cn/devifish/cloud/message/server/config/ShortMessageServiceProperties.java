package cn.devifish.cloud.message.server.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ShortMessageServiceProperties
 * 短信服务配置
 *
 * @author Devifish
 * @date 2020/8/9 16:33
 */
@Getter
@Setter
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "sms")
public class ShortMessageServiceProperties {

    private AliYunSMSConfig aliyun;

    @Getter
    @Setter
    public static class AliYunSMSConfig {

        /** 阿里云AccessKeyId **/
        private String accessKeyId;

        /** 阿里云AccessKeySecret **/
        private String accessKeySecret;

        /** 阿里云COS所属地区 **/
        private String regionId;

        /** 阿里云短信签名 **/
        private String signature;

        /** 阿里云短信服务域名 **/
        private String domain;

        /** 阿里云短信接口版本 **/
        private String version;
    }

}
