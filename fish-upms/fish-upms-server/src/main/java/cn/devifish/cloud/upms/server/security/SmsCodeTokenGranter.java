package cn.devifish.cloud.upms.server.security;

import cn.devifish.cloud.upms.common.enums.SmsCodeType;
import cn.devifish.cloud.upms.server.service.SmsCodeService;
import cn.devifish.cloud.upms.server.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.HashMap;
import java.util.Objects;

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

        // 校验用户手机号
        if (StringUtils.isEmpty(telephone)) throw new InvalidGrantException("手机号不能为空");
        var user = userService.selectByTelephone(telephone);
        if (user == null) throw new InvalidGrantException("该用户不存在");

        // 校验验证码
        if (StringUtils.isEmpty(code)) throw new InvalidGrantException("验证码不能为空");
        var cache_code = smsCodeService.get(telephone, SmsCodeType.UserLogin);
        if (cache_code == null) throw new InvalidGrantException("验证码已失效, 请重新获取验证码");
        if (!Objects.equals(cache_code, code)) throw new InvalidGrantException("验证码不正确, 请重试");
        smsCodeService.delete(telephone, SmsCodeType.UserLogin);

        // 构建用户Token
        var userDetails = userService.loadUserByUsername(user.getUsername());
        var token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        token.setDetails(parameters);

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, token);
    }
}
