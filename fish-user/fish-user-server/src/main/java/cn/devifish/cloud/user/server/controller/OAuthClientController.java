package cn.devifish.cloud.user.server.controller;

import cn.devifish.cloud.user.common.vo.OAuthClientVo;
import cn.devifish.cloud.user.server.service.OAuthClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * OAuthClientController
 * OAuth2 客户端
 *
 * @author Devifish
 * @date 2020/7/7 18:26
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth/client")
public class OAuthClientController {

    private final OAuthClientService oauthClientService;

    /**
     * 根据客户端ID查询单个信息
     *
     * @param clientId 客户端ID
     * @return OAuthClientVo
     */
    @GetMapping("/find/{clientId}")
    public OAuthClientVo findByClientId(@PathVariable String clientId) {
        return oauthClientService.findVoByClientId(clientId);
    }

}
