package cn.devifish.cloud.common.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * PasswordEncoderConfiguration
 * 默认使用 BCrypt 算法 (使用$2A版本强度10)
 *
 * @author Devifish
 * @date 2020/7/11 14:31
 */
@Configuration(proxyBeanMethods = false)
public class PasswordEncoderConfiguration {

    private static final BCryptVersion VERSION = BCryptVersion.$2A;
    private static final int STRENGTH = 10;

    /**
     * 加载 Spring Security 加密算法
     *
     * @return PasswordEncoder
     * @see org.springframework.security.crypto.factory.PasswordEncoderFactories
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(VERSION, STRENGTH);
    }

}
