package com.yanzhao.community.advice;

import com.alibaba.fastjson.JSON;
import com.yanzhao.community.dto.ResultDTO;
import com.yanzhao.community.exception.CustomizeErrorCode;
import com.yanzhao.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e , Model model, HttpServletRequest request, HttpServletResponse response) {
        ResultDTO resultDTO=null;
        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            // send back json
            if (e instanceof CustomizeException){
                resultDTO= ResultDTO.errorOf((CustomizeException) e);
            }
            else{
                resultDTO = (ResultDTO) ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
            } catch (IOException ioe) {
            }
            return null;
        }else //page jump
        if (e instanceof CustomizeException){
            model.addAttribute("message",e.getMessage());}
        else{
            model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
        }
        return new ModelAndView("error");
    }
}
