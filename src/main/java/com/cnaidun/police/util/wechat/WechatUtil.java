package com.cnaidun.police.util.wechat;

import com.cnaidun.police.util.HttpUtil;
import com.cnaidun.police.util.JsonUtil;
import com.cnaidun.police.util.wechat.bean.WechatUserAuthInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName WechatUtil
 * @Descriprion 微信扫码登录工具类
 * @Author dongyin
 * @Date 2019/8/27 12:56
 **/
public class WechatUtil {
    private static String APPID = "wxd07c689cd2face68";
    private static String APP_SECRET = "56bec14488814d1ea7865899c4a872b0";


    /**
     * @Author dongyin
     * @Description 根据扫码code获取数据
     * @Date 2019/8/27 13:04
     * @Param [code]
     * @return com.cnaidun.police.util.wechat.bean.WechatUserAuthInfo
    **/
    public static WechatUserAuthInfo getAccessToken(String code) {
        Map<String, String> params = new HashMap<>(4);
        params.put("grant_type", "authorization_code");
        params.put("appid", APPID);
        params.put("secret", APP_SECRET);
        params.put("code",code);
        WechatUserAuthInfo wechatUserAuthInfo;
        try {
            String response = HttpUtil.get("https://api.weixin.qq.com/sns/oauth2/access_token", params);
            wechatUserAuthInfo = JsonUtil.parseJson(response, WechatUserAuthInfo.class);
        }catch (Exception e){
            return null;
        }
        return wechatUserAuthInfo;
    }
}
