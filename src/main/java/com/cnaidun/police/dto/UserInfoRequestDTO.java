package com.cnaidun.police.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoRequestDTO {

    /**
     * id
     */
    private Integer id;
    /**
     * 帐号
     */
    @NotNull(message = "账号不能为空")
    private String account;
    /**
     * 名称（昵称或者真实姓名，不同系统不同定义）
     */
    @NotNull(message = "用户名不能为空")
    private String userName;
    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    private String password;

    private String createTime;
}
