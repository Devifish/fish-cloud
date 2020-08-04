package cn.devifish.cloud.message.common.entity;

import cn.devifish.cloud.common.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SmsTemplate
 * 短信模板
 *
 * @author Devifish
 * @date 2020/8/4 17:26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SmsTemplate extends BaseEntity {

    /** 模板编码 **/
    private String code;

    /** 模板内容 **/
    private String content;

    /** 描述 **/
    private String description;

}
