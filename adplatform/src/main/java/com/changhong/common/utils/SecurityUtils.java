package com.changhong.common.utils;

import com.changhong.system.domain.user.User;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午10:43
 */
public class SecurityUtils {

    public static User currentUser() {
        try {
            SecurityContext securityContent = SecurityContextHolder.getContext();
            return (User) securityContent.getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

    public static String currectUserUuid() {
        User user = currentUser();
        if (user == null) return "";
        return user.getUuid();
    }
}
