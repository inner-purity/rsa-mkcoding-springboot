package com.rsa.common;

public class ExceptionMessage {

    //事务处理异常
    public static final String REQUEST_PATH_BANNED = "本次请求暂时不可处理，请等待系统开放接口";
    public static final String NO_AUTH_TODO = "开发权限不够，暂处于观察者模式";
    public static final String HAD_BEEN_ADD = "已在库中，请勿重复添加";
    public static final String NO_SUCH_USER = "用户不存在";
    public static final String USER_NOT_UNIQUE = "用户已存在,换个名字试试";
    public static final String EMAIL_NOT_UNIQUE = "该邮箱已被注册!";
    public static final String ERROR_PASSWORD = "密码错误";
    public static final String ERROR_PRIOR_PASSWORD = "原密码错误，请重新输入";
    public static final String CHECK_PASSWORD_ERROR = "密码错误,验证失败";
    public static final String NO_AUTH_TO_GIVE_USER_AUTH = "权限不匹配，暂时无法为用户分配权限";
    public static final String USER_HAD_BEEN_DELETED_OR_BANNED = "该账户已被禁用或删除，请联系开发者处理";
    public static final String IP_HAD_BEEN_BANNED = "您的IP已被封禁，如有疑问请联系开发者处理";
    public static final String EMAIL_ERROR = "验证失败! 邮箱错误";
    public static final String EMAIL_CODE_ERROR = "验证失败! 验证码错误";
    public static final String CODE_OUTDATED = "验证码已过期，请尝试重新获取";
    public static final String EMAIL_TYPE_NOT_ALLOWED = "当前暂不支持此类邮箱的验证哦~";
    public static final String NO_SUCH_EMAIL_USER = "用户不存在 或 该邮箱未绑定账户";
    public static final String QRCODE_HAD_BEEN_DISABLED = "二维码已过期/失效";

    //异常系统崩溃
    public static final String DO_EXCEPTION_SYSTEM_ERROR = "异常处理及记录系统崩溃(***紧急***)   ";

    public static final String EMAIL_CODE_HAD_BEEN_SEND = "邮箱验证码已发送，有效期内请勿重复申请";

    //数据处理异常

    //系统异常

    //其他异常


}
