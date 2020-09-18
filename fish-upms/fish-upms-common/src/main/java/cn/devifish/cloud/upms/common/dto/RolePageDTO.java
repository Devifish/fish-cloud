package cn.devifish.cloud.upms.common.dto;

import cn.devifish.cloud.common.core.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * RolePageDTO
 * 角色分页DTO
 *
 * @author Devifish
 * @date 2020/9/18 17:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RolePageDTO extends PageParam {

    /** 名称 **/
    private String name;

}
