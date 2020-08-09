package cn.devifish.cloud.message.server.mapper;

import cn.devifish.cloud.message.common.entity.SmsTemplate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * SmsTemplateMapper
 * 短信模板 Mapper
 *
 * @author Devifish
 * @date 2020/8/4 17:47
 */
@Mapper
public interface SmsTemplateMapper extends BaseMapper<SmsTemplate> {

    /**
     * 根据模板编码查询短信模板数据
     *
     * @param code 模板编码
     * @return SmsTemplate
     */
    SmsTemplate selectByCode(String code);

}
