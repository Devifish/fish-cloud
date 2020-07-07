package cn.devifish.cloud.common.core;

/**
 * StatusCode
 * 错误码实现接口
 * 如需要自定义相关错误/状态码请实现该接口
 *
 * @author Devifish
 * @date 2020/7/7 14:40
 */
public interface StatusCode {

    /**
     * 获取状态码
     *
     * @return 状态码
     */
    int getCode();

    /**
     * 获取状态详情
     *
     * @return 错误详情
     */
    String getDesc();

}
