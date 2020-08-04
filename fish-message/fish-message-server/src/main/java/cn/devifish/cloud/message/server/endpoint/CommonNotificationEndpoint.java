package cn.devifish.cloud.message.server.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;

import javax.websocket.server.ServerEndpoint;

/**
 * CommonNotificationEndpoint
 * 公共网站通知 Endpoint
 *
 * @author Devifish
 * @date 2020/8/1 18:26
 */
@Endpoint
@ServerEndpoint("/common/notification")
public class CommonNotificationEndpoint {

}
