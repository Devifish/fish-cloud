package cn.devifish.cloud.message.server.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController
 *
 * @author Devifish
 * @date 2020/7/11 17:37
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/principal")
    public Object principal() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication.getPrincipal();
    }
}
