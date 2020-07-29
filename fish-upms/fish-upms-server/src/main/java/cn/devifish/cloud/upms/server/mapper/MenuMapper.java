package cn.devifish.cloud.upms.server.mapper;

import cn.devifish.cloud.upms.common.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
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
     * 获取全部菜单数据
     *
     * @return List<Menu>
     */
    List<Menu> selectAll();

    /**
     * 获取全部菜单的权限代码
     * 用于校验角色权限是否正确
     *
     * @return 权限代码集合
     */
    Set<String> selectAllPermission();

    /**
     * 根据父级ID查询菜单
     *
     * @param parentId 父级ID
     * @return List<Menu>
     */
    List<Menu> selectByParentId(Long parentId);

    /**
     * 根据父级ID统计菜单
     *
     * @param parentId 父级ID
     * @return count
     */
    Integer countByParentId(Long parentId);

}
