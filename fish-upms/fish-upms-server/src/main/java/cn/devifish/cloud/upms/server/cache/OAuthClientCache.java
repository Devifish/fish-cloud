package cn.devifish.cloud.upms.server.cache;

import cn.devifish.cloud.common.redis.BaseCache;
import cn.devifish.cloud.upms.common.entity.OAuthClient;
import org.springframework.stereotype.Repository;

/**
 * OAuthClientCache
 *
 * @author Devifish
 * @date 2020/7/7 18:04
 */
@Repository
public class OAuthClientCache extends BaseCache<OAuthClient, String> {

    @Override
    public String getBaseCacheKey() {
        return "oauth_client";
    }
}
