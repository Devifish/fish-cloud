package cn.devifish.cloud.common.core;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * LogicDeleteEntity
 * 逻辑删除实体类
 * 用于需要逻辑删除的业务代码
 *
 * @author Devifish
 * @date 2020/7/10 23:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class LogicDeleteEntity<ID extends Serializable> extends BaseEntity<ID> {

    /**
     * 删除标记
     */
    @TableLogic
    private Boolean deleteFlag;

}
