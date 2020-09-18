package cn.devifish.cloud.common.core;

import lombok.Data;

import java.io.Serializable;

/**
 * PageParam
 * 分页参数
 *
 * @author Devifish
 * @date 2020/9/18 11:04
 */
@Data
public class PageParam implements Serializable {

    private static final Integer DEFAULT_CURRENT = 1;
    private static final Integer DEFAULT_SIZE = 10;

    /** 当页条数 **/
    private Long size;

    /** 当前页 **/
    private Long current;

    public PageParam() {
        this.size = DEFAULT_SIZE.longValue();
        this.current = DEFAULT_CURRENT.longValue();
    }

}
