package com.cnaidun.police.util.wechat.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatUserAuthInfo {

    private String openid;
    private String unionid;
    private String accessToken;
    private String refreshToken;
    private int expiresIn;
    private String scope;
    private Date createTime;   //创建时间
    private Date updateTime;   //更新时间
    private Date expiredTime;  //过期时间 access_token过期时间

}
