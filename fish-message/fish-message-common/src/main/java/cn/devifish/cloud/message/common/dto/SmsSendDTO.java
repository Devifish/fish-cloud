package cn.devifish.cloud.message.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * SendSmsDTO
 * 短信发送DTO
 *
 * @author Devifish
 * @date 2020/8/24 17:42
 */
@Data
public class SmsSendDTO implements Serializable {

    /** 接收人手机号 **/
    private String telephone;

    /** 短信模板编码 **/
    private String templateCode;

    /** 短信模板参数 **/
    private Map<String, Object> params;

}
