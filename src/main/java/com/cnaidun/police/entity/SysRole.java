package com.cnaidun.police.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 系统角色
 * @author dongyin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysRole {
    /**
     * id
     */
    private Integer id;
    /**
     * 角色标识程序中判断使用,如"admin",这个是唯一的:
     */
    private String role;
    /**
     * 角色描述,UI界面显示使用
     */
    private String description;
    /**
     * 是否可用,如果不可用将不会添加给用户
     */
    private Boolean available;
    /**
     * 角色 -- 权限关系：多对多关系
     */
    private List<SysPermission> permissions;
    /**
     * 用户 - 角色关系定义
     */
    private List<UserInfo> userInfos;
}