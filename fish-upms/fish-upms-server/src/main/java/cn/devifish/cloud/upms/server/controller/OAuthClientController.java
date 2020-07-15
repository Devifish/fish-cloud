package cn.devifish.cloud.upms.server.controller;

import cn.devifish.cloud.upms.common.entity.OAuthClient;
import cn.devifish.cloud.upms.common.vo.OAuthClientVo;
import cn.devifish.cloud.upms.server.service.OAuthClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * OAuthClientController
 * OAuth2 客户端接口
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
    @GetMapping("/select/id/{clientId}")
    public OAuthClientVo selectByClientId(@PathVariable String clientId) {
        OAuthClient oauthClient = oauthClientService.selectByClientId(clientId);
        OAuthClientVo oauthClientVo = new OAuthClientVo();
        BeanUtils.copyProperties(oauthClient, oauthClientVo);
        return oauthClientVo;
    }

}
