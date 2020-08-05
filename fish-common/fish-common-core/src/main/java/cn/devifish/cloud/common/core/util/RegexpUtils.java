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

    /** BASE64编码文件 **/
    public static final String BASE64_FILE = "^[/]?([\\da-zA-Z]+[/+]+)*[\\da-zA-Z]+([+=]{1,2}|[/])?$";

    /** 汉字 **/
    public static final String CHINESE_CHARACTER = "^[\\u4e00-\\u9fa5]+$";

    /** 身份证号码 **/
    public static final String ID_CARD = "^\\d{15}|\\d{18}$";

}
