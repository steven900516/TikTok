package com.lin.common.result;

/**
 * 规定:
 * #1表示成功
 * #1001～1999 区间表示参数错误
 * #2001～2999 区间表示用户错误
 * #3001～3999 区间表示接口异常
 *
 */
public enum ResultCode {
    /* 成功 */
    SUCCESS(200, "成功"),

    /* 默认失败 */
    COMMON_FAIL(500, "失败"),

    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),

    PARAMS_ERROR(10001, "参数有误"),
    ACCOUNT_PWD_NOT_EXIST(10002, "用户名或密码不存在"),


    TOKEN_ERROR(10003, "token不合法"),
    ACCOUNT_EXIST(10004, "账号已存在"),
    SESSION_TIME_OUT(90001, "会话超时"),
    UPLOAD_FAIL(20001, "上传失败"),

    /* 用户错误 */
    USER_NOT_LOGIN(4010, "用户未登录"),
    USER_ACCOUNT_EXPIRED(4011, "账号已过期，请重新登录"),
    USER_CREDENTIALS_ERROR(4012, "密码错误"),
    USER_ACCOUNT_DISABLE(4013, "账号不可用"),
    USER_ACCOUNT_NOT_EXIST(4014, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(4015, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(4016, "账号下线"),



    /* 用户信息更改错误 */
    USER_DETAIL_NAME_HAS_CHANGED(30000,"名字在一个月内已修改过，无法修改"),
    USER_DETAIL_TTACOUNT_HAS_CHANGED(30001,"抖音号在180天内已更改，无法在此期间做更改"),
    USER_DETAIL_TTACOUNT_EXITST(30002,"抖音号已存在，请更换另一个抖音号"),


    /* Redis错误 */
    REDIS_TYPE_ERROR(40001,"redis请求类型type错误");


    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 根据code获取message
     *
     * @param code
     * @return
     */
    public static String getMessageByCode(Integer code) {
        for (ResultCode ele : values()) {
            if (ele.getCode().equals(code)) {
                return ele.getMessage();
            }
        }
        return null;
    }
}

