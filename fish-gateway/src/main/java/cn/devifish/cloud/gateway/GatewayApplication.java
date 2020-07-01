package cn.devifish.cloud.gateway;

import cn.devifish.cloud.common.core.annotation.FishCloudApplication;
import org.springframework.boot.SpringApplication;

/**
 * GatewayApplication
 * 网关应用
 *
 * @author Devifish
 * @date 2020/6/30 15:05
 */
@FishCloudApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
