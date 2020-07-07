package cn.devifish.cloud.user.server.security;

import cn.devifish.cloud.user.common.entity.OAuthClient;
import cn.devifish.cloud.user.server.service.OAuthClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * OAuth2ClientDetailsService
 * OAuth2 客户端详情服务接口实现
 *
 * @author Devifish
 * @date 2020/7/6 11:20
 * @see org.springframework.security.oauth2.provider.ClientDetailsService
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2ClientDetailsService implements ClientDetailsService {

    private final OAuthClientService oauthClientService;

    /**
     * 适配 Spring Cloud OAuth2 接口
     *
     * @param clientId 客户端ID
     * @return ClientDetails
     * @throws ClientRegistrationException 客户端注册异常
     */
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OAuthClient oauthClient = oauthClientService.findByClientId(clientId);
        if (oauthClient == null) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }
        return buildClientDetails(oauthClient);
    }

    /**
     * 构建 ClientDetails
     *
     * @param oauthClient OAuthClient
     * @return ClientDetails
     */
    private ClientDetails buildClientDetails(OAuthClient oauthClient) {
        Assert.notNull(oauthClient, "OAuthClient 不能为NULL");
        BaseClientDetails details = new BaseClientDetails(
                oauthClient.getClientId(), oauthClient.getResourceIds(), oauthClient.getScope(),
                oauthClient.getGrantTypes(), oauthClient.getAuthorities());

        details.setClientSecret(oauthClient.getClientSecret());

        return details;
    }

}
