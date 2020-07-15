package cn.devifish.cloud.upms.common.enums;

import cn.devifish.cloud.common.core.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * SexEnum
 * 性别枚举
 *
 * @author Devifish
 * @date 2020/7/11 11:24
 */
public enum SexEnum implements BaseEnum<Integer> {

    /** 未设置 **/
    Unset(0),

    /** 男 **/
    Man(1),

    /** 女 **/
    Woman(2);

    @JsonValue
    @EnumValue
    private final Integer param;

    SexEnum(Integer param) {
        this.param = param;
    }

    @Override
    public Integer getParam() {
        return param;
    }
}
