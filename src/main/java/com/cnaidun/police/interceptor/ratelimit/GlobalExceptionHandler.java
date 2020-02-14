package com.cnaidun.police.interceptor.ratelimit;

import com.cnaidun.police.util.BussinessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 * @author dy
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 拦截业务异常
     */
    @ExceptionHandler(BussinessException.class)
    @ResponseBody
    public Object bussinessException(BussinessException e) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("code", e.getCode());
        jsonMap.put("msg", e.getMessage());
        return jsonMap;
    }
}
