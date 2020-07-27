package cn.devifish.cloud.upms.server.service;

import cn.devifish.cloud.common.security.constant.SecurityConstant;
import cn.devifish.cloud.upms.common.entity.OAuthClient;
import cn.devifish.cloud.upms.server.cache.OAuthClientCache;
import cn.devifish.cloud.upms.server.mapper.OAuthClientMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * OAuthClientService
 * OAuth2 客户端服务
 *
 * @author Devifish
 * @date 2020/7/3 17:36
 */
@Service
@RequiredArgsConstructor
public class OAuthService {

    private final OAuthClientMapper oauthClientMapper;
    private final OAuthClientCache oauthClientCache;
    private final TokenStore tokenStore;

    /**
     * 根据客户端ID查询单个信息
     *
     * @param clientId 客户端ID
     * @return OAuthClient
     */
    public OAuthClient selectByClientId(String clientId) {
        return oauthClientCache.getIfAbsent(clientId, oauthClientMapper::selectById);
    }

    /**
     * 根据用户Token注销用户
     * 不存在及为空的Token均返回注销失败
     *
     * @param token Token
     * @return 是否成功
     */
    public Boolean logout(String token) {
        if (StringUtils.isEmpty(token)) return Boolean.FALSE;
        if (StringUtils.startsWithIgnoreCase(token, SecurityConstant.OAUTH_HEADER_PREFIX))
            token = StringUtils.removeStartIgnoreCase(token, SecurityConstant.OAUTH_HEADER_PREFIX);

        //校验Token是否存在
        var accessToken = tokenStore.readAccessToken(token);
        if (accessToken == null || StringUtils.isEmpty(accessToken.getValue())) return Boolean.FALSE;
        var refreshToken = accessToken.getRefreshToken();

        //注销Token
        tokenStore.removeAccessToken(accessToken);
        tokenStore.removeRefreshToken(refreshToken);
        return Boolean.TRUE;
    }

    /**
     * 根据用户名及客户端ID查询用户所有存在的Token
     * 如果客户端ID为NULL则查询全部
     * 不存在及为空的Token均返回空集合
     *
     * @param clientId 客户端ID
     * @param username 用户名
     * @return Token集合
     */
    public Set<OAuth2AccessToken> findAllTokenByUsernameAndClientId(String clientId, String username) {
        if (StringUtils.isEmpty(username)) return Collections.emptySet();

        //获取ClientDetails信息
        var clientDetails = StringUtils.isEmpty(clientId)
                ? Collections.<OAuthClient>emptyList()
                : Collections.singletonList(selectByClientId(clientId));
        if (CollectionUtils.isEmpty(clientDetails)) return Collections.emptySet();

        //查询该用户全平台Token
        return clientDetails.stream()
                .map(item -> {
                    var itemClientId = item.getClientId();
                    return tokenStore.findTokensByClientIdAndUserName(itemClientId, username);
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    /**
     * 根据用户名注销用户所有存在的Token
     * 不存在及为空的Token均返回注销失败
     *
     * @param clientId 客户端ID
     * @param username 用户名
     * @return 是否成功
     */
    public Boolean logoutAllByUsername(String clientId, String username) {
        var accessTokens = findAllTokenByUsernameAndClientId(clientId, username);
        if (CollectionUtils.isEmpty(accessTokens)) return Boolean.FALSE;

        // 遍历注销全部已存在的Token
        accessTokens.stream()
                .map(OAuth2AccessToken::getValue)
                .forEach(this::logout);

        return Boolean.TRUE;
    }

    /**
     * 根据用户名注销用户所有存在的Token
     * 不存在及为空的Token均返回注销失败
     *
     * @param username 用户名
     * @return 是否成功
     */
    public Boolean logoutAllByUsername(String username) {
        return logoutAllByUsername(null, username);
    }

}
