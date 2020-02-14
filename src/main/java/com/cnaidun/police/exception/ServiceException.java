package com.cnaidun.police.exception;

import java.util.Map;

public class ServiceException extends RuntimeException {
    public ServiceException(String msg) {
        super(msg);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    private String errorCode;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    private String success;

    public ServiceException(String msg, String errorCode) {
        super(msg);
        this.setErrorCode(errorCode);
    }

    public ServiceException(Map<String, Object> map) {
        super(map.get("message").toString());
        this.setErrorCode(map.get("code").toString());
        this.setSuccess(map.get("success").toString());
    }
}
