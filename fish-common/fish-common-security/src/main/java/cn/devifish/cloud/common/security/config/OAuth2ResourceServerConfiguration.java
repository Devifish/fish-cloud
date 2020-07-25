package cn.devifish.cloud.common.security.config;

import cn.devifish.cloud.common.security.error.OAuth2SecurityExceptionRenderer;
import cn.devifish.cloud.common.security.error.OAuth2SecurityExceptionTranslator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.util.List;

/**
 * OAuth2ResourceServerConfiguration
 * OAuth2资源服务公共配置
 * 当前环境存在 {@link AuthorizationServerConfigurer} 授权服务配置时
 * 停止加载该OAuth2资源服务配置 Bean
 *
 * @author Devifish
 * @date 2020/7/9 17:25
 */
@EnableResourceServer
@RequiredArgsConstructor
@EnableConfigurationProperties(OpenApiUrlProperties.class)
@Configuration(proxyBeanMethods = false)
public class OAuth2ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private final OAuth2SecurityExceptionRenderer exceptionRenderer;
    private final OAuth2SecurityExceptionTranslator exceptionTranslator;
    private final TokenStore tokenStore;
    private final OpenApiUrlProperties openApiUrlProperties;

    /**
     * 加载放行路径
     * 不被安全框架拦截
     *
     * @param http HttpSecurity
     * @throws Exception 异常
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        List<String> openApiUrls = openApiUrlProperties.getUrls();
        String[] ignore = openApiUrls.toArray(new String[0]);

        //白名单路径放行, 其他需要鉴权
        http.authorizeRequests()
                .antMatchers(ignore).permitAll()
                .anyRequest().authenticated();
    }

    /**
     * 配置授权、令牌的访问服务
     * 用于资源服务器获取授权信息
     *
     * @param resources spring security
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(getAuthenticationEntryPoint())
                .accessDeniedHandler(getAccessDeniedHandler())
                .tokenStore(tokenStore);
    }

    private AuthenticationEntryPoint getAuthenticationEntryPoint() {
        var authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
        authenticationEntryPoint.setExceptionRenderer(exceptionRenderer);
        authenticationEntryPoint.setExceptionTranslator(exceptionTranslator);
        return authenticationEntryPoint;
    }

    private AccessDeniedHandler getAccessDeniedHandler() {
        var accessDeniedHandler = new OAuth2AccessDeniedHandler();
        accessDeniedHandler.setExceptionRenderer(exceptionRenderer);
        accessDeniedHandler.setExceptionTranslator(exceptionTranslator);
        return accessDeniedHandler;
    }



}
