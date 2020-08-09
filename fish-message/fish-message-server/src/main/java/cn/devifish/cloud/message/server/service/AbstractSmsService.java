package cn.devifish.cloud.message.server.service;

import cn.devifish.cloud.common.core.exception.UtilException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * AbstractSmsService
 * 短信服务抽象类
 *
 * @author Devifish
 * @date 2020/8/4 17:32
 */
public abstract class AbstractSmsService {

    private static final int DEFAULT_MAX_RETRY = 2;
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\$\\{([^}]+)}");

    @Autowired
    private SmsTemplateService smsTemplateService;

    @Getter
    @Setter
    private String defaultSignature;

    /**
     * 发送短信息
     *
     * @param telephone 电话号码
     * @param content 短信内容
     * @param retry 重试次数
     */
    protected abstract void send(String telephone, String signature, String content, int retry);

    /**
     * 发送短信息
     *
     * @param telephone 电话号码
     * @param content 短信内容
     */
    public void send(String telephone, String signature, String content) {
        send(telephone, signature, content, DEFAULT_MAX_RETRY);
    }

    /**
     * 根据模板发送短信息到手机
     * 自动组装数据到模板中
     *
     * @param telephone 电话号码
     * @param signature 短信签名
     * @param templateCode 模板编号
     * @param params 参数
     */
    public void send(String telephone, String signature, String templateCode, Map<String, Object> params) {
        var content = formatMessage(templateCode, params);
        send(telephone, signature, content);
    }

    /**
     * 根据模板发送短信息到手机
     * 自动组装数据到模板中
     *
     * @param telephone 电话号码
     * @param templateCode 模板编号
     * @param params 参数
     */
    public void send(String telephone, String templateCode, Map<String, Object> params) {
        send(telephone, getDefaultSignature(), templateCode, params);
    }

    /**
     * 格式化消息
     *
     * @param templateCode 模板编号
     * @param params 参数
     * @return 新消息
     */
    private String formatMessage(String templateCode, Map<String, Object> params) {
        var smsTemplate = smsTemplateService.selectByCode(templateCode);
        Assert.notNull(smsTemplate, "模板不存在");

        var template = smsTemplate.getContent();
        var matcher = PLACEHOLDER_PATTERN.matcher(template);
        while (matcher.find()) {
            var key = matcher.group(1);
            if (params.containsKey(key)) {
                var value = params.get(key);
                template = matcher.replaceFirst(value.toString());
                matcher = PLACEHOLDER_PATTERN.matcher(template);
            }else {
                throw new UtilException("缺少必要参数：" + key);
            }
        }
        return template;
    }

}
