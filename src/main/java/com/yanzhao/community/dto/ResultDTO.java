package com.yanzhao.community.dto;

import com.yanzhao.community.exception.CustomizeErrorCode;
import com.yanzhao.community.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;
    public static ResultDTO errorOf(Integer code, String message){
        ResultDTO resultDTO= new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static Object errorOf(CustomizeErrorCode errorCode) {

        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }

    public static <T> ResultDTO okof(Object t){
        ResultDTO resultDTO= new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("request success");
        resultDTO.setData(t);
        return resultDTO;
    }

    public static ResultDTO okof(){
        ResultDTO resultDTO= new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("request success");
        return resultDTO;
    }

}
