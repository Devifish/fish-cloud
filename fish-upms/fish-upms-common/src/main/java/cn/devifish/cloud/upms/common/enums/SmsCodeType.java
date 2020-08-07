package cn.devifish.cloud.upms.common.enums;

import cn.devifish.cloud.common.core.BaseEnum;

/**
 * SmsCodeType
 * 短信验证码类型
 *
 * @author Devifish
 * @date 2020/8/7 9:41
 */
public enum SmsCodeType implements BaseEnum<String> {

    UserLogin("user_login"),
    UserRegister("user_register"),
    ResetPassword("reset_password");

    private final String param;

    SmsCodeType(String param) {
        this.param = param;
    }

    @Override
    public String getParam() {
        return param;
    }
}
