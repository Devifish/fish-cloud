package cn.devifish.cloud.user.server;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * UserApplication
 * 用户服务
 *
 * @author Devifish
 * @date 2020/7/1 11:05
 */
@SpringCloudApplication
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
