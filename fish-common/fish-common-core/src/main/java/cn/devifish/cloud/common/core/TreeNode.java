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
public abstract class TreeNode<C extends Collection<? extends TreeNode<C>>> implements Serializable {

    /** 主键ID **/
    private Long id;

    /** 父节点ID **/
    private Long parentId;

    /** 子节点数据 **/
    private C children;

}
