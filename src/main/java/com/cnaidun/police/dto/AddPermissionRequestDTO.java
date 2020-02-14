package com.cnaidun.police.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Description 创建菜单实体类
 * @Author gzw
 * @Date 2019/7/2 17:10
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddPermissionRequestDTO {

    /**
     * id
     */
    private Integer id;

    /**
     * 菜单名
     */
    @NotNull(message = "菜单名不可为空")
    private String name;

    /**
     * 资源类型，[menu|button]
     */
    @NotNull(message = "资源类型不可为空")
    private String resourceType;

    /**
     * 资源路径
     */
    private String url;

    /**
     * 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
     */
    @NotNull(message = "权限字符串不可为空")
    private String permission;

    /**
     * 父编号
     */
    @NotNull(message = "父编号不可为空")
    private Integer parentId;

    /**
     * 按钮类型
     */
    private String buttonType;

    /**
     * 一级菜单icon
     */
    private String icon;
}
