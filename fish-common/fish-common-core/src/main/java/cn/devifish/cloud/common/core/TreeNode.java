package cn.devifish.cloud.common.core;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

/**
 * TreeNode
 * 树结构数据抽象
 *
 * @author Devifish
 * @date 2020/8/1 14:52
 */
@Data
public abstract class TreeNode<ID extends Serializable,
    C extends Collection<? extends TreeNode<ID, C>>> implements Serializable {

    /** 主键ID **/
    private ID id;

    /** 父节点ID **/
    private ID parentId;

    /** 子节点数据 **/
    private C children;

}
