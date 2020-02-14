package com.cnaidun.police.config.shiro;

import com.cnaidun.police.constant.Constant;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

/**
 * @ClassName MyHashedCredentialsMatcher
 * @Descriprion 跳过密码校验
 * @Author dongyin
 * @Date 2019/8/26 13:50
 **/
public class MyHashedCredentialsMatcher extends HashedCredentialsMatcher {
    public MyHashedCredentialsMatcher() {
    }

    public MyHashedCredentialsMatcher(String hashAlgorithmName) {
        super(hashAlgorithmName);
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken uToken = (UsernamePasswordToken) token;
        if (uToken.getLoginType().equals(Constant.LOGIN_TYPE_PC)) {
            return super.doCredentialsMatch(token, info);
        } else {
            return true;
        }
    }
}
