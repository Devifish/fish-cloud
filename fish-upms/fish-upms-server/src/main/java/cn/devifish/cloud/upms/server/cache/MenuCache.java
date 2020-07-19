package cn.devifish.cloud.upms.server.cache;

import cn.devifish.cloud.common.redis.HashCache;
import cn.devifish.cloud.upms.common.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.function.Function;

/**
 * MenuCache
 * 菜单缓存
 *
 * @author Devifish
 * @date 2020/7/19 22:19
 */
@Repository
public class MenuCache extends HashCache<Menu, Long> {

    @Override
    public Function<Menu, Long> keyMapper() {
        return Menu::getId;
    }
}
