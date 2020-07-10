package cn.devifish.cloud.common.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * WebSecurityConfiguration
 * Spring Web Security 安全配置
 *
 * @author Devifish
 * @date 2020/7/10 10:39
 */
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/favor.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/actuator/**", "/error").permitAll()
                .anyRequest().authenticated();
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
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 默认UserDetailsService实现
     * 防止为 NULL 时 Spring Security随机生成用户数据
     * 已存在 UserDetailsService 实现时不加载
     *
     * @return UserDetailsService
     */
    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager();
    }

    /**
     * Spring Security 加密算法
     * 默认使用 BCrypt 算法
     *
     * @see org.springframework.security.crypto.factory.PasswordEncoderFactories
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        if (passwordEncoder instanceof  DelegatingPasswordEncoder) {
            BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
            ((DelegatingPasswordEncoder) passwordEncoder).setDefaultPasswordEncoderForMatches(bcryptPasswordEncoder);
        }
        return passwordEncoder;
    }

}
