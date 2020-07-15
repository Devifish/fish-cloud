package cn.devifish.cloud.upms.server;

import cn.devifish.cloud.common.core.BaseApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * UpmsApplication
 * 用户权限服务
 *
 * @author Devifish
 * @date 2020/7/1 11:05
 */
@SpringCloudApplication
public class UpmsApplication extends BaseApplication {

    public static void main(String[] args) {
        run(UpmsApplication.class, args);
    }
}
