package com.zhihu.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.zhihu.common.bean.Integral;
import com.zhihu.common.bean.LoginBean;
import com.zhihu.common.bean.UserInfo;

@Mapper
public interface LoginMapper {
	int checkUser(LoginBean lb);

	void regUser(LoginBean lb);
	
	void modifyPassword(LoginBean lb);

	LoginBean findUserById(String userid);

	LoginBean getUser(LoginBean lb);

	/**
	 * 查询用户信息
	 * 
	 * @param userInfo
	 * @return
	 */
	UserInfo getUserInfo(UserInfo userInfo);

	/**
	 * 查询用户扩展信息
	 * 
	 * @param userInfo
	 * @return
	 */
	UserInfo getUserInfoExt(UserInfo userInfo);

	/**
	 * 增加用户扩展信息
	 * 
	 * @param userInfo
	 */
	void insertUserInfoExt(UserInfo userInfo);

	/**
	 * 更新用户扩展信息
	 * 
	 * @param userInfo
	 */
	void updateUserInfoExt(UserInfo userInfo);

	/**
	 * 更新用户基本信息
	 * 
	 * @param userInfo
	 */
	void updateUserInfo(UserInfo userInfo);
	
	/**
	 * 更新积分
	 * @param integral
	 */
	void updateIntegral(Integral integral);
	
	/**
	 * 积分兑换现金
	 * @param integral
	 */
	void insertIntegralLog(Integral integral);
	
	/**
	 * 查询平台ID是否已经存在
	 * @param ptid
	 * @return
	 */
	String selectUserInfoExtByPTID(String ptid);
	
	/**
	 * 增加到接单用户表
	 * @param userinfo
	 */
	void insertTakeUser(UserInfo userinfo);
	
	/**
	 * 删除接单用户表
	 * @param userinfo
	 */
	void deleteTakeUser(UserInfo userinfo);
	
	/**
	 * 查询接单用户是否存在
	 * @param userInfo
	 * @return
	 */
	int selectTakeUser(UserInfo userInfo);
	
	/**
	 * 查询是否已存在放单权限
	 * @param userid
	 * @return
	 */
	int isExistExtUser(String userid);
	
	/**
	 * 增加放单权限
	 * @param userid
	 */
	void insertExtUser(String userid);
	
	/**
	 * 删除放单权限
	 * @param userid
	 */
	void deleteExtUser(String userid);
	
	/**
	 * 查询是否已存在接反对单权限
	 * @param userid
	 * @return
	 */
	int isExistOpposeUser(String userid);
	
	/**
	 * 增加接反对单权限
	 * @param userid
	 */
	void insertOpposeUser(String userid);
	
	/**
	 * 删除接反对单权限
	 * @param userid
	 */
	void deleteOpposeUser(String userid);
	
}
