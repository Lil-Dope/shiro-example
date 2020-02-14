package com.cnaidun.police.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 系统权限
 * @author dongyin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysPermission implements Serializable {
    /**
     * id
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 资源类型，[menu|button]
     */
    private String resourceType;
    /**
     * 资源路径
     */
    private String url;
    /**
     * 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
     */
    private String permission;
    /**
     * 父编号
     */
    private Long parentId;
    /**
         * 是否有用  0无用  1有用
     */
    private Boolean available;
    /**
     * 一级菜单icon
     */
    private String icon;
    /**
     * 权限 --  角色
     */
    private List<SysRole> roles;

}