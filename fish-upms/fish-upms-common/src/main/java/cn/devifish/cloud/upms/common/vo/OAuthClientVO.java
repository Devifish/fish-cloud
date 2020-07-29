package cn.devifish.cloud.upms.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * OAuthClientVo
 *
 * @author Devifish
 * @date 2020/7/7 18:25
 */
@Data
public class OAuthClientVO implements Serializable {

    /** 客户端ID **/
    private String clientId;

    /** 资源ID **/
    private String resourceIds;

    /** 适用域 **/
    private String scope;

    /** 授权方式 **/
    private String grantTypes;

    /** 权限 **/
    private String authorities;

}
