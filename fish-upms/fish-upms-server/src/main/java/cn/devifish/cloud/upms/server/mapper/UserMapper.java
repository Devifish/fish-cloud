package cn.devifish.cloud.upms.server.mapper;

import cn.devifish.cloud.common.mybatis.Page;
import cn.devifish.cloud.upms.common.dto.UserPageDTO;
import cn.devifish.cloud.upms.common.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * UserMapper
 * 用户 Mapper
 *
 * @author Devifish
 * @date 2020/7/6 11:40
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名称查询用户数据
     *
     * @param username 用户名
     * @return User
     */
    User selectByUsername(String username);

    /**
     * 根据手机号查询用户数据
     *
     * @param telephone 手机号
     * @return User
     */
    User selectByTelephone(String telephone);

    /**
     * 根据用户名称统计用户数据
     * 包含已逻辑删除数据
     *
     * @param username 用户名
     * @return 统计
     */
    Integer countByUsername(String username);

    /**
     * 根据手机号统计用户数据
     * 包含已逻辑删除数据
     *
     * @param telephone 手机号
     * @return 统计
     */
    Integer countByTelephone(String telephone);

    /**
     * 根据相关参数查询用户列表
     *
     * @param param 参数
     * @return List<User>
     */
    List<User> selectList(@Param("param") Map<String, Object> param);

    /**
     * 分页查询用户数据
     * 不包含已逻辑删除数据
     *
     * @param page Page
     * @return Page<User>
     */
    Page<User> selectPage(Page<?> page, @Param("param") UserPageDTO param);

}
