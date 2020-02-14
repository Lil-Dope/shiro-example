package com.cnaidun.police.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cnaidun.police.config.mybatis.PageRequest;
import com.cnaidun.police.dto.AddRoleRequestDTO;
import com.cnaidun.police.dto.RoleListResponseDTO;
import com.cnaidun.police.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoleMapper extends BaseMapper {

    List<RoleListResponseDTO> allRolesList(PageRequest pageRequest);

    List<Map<String,Object>> queryUsersByRole(@Param("roleId") Integer roleId);

    void deleteByRoleId(Integer roleId);

    void insertUserRole(@Param("list")List<UserRole> list);

    void addRole(AddRoleRequestDTO addRoleRequestDTO);

    void updateRole(AddRoleRequestDTO addRoleRequestDTO);

    void deleteRole(@Param("id")Integer id);
}
