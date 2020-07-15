package cn.devifish.cloud.upms.server.mapper;

import cn.devifish.cloud.upms.common.entity.OAuthClient;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * OAuthClientMapper
 * OAuth2 客户端 Mapper
 *
 * @author Devifish
 * @date 2020/7/3 17:39
 */
@Mapper
public interface OAuthClientMapper extends BaseMapper<OAuthClient> {
}
