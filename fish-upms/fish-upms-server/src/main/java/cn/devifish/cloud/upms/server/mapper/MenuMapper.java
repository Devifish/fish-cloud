package cn.devifish.cloud.upms.server.mapper;

import cn.devifish.cloud.upms.common.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * MenuMapper
 * 菜单 Mapper
 *
 * @author Devifish
 * @date 2020/7/16 10:57
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 获取全部菜单的权限代码
     * 用于校验角色权限是否正确
     *
     * @return 权限代码集合
     */
    Set<String> selectAllPermission();

}
