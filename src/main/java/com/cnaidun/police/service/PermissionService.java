package com.cnaidun.police.service;


import com.cnaidun.police.config.mybatis.PagedResult;
import com.cnaidun.police.dto.AddPermissionRequestDTO;
import com.cnaidun.police.dto.FindPermissionResponseDTO;
import com.cnaidun.police.dto.MenuResponseDTO;

import java.util.List;

/**
 * 权限菜单服务层
 * @author dongyin
 * @version 2019-05-29
 */
public interface PermissionService {
    List<MenuResponseDTO> findAllPermissions();

    List<Integer> findSelectedPermissionId(Integer roleId);

    /**
     * @Author gzw
     * @Description 根据菜单名分页获取菜单分页
     * @Date 2019/7/2 16:21
     * @Param [pageNo 页数, pageSize 页大小, menuName 菜单名,允许后模糊查询]
     * @return com.cnaidun.common.util.mybatisplus.PagedResult<com.cnaidun.police.dto.FindPermissionResponseDTO>
     **/
    PagedResult<FindPermissionResponseDTO> findPermissionByMenuName(Integer pageNo, Integer pageSize, String menuName);

    /**
     * @Author gzw
     * @Description 添加菜单
     * @Date 2019/7/2 17:54
     * @Param [addPermissionRequestDTO]
     * @return boolean
     **/
    boolean addOrUpdatePermission(AddPermissionRequestDTO addPermissionRequestDTO);

    /**
     * @Author gzw
     * @Description 删除菜单
     * @Date 2019/7/3 10:55
     * @Param [id]
     * @return boolean
     **/
    boolean deletePermission(Integer id);
}