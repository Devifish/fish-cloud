package cn.devifish.cloud.user.server.mapper;

import cn.devifish.cloud.user.common.entity.OAuthClient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * OAuthClientMapper
 * OAuth2 客户端 Mapper
 *
 * @author Devifish
 * @date 2020/7/3 17:39
 */
@Mapper
public interface OAuthClientMapper {

    /**
     * 根据客户端ID查询单个信息
     *
     * @param clientId 客户端ID
     * @return OAuthClient
     */
    OAuthClient findByClientId(@Param("clientId") String clientId);

}
