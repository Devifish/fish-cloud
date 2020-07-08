package cn.devifish.cloud.message.server;

import cn.devifish.cloud.common.core.BaseApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * MessageApplication
 * 消息服务
 *
 * @author Devifish
 * @date 2020/7/8 18:10
 */
@SpringCloudApplication
public class MessageApplication extends BaseApplication {

    public static void main(String[] args) {
        run(MessageApplication.class, args);
    }
}
