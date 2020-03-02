package com.zhihu.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.zhihu.common.bean.LoginBean;
import com.zhihu.common.bean.UserInfo;

@Mapper
public interface LoginMapper {
	int checkUser(LoginBean lb);
	
	void regUser(LoginBean lb);
	
	void modifyPassword(LoginBean lb);
	
	/**
	 * 查询用户信息
	 * @param userInfo
	 * @return
	 */
	UserInfo getUserInfo(UserInfo userInfo);
	
	/**
	 * 查询用户扩展信息
	 * @param userInfo
	 * @return
	 */
	UserInfo getUserInfoExt(UserInfo userInfo);
	
	/**
	 * 增加用户扩展信息
	 * @param userInfo
	 */
	void insertUserInfoExt(UserInfo userInfo);
	
	/**
	 * 更新用户扩展信息
	 * @param userInfo
	 */
	void updateUserInfoExt(UserInfo userInfo);
	
	/**
	 * 更新用户基本信息
	 * @param userInfo
	 */
	void updateUserInfo(UserInfo userInfo);
}
