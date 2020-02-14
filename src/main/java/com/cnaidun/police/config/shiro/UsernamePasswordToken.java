package com.cnaidun.police.config.shiro;

import com.cnaidun.police.constant.Constant;
import lombok.Data;

/**
 * @ClassName UsernamePasswordToken
 * @Descriprion 免密登录
 * @Author dongyin
 * @Date 2019/8/26 13:45
 **/
@Data
public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {
    private static final long serialVersionUID = 1L;
    /**
     * 登录方式 微信
     */
    private String loginType;

    public UsernamePasswordToken() {
        super();
    }
    /**
     * 账号密码登录
     */
    public UsernamePasswordToken(String username, String password) {
        super(username, password);
        this.loginType = Constant.LOGIN_TYPE_PC;
    }

    /**
     * 扫码免密登录
     */
    public UsernamePasswordToken(String username) {
        super(username, "", false, null);
        this.loginType = Constant.LOGIN_TYPE_WECHAT;
    }
}
