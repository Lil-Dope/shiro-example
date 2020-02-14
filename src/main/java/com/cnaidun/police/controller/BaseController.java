package com.cnaidun.police.controller;

import com.cnaidun.police.constant.Constant;
import com.cnaidun.police.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制层基类.
 */
public class BaseController {
    public UserInfo getUserInfo(){
        Session session = SecurityUtils.getSubject().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute(Constant.SESSION_USER_INFO);
        return userInfo;
    }
}
