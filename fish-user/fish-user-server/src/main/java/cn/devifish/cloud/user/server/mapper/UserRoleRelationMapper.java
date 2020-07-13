package cn.devifish.cloud.user.server.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * UserRoleRelationMapper
 * 用户角色关系 Mapper
 *
 * @author Devifish
 * @date 2020/7/13 17:32
 */
@Mapper
public interface UserRoleRelationMapper {

    /**
     * 根据角色ID查询关联的全部用户ID
     *
     * @param roleId 角色ID
     * @return 用户ID
     */
    List<Long> selectUserIdByRoleId(Long roleId);

    /**
     * 根具用户ID删除用户角色关系
     *
     * @param userId 用户ID
     * @return 删除数
     */
    int deleteByUserId(Long userId);

    /**
     * 根具角色ID删除用户角色关系
     *
     * @param roleId 角色ID
     * @return 删除数
     */
    int deleteByRoleId(Long roleId);

}
