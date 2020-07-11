package cn.devifish.cloud.common.security.util;

import cn.devifish.cloud.common.security.BasicUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * SecurityUtil
 * 安全工具类
 *
 * @author Devifish
 * @date 2020/7/11 17:15
 */
public class SecurityUtil {

    /**
     * 获取当前用户鉴权信息
     *
     * @return authentication
     */
    public static Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        return context.getAuthentication();
    }

    /**
     * 获取当前用户的身份信息
     *
     * @return Principal
     */
    public static BasicUser getPrincipal() {
        Authentication authentication = getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof BasicUser) {
                return (BasicUser) principal;
            }
        }
        return null;
    }

}
