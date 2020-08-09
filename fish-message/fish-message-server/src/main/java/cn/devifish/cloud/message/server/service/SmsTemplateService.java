package cn.devifish.cloud.message.server.service;

import cn.devifish.cloud.common.core.exception.BizException;
import cn.devifish.cloud.message.common.entity.SmsTemplate;
import cn.devifish.cloud.message.server.mapper.SmsTemplateMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static cn.devifish.cloud.common.core.MessageCode.PreconditionFailed;

/**
 * SmsTemplateService
 * 短信模板服务
 *
 * @author Devifish
 * @date 2020/8/4 17:58
 */
@Service
@RequiredArgsConstructor
public class SmsTemplateService {

    private final SmsTemplateMapper smsTemplateMapper;

    /**
     * 根据模板编码查询短信模板数据
     *
     * @param code 模板编码
     * @return SmsTemplate
     */
    public SmsTemplate selectByCode(String code) {
        if (StringUtils.isEmpty(code))
            throw new BizException(PreconditionFailed, "模板编码不能为空");

        return smsTemplateMapper.selectByCode(code);
    }

}
