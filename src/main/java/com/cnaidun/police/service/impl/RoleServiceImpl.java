/**
 * JFramework Generated
 */
package com.cnaidun.police.service.impl;


import com.cnaidun.police.config.mybatis.BeanUtil;
import com.cnaidun.police.config.mybatis.PageRequest;
import com.cnaidun.police.config.mybatis.PagedResult;
import com.cnaidun.police.config.tip.ErrorTip;
import com.cnaidun.police.config.tip.SuccessTip;
import com.cnaidun.police.config.tip.Tip;
import com.cnaidun.police.dto.AddRoleRequestDTO;
import com.cnaidun.police.dto.RoleListResponseDTO;
import com.cnaidun.police.entity.RolePermission;
import com.cnaidun.police.entity.UserRole;
import com.cnaidun.police.mapper.PermissionMapper;
import com.cnaidun.police.mapper.RoleMapper;
import com.cnaidun.police.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.stream.Collectors;


/**
 *
 * @author dongyin
 * @version 2019-05-29
 */
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
@Slf4j
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private PermissionMapper permissionMapper;

	@Override
	public PagedResult<RoleListResponseDTO> allRolesList(int pageNo, int pageSize) {
		PageRequest pageRequest = new PageRequest(pageNo,pageSize,true);
		List<RoleListResponseDTO> roleListResponseDTOS = roleMapper.allRolesList(pageRequest);
		return BeanUtil.toPagedResult(roleListResponseDTOS,pageRequest);
	}

	@Override
	public Tip queryUsersByRole(Integer roleId) {
		return new SuccessTip(roleMapper.queryUsersByRole(roleId));
	}

	@Transactional
	@Override
	public Tip bindingUserByRole(Integer roleId, List<Integer> userIds) {
		try {
			bindUserRole(roleId, userIds);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new ErrorTip("操作失败");
		}
		return new SuccessTip();
	}

	@Transactional
	@Override
	public Tip addRole(AddRoleRequestDTO addRoleRequestDTO, List<Integer> permissionIds) {
		try {
			if (null != addRoleRequestDTO.getId()) {
				roleMapper.updateRole(addRoleRequestDTO);
			} else {
				roleMapper.addRole(addRoleRequestDTO);
			}
			if (CollectionUtils.isNotEmpty(permissionIds)) {
				permissionMapper.deleteByRoleId(addRoleRequestDTO.getId());
				Integer roleId = addRoleRequestDTO.getId();
				List<RolePermission> rolePermissions = permissionIds.stream()
						.map(permissionId -> RolePermission.builder().roleId(roleId).permissionId(permissionId).build()).collect(Collectors.toList());
				permissionMapper.insertRolePermission(rolePermissions);
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new ErrorTip("操作失败");
		}
		return new SuccessTip();
	}

	@Transactional
	@Override
	public Tip deleteRole(Integer roleId) {
		roleMapper.deleteRole(roleId);
		roleMapper.deleteByRoleId(roleId);
		permissionMapper.deleteByRoleId(roleId);
		return new SuccessTip();
	}

	private void bindUserRole(Integer roleId, List<Integer> userIds) {
		roleMapper.deleteByRoleId(roleId);
		if (CollectionUtils.isNotEmpty(userIds)) {
            List<UserRole> collect = userIds.stream()
                    .map(userId -> UserRole.builder().roleId(roleId).userId(userId).build()).collect(Collectors.toList());
            roleMapper.insertUserRole(collect);
        }
	}
}