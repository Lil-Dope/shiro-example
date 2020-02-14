package com.cnaidun.police.mapper;


import com.cnaidun.police.config.mybatis.PageRequest;
import com.cnaidun.police.dto.MenuResponseDTO;
import com.cnaidun.police.dto.UserInfoRequestDTO;
import com.cnaidun.police.entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserInfoMapper {

    UserInfo findByAccount(String account);

    List<MenuResponseDTO> findUserPermissions(String account);

    Integer updateUserInfo(UserInfo userInfo);

    Integer addUserInfo(UserInfo userInfo);

    Integer dropUserInfo(@Param("id") Integer id);

    List<Map<String,Object>> queryAll();

    Integer updatePWD(UserInfo userInfo);

    String getSalt(@Param("id") Integer id);

    boolean isPasswordRight(Map<String,Object> map);

    List<UserInfoRequestDTO> allUserList(PageRequest pageRequest);

    String findAccountByOpenId(String openId);

    Integer updateOpenIdByAccount(Map map);
}
