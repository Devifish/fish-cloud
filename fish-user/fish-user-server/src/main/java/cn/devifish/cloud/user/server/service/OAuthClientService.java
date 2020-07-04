package cn.devifish.cloud.user.server.service;

import cn.devifish.cloud.common.security.OAuthClient;
import cn.devifish.cloud.user.server.mapper.OAuthClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 * OAuthClientService
 * OAuth2 客户端服务
 *
 * @author Devifish
 * @date 2020/7/3 17:36
 */
@Service
@RequiredArgsConstructor
public class OAuthClientService implements ClientDetailsService {

    private final OAuthClientMapper OAuthClientMapper;

    /**
     * 根据客户端ID查询单个信息
     *
     * @param clientId 客户端ID
     * @return OAuthClient
     */
    public OAuthClient findByClientId(String clientId) {
        return OAuthClientMapper.findByClientId(clientId);
    }

    /**
     * 适配 Spring Cloud OAuth2 接口
     *
     * @param clientId 客户端ID
     * @return ClientDetails
     * @throws ClientRegistrationException 客户端注册异常
     */
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return findByClientId(clientId);
    }


}
