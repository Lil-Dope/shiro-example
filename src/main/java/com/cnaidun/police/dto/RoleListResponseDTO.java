package com.cnaidun.police.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author dongyin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleListResponseDTO {
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
     * 角色创建时间
     */
    private String createTime;
}
