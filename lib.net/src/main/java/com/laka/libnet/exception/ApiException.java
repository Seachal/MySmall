package com.laka.libnet.exception;

/**
 * @Author:summer
 * @Date:2019/7/17
 * @Description:接口API错误定义
 */
public class ApiException extends BaseException {

    /**
     * description:定义API类型错误
     **/

    // 用户未登录
    public static final int USER_NOT_LOGIN = 4;

    // token错误
    public static final int TOKEN_ERROR = 5;

    // 搜索列表关键字为null
    public static final int SEARCH_KEY_WORD_IS_NULL = 6;

    // 数据验证不通过（数据不合法）
    public static final int ILLEGALLY_DATA = 7;
    public static final String MSG_ILLEGALLY_DATA = "数据不合法，请稍后重新获取";

    public ApiException(int code, String errorMsg) {
        super(code, errorMsg);
        overrideMsg(code);
    }

    private void overrideMsg(int code) {
        switch (code) {
            case USER_NOT_LOGIN:
                setErrorMsg("用户未登陆~请您登陆");
                break;
            case TOKEN_ERROR:
                setErrorMsg("用户登陆信息过期，请重新登陆");
                break;
            case SEARCH_KEY_WORD_IS_NULL:
                setErrorMsg("搜索的关键字不能为空");
                break;
            default:
                break;
        }
    }
}
