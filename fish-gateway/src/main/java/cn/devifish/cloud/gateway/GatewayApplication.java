package cn.devifish.cloud.gateway;

import cn.devifish.cloud.common.core.annotation.FishCloudApplication;
import cn.devifish.cloud.common.core.base.BaseApplication;

/**
 * GatewayApplication
 * 网关应用
 *
 * @author Devifish
 * @date 2020/6/30 15:05
 */
@FishCloudApplication
public class GatewayApplication extends BaseApplication {

    public static void main(String[] args) {
        run(GatewayApplication.class, args);
    }
}
