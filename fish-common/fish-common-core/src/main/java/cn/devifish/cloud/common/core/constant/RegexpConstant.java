package cn.devifish.cloud.common.core.constant;

/**
 * RegexpUtils
 * 正则表达式工具类
 *
 * @author Devifish
 * @date 2020/8/5 17:43
 */
public interface RegexpConstant {

    /** 电子邮箱 **/
    String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /** 手机号码 **/
    String PHONE_NUM = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9|1]))\\d{8}$";

    /** BASE64编码文件 **/
    String BASE64_FILE = "^[/]?([\\da-zA-Z]+[/+]+)*[\\da-zA-Z]+([+=]{1,2}|[/])?$";

    /** 汉字 **/
    String CHINESE_CHARACTER = "^[\\u4e00-\\u9fa5]+$";

    /** 身份证号码 **/
    String ID_CARD = "^\\d{15}|\\d{18}$";

}
