package cn.devifish.cloud.user.server.config;

import cn.devifish.cloud.common.security.constant.SecurityConstant;
import cn.devifish.cloud.user.server.service.ClientDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * AuthorizationServerConfiguration
 * OAuth2 身份认证服务配置
 *
 * @see org.springframework.boot.autoconfigure.security.oauth2.authserver.OAuth2AuthorizationServerConfiguration
 * @author Devifish
 * @date 2020/7/3 16:07
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    //private final AuthenticationManager authenticationManager;
    private final RedisConnectionFactory redisConnectionFactory;
    private final ClientDetailService ClientDetailService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(ClientDetailService);
    }

    /**
     * 配置授权、令牌的访问服务 用于账户密码授权
     *
     * @param endpoints spring security
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenStore tokenStore = tokenStore();
        DefaultTokenServices tokenService = tokenService(tokenStore);

        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .tokenStore(tokenStore)
                .tokenServices(tokenService);
    }

    /**
     * Redis 数据库缓存鉴权 Token
     *
     * @return TokenStore
     */
    @Bean
    public TokenStore tokenStore()  {
        log.info("Initializing OAuth2 Redis Token Store");
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.setPrefix(SecurityConstant.OAUTH_CACHE_PREFIX);
        return tokenStore;
    }

    /**
     * 注册 TokenServices
     *
     * @param tokenStore TokenStore
     * @return DefaultTokenServices
     */
    @Bean
    public DefaultTokenServices tokenService(TokenStore tokenStore) {
        log.info("Initializing OAuth2 Redis Token Service");
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setAccessTokenValiditySeconds((int) SecurityConstant.DEFAULT_ACCESS_TOKEN_VALIDITY);
        tokenServices.setRefreshTokenValiditySeconds((int) SecurityConstant.DEFAULT_REFRESH_TOKEN_VALIDITY);
        return tokenServices;
    }

}
