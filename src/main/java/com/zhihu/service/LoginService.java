package com.zhihu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhihu.common.bean.LoginBean;
import com.zhihu.common.bean.UserInfo;
import com.zhihu.global.bean.Response;
import com.zhihu.mapper.LoginMapper;

@Service
public class LoginService {
	@Autowired
	private LoginMapper loginMapper;

	public Response login(LoginBean lb) throws Exception {
		try {
			int i = loginMapper.checkUser(lb);
			Response res = new Response();
			if (i == 0) {
				res.setReturncode("0");
				res.setErrormessage("未查询到登录用户");
			} else if (i == 1) {
				res.setReturncode("1");
				res.setErrormessage("登录成功");
			} else {
				res.setReturncode("0");
				res.setErrormessage("登录异常,查询到多个用户");
			}
			return res;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * 注册
	 * 
	 * @param lb
	 * @return
	 */
	public Response reg(LoginBean lb) throws Exception {
		try {
			int i = loginMapper.checkUser(lb);
			Response res = new Response();
			if (i > 0) {
				res.setReturncode("0");
				res.setErrormessage("该用户名已存在，请更换新用户名！");
				return res;
			} else {
				loginMapper.regUser(lb);
			}
			res.setReturncode("1");
			res.setErrormessage("注册成功！");
			return res;
		} catch (Exception e) {
			throw new Exception(e);
		}

	}

	/**
	 * 修改密码
	 * 
	 * @param lb
	 * @return
	 */
	public Response modifyPassword(LoginBean lb) throws Exception {
		Response res = new Response();
		try {
			loginMapper.modifyPassword(lb);
		} catch (Exception e) {
			throw new Exception(e);
		}
		res.setReturncode("1");
		res.setErrormessage("密码修改成功");
		return res;
	}

	/**
	 * 查询用户信息
	 * 
	 * @param userInfo
	 * @return
	 */
	public UserInfo getUserInfo(String username, String password) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUsername(username);
		userInfo.setPassword(password);
		return loginMapper.getUserInfo(userInfo);
	}

	/**
	 * 查询用户信息
	 * @param userinfo
	 * @return
	 * @throws Exception
	 */
	public Response getUserInfo(UserInfo userinfo) throws Exception {
		Response res = new Response();
		try {
			UserInfo userInfo = loginMapper.getUserInfo(userinfo);
			if (userInfo == null) {
				res.setResultError("未查询到用户信息！");
				return res;
			}
			res.setUserinfo(userInfo);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return res;
	}

	public Response updateUserInfoExt(UserInfo userInfo) throws Exception {
		Response res = new Response();
		try {

			UserInfo userext = loginMapper.getUserInfoExt(userInfo);
			if (userext == null) {
				loginMapper.insertUserInfoExt(userInfo);
			} else {
				loginMapper.updateUserInfoExt(userInfo);
			}
			res.setErrormessage("信息更新完成");
		} catch (Exception e) {
			throw new Exception(e);
		}
		return res;
	}
	
	/**
	 * 更新用户信息
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public Response updateUserInfo(UserInfo userInfo) throws Exception{
		Response res = new Response();
		try {
			loginMapper.updateUserInfo(userInfo);
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		res.setErrormessage("更新完成");
		return res;
	}
	
	/**
	 * 获取用户扩展信息
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public Response getUserInfoExt(UserInfo userInfo) throws Exception{
		Response res = new Response();
		try {
			UserInfo userext = loginMapper.getUserInfoExt(userInfo);
			res.setUserinfo(userext);
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		return res;
	}
}