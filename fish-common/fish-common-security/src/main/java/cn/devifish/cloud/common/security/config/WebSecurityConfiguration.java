package cn.devifish.cloud.common.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * WebSecurityConfiguration
 * Spring Web Security 安全配置
 *
 * @author Devifish
 * @date 2020/7/10 10:39
 */
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * 排除 Spring 框架默认接口及文件
     * 防止进入Security拦截器占用CPU资源
     *
     * @param web WebSecurity
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/favor.ico", "/actuator/**", "/error");
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
    @ConditionalOnBean(UserDetailsService.class)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
