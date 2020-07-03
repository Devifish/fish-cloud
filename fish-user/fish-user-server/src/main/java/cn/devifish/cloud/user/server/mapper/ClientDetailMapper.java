package cn.devifish.cloud.user.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

/**
 * ClientDetailMapper
 *
 * @author Devifish
 * @date 2020/7/3 17:39
 */
@Mapper
public interface ClientDetailMapper {

    BaseClientDetails findByClientId(@Param("clientId") String clientId);

}
