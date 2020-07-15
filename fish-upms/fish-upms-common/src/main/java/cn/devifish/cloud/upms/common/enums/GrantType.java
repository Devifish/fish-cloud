package cn.devifish.cloud.upms.common.enums;

import cn.devifish.cloud.common.core.BaseEnum;

/**
 * GrantTypeEnum
 * 授权方式
 *
 * @author Devifish
 * @date 2020/7/6 17:28
 */
public enum GrantType implements BaseEnum<String> {

    /** 授权码模式 **/
    AuthorizationCode("authorization_code"),

    /** 简化模式 **/
    Implicit("implicit"),

    /** 密码模式 **/
    Password("password"),

    /** 客户端模式 **/
    ClientCredentials("client_credentials"),

    /** 刷新Token模式 **/
    RefreshToken("refresh_token");

    private final String param;

    private GrantType(String param) {
        this.param = param;
    }

    @Override
    public String getParam() {
        return param;
    }
}
