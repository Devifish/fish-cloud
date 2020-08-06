package cn.devifish.cloud.upms.server.security;

import cn.devifish.cloud.common.security.error.OAuth2SecurityExceptionTranslator;
import cn.devifish.cloud.upms.server.service.OAuthService;
import cn.devifish.cloud.upms.server.service.SmsCodeService;
import cn.devifish.cloud.upms.server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.List;

/**
 * OAuth2AuthorizationServerConfiguration
 * OAuth2 身份认证服务配置
 *
 * @author Devifish
 * @date 2020/7/3 16:07
 * @see org.springframework.boot.autoconfigure.security.oauth2.authserver.OAuth2AuthorizationServerConfiguration
 */
@Slf4j
@RequiredArgsConstructor
@EnableAuthorizationServer
@Configuration(proxyBeanMethods = false)
public class OAuth2AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final OAuth2SecurityExceptionTranslator exceptionTranslator;
    private final TokenStore tokenStore;
    private final OAuthService oauthService;
    private final UserService userService;
    private final SmsCodeService smsCodeService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(oauthService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.allowFormAuthenticationForClients()
            .tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()");
    }

    /**
     * 配置授权、令牌的访问服务 用于账户密码授权
     *
     * @param endpoints AuthorizationServerEndpointsConfigurer
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.setClientDetailsService(oauthService);
        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
            .exceptionTranslator(exceptionTranslator)
            .authenticationManager(authenticationManager)
            .tokenStore(tokenStore)
            .tokenGranter(initTokenGranters(endpoints));
    }

    /**
     * 加载 OAuth2 TokenGranters
     * 用于登录授权使用
     *
     * @param endpoints AuthorizationServerEndpointsConfigurer
     * @return TokenGranter
     */
    private TokenGranter initTokenGranters(AuthorizationServerEndpointsConfigurer endpoints) {
        var tokenServices = endpoints.getTokenServices();
        var authorizationCodeServices = endpoints.getAuthorizationCodeServices();
        var requestFactory = endpoints.getOAuth2RequestFactory();
        var tokenGranters = List.<TokenGranter>of(
            new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices, oauthService, requestFactory),
            new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, oauthService, requestFactory),
            new SmsCodeTokenGranter(userService, smsCodeService, tokenServices, oauthService, requestFactory),
            new RefreshTokenGranter(tokenServices, oauthService, requestFactory),
            new ImplicitTokenGranter(tokenServices, oauthService, requestFactory),
            new ClientCredentialsTokenGranter(tokenServices, oauthService, requestFactory)
        );

        return new CompositeTokenGranter(tokenGranters);
    }

}
