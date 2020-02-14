package com.cnaidun.police.service;


import com.cnaidun.police.config.mybatis.PagedResult;
import com.cnaidun.police.config.tip.Tip;
import com.cnaidun.police.dto.AddRoleRequestDTO;
import com.cnaidun.police.dto.RoleListResponseDTO;
import io.swagger.models.auth.In;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

/**
 * 角色服务层
 * @author dongyin
 * @version 2019-05-29
 */
public interface RoleService {

    PagedResult<RoleListResponseDTO> allRolesList(int pageNo, int pageSize);

    Tip queryUsersByRole(Integer roleId);

    Tip bindingUserByRole(Integer roleId, List<Integer> userIds);

    Tip addRole(AddRoleRequestDTO addRoleRequestDTO, List<Integer> userIds);

    Tip deleteRole(Integer roleId);
}