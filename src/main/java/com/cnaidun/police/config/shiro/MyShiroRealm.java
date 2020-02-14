package com.cnaidun.police.config.shiro;

import com.cnaidun.police.constant.Constant;
import com.cnaidun.police.entity.SysPermission;
import com.cnaidun.police.entity.SysRole;
import com.cnaidun.police.entity.UserInfo;
import com.cnaidun.police.service.UserInfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * realm
 * @author dongyin
 */
@Component
public class MyShiroRealm extends AuthorizingRealm {
    @Resource
    private UserInfoService userInfoService;
    //TODO 授权逻辑
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserInfo userInfo  = (UserInfo)principals.getPrimaryPrincipal();
        for(SysRole role:userInfo.getRoleList()){
            authorizationInfo.addRole(role.getRole());
            for(SysPermission p:role.getPermissions()){
                authorizationInfo.addStringPermission(p.getPermission());
            }
        }
        return authorizationInfo;
    }

    /**
     * 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确
     * @param token
     * @return
     * @throws AuthenticationException
     */
    //TODO 认证逻辑
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        System.out.println("MyShiroRealm.doGetAuthenticationInfo()");
        //获取用户的输入的账号.
        String account = (String)token.getPrincipal();
        System.out.println(token.getCredentials());
        //通过account从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        UserInfo userInfo = userInfoService.findByAccount(account);
        System.out.println("----->>userInfo="+userInfo);
        if(userInfo == null){
            return null;
        }
        //相当于判断密码
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo,
                userInfo.getPassword(),
                ByteSource.Util.bytes(userInfo.getSalt()),
                getName()
        );
        //session中不需要保存密码
        userInfo.setPassword("");
        //将用户信息放入session中
        SecurityUtils.getSubject().getSession().setAttribute(Constant.SESSION_USER_INFO, userInfo);
        return authenticationInfo;
    }

}