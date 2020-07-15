package com.yanzhao.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND(2001,"the problem you find does not exist"),
    TARGET_PARAM_NOT_FOUND(2002,"did not pick any question to answer"),
    NO_LOGIN(2003,"please login first"),
    SYS_ERROR(2004,"cannot reach out the service,please try again..."),
    TYPE_PARAM_WRONG(2005,"comment type error or do not exist"),
    COMMENT_NOT_FOUND(2006,"the comment you reply does not exist"),
    CONTENT_IS_EMPTY(2007,"comment is missing"),
    ;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {

        this.message = message;
        this.code=code;
    }


}
