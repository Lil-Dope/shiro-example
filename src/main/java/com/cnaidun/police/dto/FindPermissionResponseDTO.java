package com.cnaidun.police.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 获取菜单列表  返回实体类
 * @Author gzw
 * @Date 2019/7/2 15:54
 * @Version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FindPermissionResponseDTO {

    /**
     * id
     */
    private Integer id;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 资源类型，[menu|button]
     */
    private String resourceType;

    /**
     * 父菜单名
     */
    private String parentMenuName;

    /**
     * url
     */
    private String url;

    /**
     * 权限
     */
    private String permission;

    /**
     * 父id
     */
    private Integer parentId;

    /**
     * 按钮类型
     */
    private String buttonType;

    /**
     * 以及菜单icon
     */
    private String icon;
}
