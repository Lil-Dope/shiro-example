package com.cnaidun.police.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统API返回参数
 * <p/>
 */
public enum ApiCode {
    SUCCESS(true, 200, "成功"),
    ERROR(false, 40001, "系统异常!"),
    ERROR_CUSTOM_MSG(false, 50000, "自定义的返回参数!"),
    ERROR_PARAMS_COMPLETE(false, 50001, "参数不完整!"),
    ERROR_PARAMS(false, 50002, "参数解析失败"),
    ERROR_REPONSE_VALIDATA(false, 50003, "参数验证失败!"),
    ERROR_REPONSE_BINGDING(false, 50004, "参数绑定失败!"),
    ERROR_METHOD_NOT(false, 50005, "不支持当前请求方法!"),
    ERROR_MEDIA_NOT(false, 50006, "不支持当前媒体类型!"),
    ERROR_CLASS_CASE(false, 50007, "类型强制转换异常!"),
    ERROR_ARRAY_INDEX(false, 50008, "数组下标越界异常!"),
    ERROR_NUMBER(false, 50009, "字符串转换为数字异常!"),
    ERROR_NOT_METHOD(false, 50010, "方法未找到异常!"),
    ERROR_DATA(false, 50011, "数据库异常!"),
    ERROR_ARITHMETIC(false, 50012, "算术异常!"),
    ERROR_NULL_POINTER(false, 50013, "对象为空!"),
    ERROR_ILLEGALACCESS(false, 50014, "访问权限异常!"),
    ERROR_ARRAY_STORE(false, 50015, "数组存储异常!"),
    ERROR_FILE_NOT(false, 50016, "未找到文件异常!"),
    ERROR_SQL_GRAMMAR(false, 50017, "sql错误!"),
    ERROR_JWT_AUTH(false,50018, "Jwt验证失败"),
    ERROR_INSTANTIATION(false, 50018, "实例化异常!");

    private Map<String, Object> value;

    public Map<String, Object> getValue() {
        return value;
    }

    ApiCode(boolean success, int statusCode, String message) {
        if (null == this.value) {
            this.value = new HashMap<String, Object>();
            value.put("success", success);
            value.put("code", statusCode);
            value.put("message", message);
        }
    }

    /**
     * 只修改message
     * @param message
     * @return
     */
    public Map<String, Object> getCustomApiCodeValue(String message) {
        value.put("message",message);
        return value;
    }

    /**
     * 只修改message
     * @param data
     * @return
     */
    public Map<String, Object> getCustomApiCodeValue(Object data) {
        value.put("data",data);
        return value;
    }

    /**
     * 修改mesage 和 data
     * @param message
     * @param data
     * @return
     */
    public Map<String, Object> getCustomApiCodeValue(Object data,String message) {
        value.put("data",data);
        value.put("message",message);
        return value;
    }
}
