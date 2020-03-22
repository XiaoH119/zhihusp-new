package com.zhihu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhihu.common.bean.Integral;
import com.zhihu.common.bean.LoginBean;
import com.zhihu.common.bean.UserInfo;
import com.zhihu.global.bean.Response;
import com.zhihu.global.exception.AppException;
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
				throw new AppException("-1", "未查询到登录用户");
			} else if (i == 1) {
				res.setResultRight("登录成功");
			} else {
				throw new AppException("-1", "登录异常,查询到多个用户");
			}
			LoginBean ll = loginMapper.getUser(lb);
			res.setData(ll);
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
				res.setResultError("该用户名已存在，请更换新用户名！");
				return res;
			} else {
				loginMapper.regUser(lb);
			}
			res.setResultRight("注册成功！");
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
		res.setResultRight("密码修改成功");
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
	 * 
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
			res.setData(userInfo);
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
			res.setResultRight("信息更新完成");
		} catch (Exception e) {
			throw new Exception(e);
		}
		return res;
	}

	/**
	 * 更新用户信息
	 * 
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public Response updateUserInfo(UserInfo userInfo) throws Exception {
		Response res = new Response();
		try {
			loginMapper.updateUserInfo(userInfo);
		} catch (Exception e) {
			throw new Exception(e);
		}
		res.setResultRight("更新完成");
		return res;
	}

	/**
	 * 获取用户扩展信息
	 * 
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public Response getUserInfoExt(UserInfo userInfo) throws Exception {
		Response res = new Response();
		try {
			UserInfo userext = loginMapper.getUserInfoExt(userInfo);
			res.setData(userext);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return res;
	}

	public LoginBean findUserById(String userid) throws Exception {
		return loginMapper.findUserById(userid);
	}

	/**
	 * 积分兑换现金
	 * 
	 * @param integral
	 * @return
	 * @throws Exception
	 */
	public Response exchange(Integral integral) throws Exception {
		Response res = new Response();
		try {
			// 添加兑换记录，更新积分信息
			loginMapper.insertIntegralLog(integral);
//			String s = null;
//			if (s.equals("1")) {
//				s = "";
//			}
//			UserInfo userInfo = new UserInfo();
//			userInfo.setUserid(Integer.parseInt(integral.getUserid()));
			integral.setIntegral(integral.getIntegral() * -1);
			loginMapper.updateIntegral(integral);
		} catch (Exception e) {
			throw new Exception(e);
		}
		res.setResultRight("更新完成");
		return res;
	}
}
