package cn.devifish.cloud.upms.server.security;

import cn.devifish.cloud.common.core.exception.BizException;
import cn.devifish.cloud.upms.common.entity.User;
import cn.devifish.cloud.upms.common.enums.SmsCodeType;
import cn.devifish.cloud.upms.server.service.SmsCodeService;
import cn.devifish.cloud.upms.server.service.UserService;
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
    private final SmsCodeService smsCodeService;

    protected SmsCodeTokenGranter(
        UserService userService,
        SmsCodeService smsCodeService,
        AuthorizationServerTokenServices tokenServices,
        ClientDetailsService clientDetailsService,
        OAuth2RequestFactory requestFactory) {

        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.userService = userService;
        this.smsCodeService = smsCodeService;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        var parameters = new HashMap<>(tokenRequest.getRequestParameters());
        var telephone = parameters.get("telephone");
        var code = parameters.get("code");
        parameters.remove("code");

        // 校验参数
        User user;
        try {
            user = userService.selectByTelephone(telephone);
            if (user == null) throw new InvalidGrantException("该用户不存在");
            if (!smsCodeService.verify(telephone, SmsCodeType.UserLogin, code))
                throw new InvalidGrantException("验证码不正确, 请重试");

            smsCodeService.delete(telephone, SmsCodeType.UserLogin);
        } catch (BizException exception) {
            throw new InvalidGrantException(exception.getMessage());
        }

        // 构建用户Token
        var userDetails = userService.loadUserByUsername(user.getUsername());
        var token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        token.setDetails(parameters);

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, token);
    }
}
