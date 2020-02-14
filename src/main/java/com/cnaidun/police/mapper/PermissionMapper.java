package com.cnaidun.police.mapper;

import com.cnaidun.police.config.mybatis.PageRequest;
import com.cnaidun.police.dto.AddPermissionRequestDTO;
import com.cnaidun.police.dto.FindPermissionResponseDTO;
import com.cnaidun.police.dto.MenuResponseDTO;
import com.cnaidun.police.entity.RolePermission;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper {
    List<MenuResponseDTO> findAllPermissions();

    List<Integer> findSelectedPermissionId(Integer roleId);

    void deleteByRoleId(Integer roleId);

    void insertRolePermission(@Param("list")List<RolePermission> list);

    /**
     * @Author gzw
     * @Description 根据菜单名分页获取菜单分页
     * @Date 2019/7/2 16:21
     * @Param [pageNo 页数, pageSize 页大小, menuName 菜单名,允许后模糊查询]
     * @return com.cnaidun.common.util.mybatisplus.PagedResult<com.cnaidun.police.dto.FindPermissionResponseDTO>
     **/
    List<FindPermissionResponseDTO> findPermissionByMenuName(PageRequest pageRequest, @Param("menuName") String menuName);

    /**
     * @Author gzw
     * @Description 根据id查找是否有本菜单
     * @Date 2019/7/3 9:20
     * @Param [id 菜单id]
     * @return java.lang.Integer
     **/
    Integer findPermissionIdById(Integer id);

    /**
     * @Author gzw
     * @Description 修改菜单
     * @Date 2019/7/3 9:34
     * @Param [addPermissionRequestDTO]
     * @return int
     **/
    int modifyPermission(AddPermissionRequestDTO permissionRequestDTO);

    /**
     * @Author gzw
     * @Description 新增菜单
     * @Date 2019/7/3 10:05
     * @Param [addPermissionRequestDTO]
     * @return int
     **/
    int addPermission(AddPermissionRequestDTO addPermissionRequestDTO);

    /**
     * @Author gzw
     * @Description 删除菜单
     * @Date 2019/7/3 10:58
     * @Param [id]
     * @return int
     **/
    int deletePermission(Integer id);

    int addRolePermission(RolePermission rolePermission);
}
