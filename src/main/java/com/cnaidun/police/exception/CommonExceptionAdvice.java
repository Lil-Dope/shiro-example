package com.cnaidun.police.exception;

import com.cnaidun.police.util.ApiCode;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ResponseBody
public class CommonExceptionAdvice {
    private static Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);


    /**
     * 未认证异常
     * @return
     */
    @ExceptionHandler(UnauthenticatedException.class)
    public Map<String, Object> handleUnauthenticatedException() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", false);
        map.put("code", "1001");
        map.put("msg", "未认证异常");
        return map;
    }

    /**
     * 无权限访问接口
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    public Map<String, Object> handleUnauthorizedException() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", false);
        map.put("code", "1002");
        map.put("msg", "您未被授权访问该接口！");
        return map;
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Map<String, Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.error("缺少请求参数", e);
        return ApiCode.ERROR_PARAMS_COMPLETE.getValue();
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error("参数解析失败", e);
        return ApiCode.ERROR_PARAMS.getValue();
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("参数验证失败", e);
        return ApiCode.ERROR_REPONSE_VALIDATA.getValue();
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public Map<String, Object> handleBindException(BindException e) {
        logger.error("参数绑定失败", e);
        return ApiCode.ERROR_REPONSE_BINGDING.getValue();
    }


    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Map<String, Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error("不支持当前请求方法", e);
        return ApiCode.ERROR_METHOD_NOT.getValue();
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Map<String, Object> handleHttpMediaTypeNotSupportedException(Exception e) {
        logger.error("不支持当前媒体类型", e);
        return ApiCode.ERROR_MEDIA_NOT.getValue();
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServiceException.class)
    public Map<String, Object> handleServiceException(ServiceException e) {
        logger.error("业务逻辑异常", e);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", false);
        map.put("code", e.getErrorCode());
        map.put("msg", e.getMessage());
        return map;
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, Object> handleException(Exception e) {
        logger.error("通用异常", e);
        if (e instanceof NullPointerException) {
            return ApiCode.ERROR_NULL_POINTER.getValue();
        } else if (e instanceof ClassCastException) {
            return ApiCode.ERROR_CLASS_CASE.getValue();
        } else if (e instanceof ArrayIndexOutOfBoundsException) {
            return ApiCode.ERROR_ARRAY_INDEX.getValue();
        } else if (e instanceof NumberFormatException) {
            return ApiCode.ERROR_NUMBER.getValue();
        } else if (e instanceof NoSuchMethodException) {
            return ApiCode.ERROR_NOT_METHOD.getValue();
        } else if (e instanceof ArithmeticException) {
            return ApiCode.ERROR_ARITHMETIC.getValue();
        } else if (e instanceof IllegalAccessException) {
            return ApiCode.ERROR_ILLEGALACCESS.getValue();
        } else if (e instanceof ArrayStoreException) {
            return ApiCode.ERROR_ARRAY_STORE.getValue();
        } else if (e instanceof FileNotFoundException) {
            return ApiCode.ERROR_FILE_NOT.getValue();
        } else if (e instanceof InstantiationException) {
            return ApiCode.ERROR_INSTANTIATION.getValue();
        } else if (e instanceof BadSqlGrammarException) {
            return ApiCode.ERROR_SQL_GRAMMAR.getValue();
        }

        return ApiCode.ERROR.getValue();
    }

    /**
     * 操作数据库出现异常:名称重复，外键关联
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Map<String, Object> handleException(DataIntegrityViolationException e) {
        logger.error("操作数据库出现异常:", e);
        return ApiCode.ERROR_DATA.getValue();
    }
}
