package cn.devifish.cloud.common.security.config;

import cn.devifish.cloud.common.security.constant.SecurityConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * OAuth2TokenConfiguration
 * OAuth2 Token配置
 *
 * @author Devifish
 * @date 2020/7/9 17:28
 */
@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class OAuth2TokenConfiguration {

    private final RedisConnectionFactory redisConnectionFactory;

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

}
