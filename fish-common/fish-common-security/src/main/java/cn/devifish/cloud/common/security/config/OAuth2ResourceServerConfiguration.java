package cn.devifish.cloud.common.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * OAuth2ResourceServerConfiguration
 * OAuth2资源服务公共配置
 * 当前环境存在 {@link AuthorizationServerConfigurer} 授权服务配置时
 * 停止加载该OAuth2资源服务配置 Bean
 *
 * @author Devifish
 * @date 2020/7/9 17:25
 */
@RequiredArgsConstructor
@ConditionalOnMissingBean(AuthorizationServerConfigurer.class)
public class OAuth2ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private final TokenStore tokenStore;

    /**
     * Http接口安全配置
     *
     * @param http HttpSecurity
     * @throws Exception 异常
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }

    /**
     * 配置授权、令牌的访问服务
     * 用于资源服务器获取授权信息
     *
     * @param resources spring security
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }
}
