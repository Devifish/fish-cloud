package cn.devifish.cloud.upms.server.security;

import cn.devifish.cloud.upms.server.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.HashMap;

/**
 * SmsCodeTokenGranter
 * 短信验证码 TokenGranter
 *
 * @author Devifish
 * @date 2020/8/6 16:44
 */
public class SmsCodeTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = "sms_code";

    private final UserService userService;

    protected SmsCodeTokenGranter(
        UserService userService,
        AuthorizationServerTokenServices tokenServices,
        ClientDetailsService clientDetailsService,
        OAuth2RequestFactory requestFactory) {

        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.userService = userService;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        var parameters = new HashMap<>(tokenRequest.getRequestParameters());
        var mobile = parameters.get("mobile");
        var code = parameters.get("code");
        parameters.remove("code");

        // 校验参数
        if (StringUtils.isEmpty(mobile)) throw new InvalidGrantException("手机号不能为空");
        if (StringUtils.isEmpty(code)) throw new InvalidGrantException("验证码不能为空");

        // 校验用户是否存在
        var user = userService.selectByMobile(mobile);
        if (user == null) throw new InvalidGrantException("该用户不存在");

        // 构建用户Token
        var userDetails = userService.loadUserByUsername(user.getUsername());
        var token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        token.setDetails(parameters);

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, token);
    }
}
