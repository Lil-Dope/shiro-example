package com.cnaidun.police.service;


import com.cnaidun.police.config.mybatis.PagedResult;
import com.cnaidun.police.config.tip.Tip;
import com.cnaidun.police.dto.MenuResponseDTO;
import com.cnaidun.police.dto.UserInfoRequestDTO;
import com.cnaidun.police.entity.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * 用户信息服务层
 * @author dongyin
 * @version 2019-05-23
 */
public interface UserInfoService{

    UserInfo findByAccount(String account);

    UserInfo completeUserPassword(UserInfo userInfo);

    Map completeUserPassword(Integer id,String password);

    List<MenuResponseDTO> findUserPermissions(String account);

    Tip postUserInfo(UserInfo userInfo);

    Integer dropUserInfo(Integer id);

    List<Map<String,Object>> queryAll();

    Integer updatePWD(Integer id,String oldPWD,String newPWD);

    PagedResult<UserInfoRequestDTO> allUserList(int pageNo, int pageSize);

    Tip scanCodeLogin(String code);

    Tip login(String account, String password, String openId);
}