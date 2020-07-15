package cn.devifish.cloud.upms.server.controller;

import cn.devifish.cloud.upms.server.service.OAuthTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * OAuthTokenController
 * OAuth2 Token 接口
 *
 * @author Devifish
 * @date 2020/7/14 10:43
 */
@RestController
@RequestMapping("/oauth/token")
@RequiredArgsConstructor
public class OAuthTokenController {

    private final OAuthTokenService oauthTokenService;

    /**
     * 根据用户Token注销用户
     * 不存在及为空的Token均返回注销失败
     *
     * @param token Token
     * @return 是否成功
     */
    @DeleteMapping("/logout")
    public Boolean logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return oauthTokenService.logout(token);
    }
}
