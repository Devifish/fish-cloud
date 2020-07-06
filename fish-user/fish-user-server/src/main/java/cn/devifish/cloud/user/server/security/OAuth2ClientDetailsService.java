package cn.devifish.cloud.user.server.security;

import cn.devifish.cloud.common.security.OAuthClient;
import cn.devifish.cloud.user.server.service.OAuthClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;

/**
 * OAuth2ClientDetailsService
 * OAuth2 客户端详情服务接口实现
 *
 * @see org.springframework.security.oauth2.provider.ClientDetailsService
 * @author Devifish
 * @date 2020/7/6 11:20
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
            log.warn("请求 clientId: {} 查询数据为空", clientId);
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }
        return oauthClient;
    }
}
