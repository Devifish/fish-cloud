package cn.devifish.cloud.upms.server.security;

import cn.devifish.cloud.common.security.config.WebSecurityConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * OAuth2WebSecurityConfiguration
 * OAuth2 Web 安全配置
 * 手动设置 AuthenticationManagerBuilder
 * 防止框架尝试生成 AuthenticationManager
 *
 * @author Devifish
 * @date 2020/7/31 15:19
 */
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class OAuth2WebSecurityConfiguration extends WebSecurityConfiguration {

    private final OAuth2UserDetailsService oauth2UserDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 构建 AuthenticationManager 授权管理器
     * 用于 OAuth2 Password 方式认证
     *
     * @param auth AuthenticationManagerBuilder
     * @throws Exception 异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(oauth2UserDetailsService)
            .passwordEncoder(passwordEncoder);
    }

    /**
     * 注册 AuthenticationManager
     * OAuth2 GrantType为密码方式时依赖
     *
     * @return AuthenticationManager
     * @throws Exception 异常
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
