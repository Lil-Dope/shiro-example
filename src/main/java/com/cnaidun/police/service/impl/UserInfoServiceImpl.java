/**
 * JFramework Generated
 */
package com.cnaidun.police.service.impl;


import com.cnaidun.police.config.mybatis.BeanUtil;
import com.cnaidun.police.config.mybatis.PageRequest;
import com.cnaidun.police.config.mybatis.PagedResult;
import com.cnaidun.police.config.shiro.UsernamePasswordToken;
import com.cnaidun.police.config.tip.ErrorTip;
import com.cnaidun.police.config.tip.SuccessTip;
import com.cnaidun.police.config.tip.Tip;
import com.cnaidun.police.constant.Constant;
import com.cnaidun.police.dto.MenuResponseDTO;
import com.cnaidun.police.dto.UserInfoRequestDTO;
import com.cnaidun.police.entity.UserInfo;
import com.cnaidun.police.mapper.UserInfoMapper;
import com.cnaidun.police.service.UserInfoService;
import com.cnaidun.police.util.RandomUtil;
import com.cnaidun.police.util.wechat.WechatUtil;
import com.cnaidun.police.util.wechat.bean.WechatUserAuthInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;


/**
 *
 * @author dongyin
 * @version 2019-05-23
 */
@Service
@Transactional(rollbackFor = Exception.class,readOnly = true)
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoMapper userInfoMapper;


	@Override
	public UserInfo findByAccount(String account) {
		return userInfoMapper.findByAccount(account);
	}

	/**
	 * 补全用户密码以及salt
	 * @param userInfo
	 * @return
	 */
	@Override
	public UserInfo completeUserPassword(UserInfo userInfo) {
		String hashAlgorithmName = Constant.MD5;
		String credentials = userInfo.getPassword();
		int hashIterations = Constant.HASHITERATIONS;
		String randomSixNum = RandomUtil.getRandomSixNum();
		ByteSource credentialsSalt = ByteSource.Util.bytes(randomSixNum);
		Object obj = new SimpleHash(hashAlgorithmName, credentials, credentialsSalt, hashIterations);
		userInfo.setPassword(obj.toString());
		userInfo.setSalt(randomSixNum);
		return userInfo;
	}

	@Override
	public Map completeUserPassword(Integer id,String password) {
		String hashAlgorithmName = Constant.MD5;
		String credentials = password;
		int hashIterations = Constant.HASHITERATIONS;
		//获取库中原有salt
		String salt = userInfoMapper.getSalt(id);
		ByteSource credentialsSalt = ByteSource.Util.bytes(salt);
		Object obj = new SimpleHash(hashAlgorithmName, credentials, credentialsSalt, hashIterations);
		Map map = new HashMap(){{
			put("password",obj.toString());
			put("salt",salt);
		}};
		return map;
	}

	@Override
	public List<MenuResponseDTO> findUserPermissions(String account) {
		List<MenuResponseDTO> userPermissions = userInfoMapper.findUserPermissions(account);
		return packageInfo(userPermissions);
	}

	public List<MenuResponseDTO> packageInfo(List<MenuResponseDTO> userPermissions) {
		return userPermissions.stream()
				.filter(parent -> parent.getParentId() == 0)
				.map(parent -> MenuResponseDTO.builder()
						.id(parent.getId())
						.parentId(parent.getParentId())
						.label(parent.getLabel())
						.url(parent.getUrl())
						.type(parent.getType())
						.icon(parent.getIcon())
						.children(
								userPermissions.stream()
										.filter(child -> child.getParentId().equals(parent.getId()))
										.map(child -> MenuResponseDTO.builder()
												.id(child.getId())
												.parentId(parent.getId())
												.label(child.getLabel())
												.url(child.getUrl())
												.type(child.getType())
												.icon(child.getIcon())
												.children(
														userPermissions.stream()
																.filter(button -> Constant.BUTTON.equals(button.getType()) && button.getParentId().equals(child.getId()))
																.map(button -> MenuResponseDTO.builder()
																		.id(button.getId())
																		.parentId(child.getId())
																		.label(button.getLabel())
																		.url(button.getUrl())
																		.type(button.getType())
																		.type(button.getIcon())
																		.buttonType(button.getButtonType()).build()).collect(Collectors.toList())
												)
												.build())
										.collect(Collectors.toList())
						).build()).collect(Collectors.toList());
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public Tip postUserInfo(UserInfo userInfo) {
		UserInfo byAccount = userInfoMapper.findByAccount(userInfo.getAccount());
		if (byAccount != null) {
			return new ErrorTip("用户名已存在");
		}
		Integer id;
		if (userInfo.getId() !=null){
            //更新用户信息
            userInfoMapper.updateUserInfo(completeUserPassword(userInfo));
			id = userInfo.getId();
		}else {
            //新增用户信息
			userInfoMapper.addUserInfo(completeUserPassword(userInfo));
			id = userInfo.getId();
		}
		return new SuccessTip("成功",id);
	}

	@Override
    @Transactional(rollbackFor = Exception.class)
	public Integer dropUserInfo(@RequestParam Integer id) {
		return userInfoMapper.dropUserInfo(id);
	}

	@Override
	public List<Map<String, Object>> queryAll() {
		return userInfoMapper.queryAll();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer updatePWD(Integer id, String oldPWD, String newPWD) {
		Map m = completeUserPassword(id, oldPWD);
		if (userInfoMapper.isPasswordRight(m)) {
			return userInfoMapper.updatePWD(completeUserPassword(UserInfo.builder()
					.id(id)
					.password(newPWD)
					.build()));
		} else {
			return -1;
		}
	}

	@Override
	public PagedResult<UserInfoRequestDTO> allUserList(int pageNo, int pageSize) {
		PageRequest pageRequest = new PageRequest(pageNo,pageSize,true);
		List<UserInfoRequestDTO> userInfoRequestDTOS = userInfoMapper.allUserList(pageRequest);
		return BeanUtil.toPagedResult(userInfoRequestDTOS,pageRequest);
	}

	@Override
	public Tip scanCodeLogin(String code) {
		WechatUserAuthInfo userAuthInfo = WechatUtil.getAccessToken(code);
		if (userAuthInfo == null || StringUtils.isBlank(userAuthInfo.getOpenid())) {
			return new ErrorTip("扫码登录失败");
		}
		String openid = userAuthInfo.getOpenid();
		String account = userInfoMapper.findAccountByOpenId(openid);
		if (StringUtils.isBlank(account)) {
			//未绑定账号
			return new ErrorTip(201, "请先绑定账号", openid);
		}
		//已绑定账号直接登录
		UsernamePasswordToken token = new UsernamePasswordToken(account);
		return loginReturnData(token, null);
	}

	@Override
	public Tip login(String account, String password, String openId) {
		UsernamePasswordToken token = new UsernamePasswordToken(account, password);
		try {
			return loginReturnData(token, openId);
		} catch (AuthenticationException e) {
			String exception = e.getClass().getName();
			String msg;
			if (UnknownAccountException.class.getName().equals(exception)) {
				System.out.println("UnknownAccountException -- > 账号不存在：");
				msg = "账号不存在";
			} else if (IncorrectCredentialsException.class.getName().equals(exception)) {
				System.out.println("IncorrectCredentialsException -- > 密码不正确：");
				msg = "密码不正确";
			} else {
				msg = "登录失败";
				System.out.println("else -- >" + exception);
			}
			return new ErrorTip(msg);
		}
	}

	private Tip loginReturnData(UsernamePasswordToken token, String openId) {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.login(token);
		Session session = SecurityUtils.getSubject().getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute(Constant.SESSION_USER_INFO);
		List<MenuResponseDTO> userPermissions = findUserPermissions(userInfo.getAccount());
		Map map = new HashMap(3);
		map.put("userPermissions", userPermissions);
		map.put("JSESSIONID", session.getId().toString());
		map.put(Constant.SESSION_USER_INFO, userInfo);

		if (StringUtils.isNotBlank(openId)) {
			//如果openid不为null，则是用户扫码后，绑定账号
			Map pa = new HashMap(2);
			pa.put("openId", openId);
			pa.put("account", userInfo.getAccount());
			userInfoMapper.updateOpenIdByAccount(pa);
		}
		return new SuccessTip(map);
	}
}