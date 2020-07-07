package cn.devifish.cloud.common.core;

/**
 * ErrorStatus
 * 错误状态枚举类
 *
 * @author Devifish
 * @date 2020/7/7 14:46
 */
public enum ErrorStatus implements StatusCode {

    /** 常规 **/
    Other(-1, "其他"),
    Ok(200, "ok"),

    /** 4xx: 访问及权限错误 **/
    BadRequest(400, "请求失败"),
    Unauthorized(401, "当前请求需要用户登录"),
    Forbidden(403, "未授权访问"),
    PreconditionFailed(412, "参数不合法"),

    /** 5xx: 服务端运行错误 **/
    InternalServerError(500, "内部错误"),

    /** 6xx: Token相关错误 **/
    TokenExpired(601, "Token已过期"),
    TokenInvalid(602, "无效的Token");

    private final int code;
    private final String desc;

    ErrorStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
