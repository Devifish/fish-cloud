package cn.devifish.cloud.common.core;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * BaseEntity
 * 基础实体类
 *
 * @author Devifish
 * @date 2020/7/10 22:51
 */
@Data
public abstract class BaseEntity implements Serializable {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

}
