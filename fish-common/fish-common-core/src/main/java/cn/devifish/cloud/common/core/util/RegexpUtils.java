package cn.devifish.cloud.common.core.util;

/**
 * RegexpUtils
 * 正则表达式工具类
 *
 * @author Devifish
 * @date 2020/8/5 17:43
 */
public class RegexpUtils {

    /** 电子邮箱 **/
    public static final String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /** 手机号码 **/
    public static final String PHONE_NUM = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9|1]))\\d{8}$";

    /** BASE64编码文件 **/
    public static final String BASE64_FILE = "^[/]?([\\da-zA-Z]+[/+]+)*[\\da-zA-Z]+([+=]{1,2}|[/])?$";

    /** 汉字 **/
    public static final String CHINESE_CHARACTER = "^[\\u4e00-\\u9fa5]+$";

    /** 身份证号码 **/
    public static final String ID_CARD = "^\\d{15}|\\d{18}$";



}
