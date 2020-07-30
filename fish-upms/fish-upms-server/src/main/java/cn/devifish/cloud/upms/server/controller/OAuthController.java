package cn.devifish.cloud.upms.server.controller;

import cn.devifish.cloud.common.core.util.BeanUtils;
import cn.devifish.cloud.upms.common.vo.OAuthClientVO;
import cn.devifish.cloud.upms.server.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * OAuthClientController
 * OAuth2 客户端接口
 *
 * @author Devifish
 * @date 2020/7/7 18:26
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    private final OAuthService oauthService;

    /**
     * 根据客户端ID查询单个信息
     *
     * @param clientId 客户端ID
     * @return OAuthClientVo
     */
    @GetMapping("/select/client/id/{clientId}")
    public OAuthClientVO selectClientByClientId(@PathVariable String clientId) {
        var oauthClient = oauthService.selectByClientId(clientId);
        return BeanUtils.copyProperties(oauthClient, OAuthClientVO::new);
    }

    /**
     * 根据用户Token注销用户
     * 不存在及为空的Token均返回注销失败
     *
     * @param token Token
     * @return 是否成功
     */
    @DeleteMapping("/logout")
    public Boolean logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return oauthService.logout(token);
    }

}
