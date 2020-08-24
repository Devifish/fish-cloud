package cn.devifish.cloud.message.server.service.sms;

import cn.devifish.cloud.common.core.exception.UtilException;
import cn.devifish.cloud.message.server.config.ShortMessageServiceProperties;
import cn.devifish.cloud.message.server.config.ShortMessageServiceProperties.AliYunSMSConfig;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * AliYunSmsService
 *
 * @author Devifish
 * @date 2020/8/9 17:01
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnBean(IAcsClient.class)
public class AliYunSmsService extends AbstractSmsService {

    private final ShortMessageServiceProperties properties;
    private final IAcsClient client;

    private AliYunSMSConfig config;

    @PostConstruct
    private void init() {
        this.config = properties.getAliyun();
    }

    /**
     * 根据模板发送短信息到手机
     * 自动组装数据到模板中
     *
     * @param telephone 电话号码
     * @param templateCode 模板编号
     * @param params 参数
     */
    @Override
    public void send(String telephone, String signature, String templateCode, Map<String, Object> params) {
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(config.getDomain());
        request.setSysVersion(config.getVersion());
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", config.getRegionId());
        request.putQueryParameter("PhoneNumbers", telephone);
        request.putQueryParameter("SignName", signature);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", JSON.toJSONString(params));

        try {
            var response = client.getCommonResponse(request);
            if (log.isDebugEnabled())
                log.debug("阿里云发送短信成功 {}", response.getData());

        } catch (ClientException e) {
            log.error("阿里云发送短信失败 telephone: {} templateCode: {} params: {}", telephone, templateCode, params, e);
        }
    }

    /**
     * 发送短信息
     *
     * @param telephone 电话号码
     * @param content 短信内容
     * @param retry 重试次数
     */
    @Deprecated
    @Override
    protected void send(String telephone, String signature, String content, int retry) {
        throw new UtilException("ALiYun短信服务暂不支持直接推送内容");
    }

    /**
     * 发送短信息
     *
     * @param telephone 电话号码
     * @param content 短信内容
     */
    @Deprecated
    @Override
    public void send(String telephone, String signature, String content) {
        super.send(telephone, signature, content);
    }
}
