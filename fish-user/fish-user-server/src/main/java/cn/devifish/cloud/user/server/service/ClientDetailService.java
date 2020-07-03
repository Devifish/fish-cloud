package cn.devifish.cloud.user.server.service;

import cn.devifish.cloud.user.server.mapper.ClientDetailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * ClientDetailsService
 *
 * @author Devifish
 * @date 2020/7/3 17:36
 */
@Service
@RequiredArgsConstructor
public class ClientDetailService implements ClientDetailsService {

    private final ClientDetailMapper clientDetailMapper;

    @PostConstruct
    private void init() {
        clientDetailMapper.findByClientId("server");
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return clientDetailMapper.findByClientId(clientId);
    }
}
