package cn.devifish.cloud.message.server.service;

import cn.devifish.cloud.message.common.dto.SmsSendDTO;

import java.util.Map;

/**
 * SmsService
 * 短信服务
 *
 * @author Devifish
 * @date 2020/8/24 17:59
 */
public interface SmsService {

    /**
     * 发送短信息
     *
     * @param data 短信参数
     */
    default void send(SmsSendDTO data) {
        var telephone = data.getTelephone();
        var templateCode = data.getTemplateCode();
        var params = data.getParams();

        send(telephone, templateCode, params);
    }

    /**
     * 发送短信息
     *
     * @param telephone 电话号码
     * @param content 短信内容
     */
    void send(String telephone, String signature, String content);

    /**
     * 根据模板发送短信息到手机
     * 自动组装数据到模板中
     *
     * @param telephone 电话号码
     * @param signature 短信签名
     * @param templateCode 模板编号
     * @param params 参数
     */
    void send(String telephone, String signature, String templateCode, Map<String, Object> params);

    /**
     * 根据模板发送短信息到手机
     * 自动组装数据到模板中
     *
     * @param telephone 电话号码
     * @param templateCode 模板编号
     * @param params 参数
     */
    void send(String telephone, String templateCode, Map<String, Object> params);

}
