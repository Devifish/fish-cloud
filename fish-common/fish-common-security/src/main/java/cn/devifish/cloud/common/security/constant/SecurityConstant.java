package cn.devifish.cloud.common.security.constant;

import java.util.concurrent.TimeUnit;

/**
 * SecurityConstant
 * 安全相关常量
 *
 * @author Devifish
 * @date 2020/7/3 16:42
 */
public interface SecurityConstant {

    /** OAUTH2 TOKEN 缓存前缀 **/
    String OAUTH_CACHE_PREFIX = "fish_common_security:";

    /** Token 头信息前缀 **/
    String OAUTH_HEADER_PREFIX = "Bearer";

    /** 默认角色前缀 **/
    String DEFAULT_ROLE_PREFIX = "ROLE_";

    /** OAUTH2 TOKEN 生存时间 **/
    long DEFAULT_REFRESH_TOKEN_VALIDITY = TimeUnit.DAYS.toSeconds(7);
    long DEFAULT_ACCESS_TOKEN_VALIDITY = TimeUnit.DAYS.toSeconds(1);

}
