/**
 * JFramework Generated
 */
package com.cnaidun.police.service.impl;


import com.cnaidun.police.config.mybatis.BeanUtil;
import com.cnaidun.police.config.mybatis.PageRequest;
import com.cnaidun.police.config.mybatis.PagedResult;
import com.cnaidun.police.dto.AddPermissionRequestDTO;
import com.cnaidun.police.dto.FindPermissionResponseDTO;
import com.cnaidun.police.dto.MenuResponseDTO;
import com.cnaidun.police.entity.RolePermission;
import com.cnaidun.police.mapper.PermissionMapper;
import com.cnaidun.police.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;


/**
 *
 * @author dongyin
 * @version 2019-05-29
 */
@Service
@Transactional(rollbackFor = Exception.class,readOnly = true)
@Slf4j
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionMapper permissionMapper;
	@Autowired
	private UserInfoServiceImpl userInfoServiceImpl;

	@Override
	public List<MenuResponseDTO> findAllPermissions() {
		List<MenuResponseDTO> allPermissions = permissionMapper.findAllPermissions();
		return userInfoServiceImpl.packageInfo(allPermissions);
	}

	@Override
	public List<Integer> findSelectedPermissionId(Integer roleId) {
		return permissionMapper.findSelectedPermissionId(roleId);
	}

	/**
	 * @Author gzw
	 * @Description 根据菜单名分页获取菜单分页
	 * @Date 2019/7/2 16:21
	 * @Param [pageNo 页数, pageSize 页大小, menuName 菜单名,允许后模糊查询]
	 * @return com.cnaidun.common.util.mybatisplus.PagedResult<com.cnaidun.police.dto.FindPermissionResponseDTO>
	 **/
	@Override
	public PagedResult<FindPermissionResponseDTO> findPermissionByMenuName(Integer pageNo, Integer pageSize, String menuName) {
		PageRequest pageRequest = new PageRequest(pageNo,pageSize,true);
		List<FindPermissionResponseDTO> respList = permissionMapper.findPermissionByMenuName(pageRequest,menuName);
		PagedResult<FindPermissionResponseDTO> findPermissionResponseDTOPagedResult = BeanUtil.toPagedResult(respList, pageRequest);
		return findPermissionResponseDTOPagedResult;
	}

	/**
	 * @Author gzw
	 * @Description 添加菜单
	 * @Date 2019/7/2 17:54
	 * @Param [addPermissionRequestDTO]
	 * @return boolean
	 **/
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean addOrUpdatePermission(AddPermissionRequestDTO addPermissionRequestDTO) {
		//如果id为空则是添加操作
		if(addPermissionRequestDTO.getId()==null){
			log.info("addPermission，id为空，执行新增操作");
			//新增菜单
			return addPermissionDetail(addPermissionRequestDTO);
		}
		//如果id不为空
		if(addPermissionRequestDTO.getId()!=null){
			//查找数据库中是否有id所对应的菜单
			Integer id = permissionMapper.findPermissionIdById(addPermissionRequestDTO.getId());
			//如果菜单存在，执行修改;否则新增
			if(id!=null){
				log.info("addPermission,id:{}，执行修改操作",id);
				//修改菜单
				int effectLine = permissionMapper.modifyPermission(addPermissionRequestDTO);
				if(effectLine>0){
					return true;
				}else{
					return false;
				}
			}else{
				log.info("addPermission,id:{},数据库中不存在，执行新增操作",id);
				//新增菜单
				return addPermissionDetail(addPermissionRequestDTO);
			}
		}
		return false;
	}

	/**
	 * @Author gzw
	 * @Description 新增菜单详细步骤
	 * @Date 2019/7/3 9:27
	 * @Param [addPermissionRequestDTO]
	 * @return boolean
	 **/
	@Transactional(rollbackFor = Exception.class)
	public boolean addPermissionDetail(AddPermissionRequestDTO addPermissionRequestDTO) {
		//1.菜单表中添加一条菜单，获取菜单id
		int effectLine = permissionMapper.addPermission(addPermissionRequestDTO);
		//2.在角色菜单关系表中，为roleId为1的添加一条记录
		int effectLine2 = permissionMapper.addRolePermission(RolePermission.builder().roleId(1).permissionId(addPermissionRequestDTO.getId()).build());
		//如果有一个插入失败，则回滚，返回插入失败
		if(effectLine==0||effectLine2==0){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	/**
	 * @Author gzw
	 * @Description 删除菜单
	 * @Date 2019/7/3 10:56
	 * @Param [id]
	 * @return boolean
	 **/
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean deletePermission(Integer id){
		int effectLine = permissionMapper.deletePermission(id);
		if(effectLine>0){
			return true;
		}else{
			return false;
		}
	}
}