package cn.devifish.cloud.upms.common.dto;

import cn.devifish.cloud.common.core.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * UserPageDTO
 * 用户分页DTO
 *
 * @author Devifish
 * @date 2020/9/18 17:10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserPageDTO extends PageParam {

    /** 用户名 **/
    private String username;

    /** 电话号码 **/
    private String telephone;

}
