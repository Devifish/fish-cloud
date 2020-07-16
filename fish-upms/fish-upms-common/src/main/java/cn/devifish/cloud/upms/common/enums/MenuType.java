package cn.devifish.cloud.upms.common.enums;

import cn.devifish.cloud.common.core.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * MenuType
 * 菜单类型
 *
 * @author Devifish
 * @date 2020/7/16 10:37
 */
public enum MenuType implements BaseEnum<Integer> {

    /** 菜单 **/
    Menu(0),

    /** 按钮 **/
    Button(1);

    @JsonValue
    @EnumValue
    private final Integer param;

    MenuType(Integer param) {
        this.param = param;
    }

    @Override
    public Integer getParam() {
        return param;
    }
}
