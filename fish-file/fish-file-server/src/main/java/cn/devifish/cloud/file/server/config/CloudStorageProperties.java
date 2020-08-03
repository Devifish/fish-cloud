package cn.devifish.cloud.file.server.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * CloudStorageProperties
 * 云存储配置文件
 *
 * @author Devifish
 * @date 2020/8/3 15:03
 */
@Getter
@Setter
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "storage")
public class CloudStorageProperties {

    private ALiYunOSSConfig aliyun;

    private QCloudCOSConfig qcloud;

    /**
     * 阿里云OSS配置参数
     * 可通过阿里云管理后台获取
     */
    @Getter
    @Setter
    public static class ALiYunOSSConfig {

        /** 阿里云绑定的域名 **/
        private String domain;

        /** 阿里云路径前缀 **/
        private String prefix;

        /** 阿里云EndPoint **/
        private String endPoint;

        /** 阿里云AccessKeyId **/
        private String accessKeyId;

        /** 阿里云AccessKeySecret **/
        private String accessKeySecret;

        /** 阿里云BucketName **/
        private String bucketName;
    }

    /**
     * 腾讯云COS配置参数
     * 可通过腾讯云管理后台获取
     */
    @Getter
    @Setter
    public static class QCloudCOSConfig {

        /** 腾讯云绑定的域名 **/
        private String domain;

        /** 腾讯云路径前缀 **/
        private String prefix;

        /** 腾讯云AppId **/
        private Integer appId;

        /** 腾讯云SecretId **/
        private String secretId;

        /** 腾讯云SecretKey **/
        private String secretKey;

        /** 腾讯云BucketName **/
        private String bucketName;

        /** 腾讯云COS所属地区 **/
        private String region;
    }

}
