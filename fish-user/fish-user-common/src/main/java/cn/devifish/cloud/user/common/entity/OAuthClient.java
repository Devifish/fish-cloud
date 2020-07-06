package cn.devifish.cloud.user.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * OAuthClient
 * OAuth2 客户端
 *
 * @author Devifish
 * @date 2020/7/6 10:18
 */
@Data
public class OAuthClient implements Serializable {


    private String clientId;
    private String resourceIds;
    private String clientSecret;
    private String scope;

}
