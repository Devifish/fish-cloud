package cn.devifish.cloud.common.rabbitmq.util;

import cn.devifish.cloud.common.rabbitmq.constant.RabbitConstant;

/**
 * RabbitUtils
 * Rabbit工具
 *
 * @author Devifish
 * @date 2020/8/10 18:27
 */
public class RabbitUtils {

    /**
     * 创建交换机名称
     *
     * @param name 名称
     * @return 交换机名称
     */
    public static String exchangeName(String... name) {
        return String.join(
            RabbitConstant.EXCHANGE_NAME_SEPARATOR, name) +
            RabbitConstant.EXCHANGE_NAME_SUFFIX;
    }

    /**
     * 创建路由名称
     *
     * @param name 名称
     * @return 路由名称
     */
    public static String routingName(String... name) {
        return String.join(RabbitConstant.NAME_SEPARATOR, name);
    }

    /**
     * 创建队列名称
     *
     * @param name 名称
     * @return 队列名称
     */
    public static String queueName(String... name) {
        return String.join(RabbitConstant.NAME_SEPARATOR, name);
    }

}
