package com.cnaidun.police.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


/**
 * @author dongyin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddRoleRequestDTO {
    private Integer id;
    /**
     * 角色标识程序中判断使用,如"admin",这个是唯一的:
     */
    @NotNull(message = "角色标识不能为空")
    private String role;
    /**
     * 角色描述,UI界面显示使用
     */
    private String description;
}
