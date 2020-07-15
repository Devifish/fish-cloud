package cn.devifish.cloud.upms.server.mapper;

import cn.devifish.cloud.upms.common.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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

}
