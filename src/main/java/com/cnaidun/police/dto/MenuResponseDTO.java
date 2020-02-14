package com.cnaidun.police.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author dongyin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuResponseDTO {
    /**
     * 元素id
     */
    private Long id;
    /**
     * 元素名
     */
    private String label;
    /**
     * 元素类型   menu|button
     */
    private String type;
    /**
     * 菜单路径
     */
    private String url;
    /**
     * 按钮类型
     */
    private String buttonType;
    /**
     * 父id  一级菜单为0  二级菜单对应一级id  按钮对应二级菜单id
     */
    private Long parentId;
    /**
     * 一级菜单icon
     */
    private String icon;
    /**
     * 子集合
     */
    private List<MenuResponseDTO> children;
}
