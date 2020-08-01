package cn.devifish.cloud.common.core;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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

    /** 主键ID **/
    @TableId
    private Long id;

    /** 创建时间 **/
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 修改时间 **/
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    public BaseEntity() {}
    public BaseEntity(Long id) {
        this.id = id;
    }
}
