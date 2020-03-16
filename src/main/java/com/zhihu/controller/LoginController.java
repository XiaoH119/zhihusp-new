package com.zhihu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zhihu.annotation.UserLoginToken;
import com.zhihu.common.bean.LoginBean;
import com.zhihu.common.bean.UserInfo;
import com.zhihu.global.bean.Response;
import com.zhihu.service.LoginService;
import com.zhihu.service.TokenService;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
//@RequestMapping("/user")
public class LoginController {
	@Autowired
	private LoginService loginService;
	@Autowired
	private TokenService tokenService;

	static Logger logger = LoggerFactory.getLogger(LoginController.class);

	/**
	 * 登录
	 * 
	 * @param lb
	 * @return
	 */
	@PostMapping("/login")
	public Response login(@RequestBody LoginBean lb) {
		Response res = new Response();
		try {
			res = loginService.login(lb);
			String token = tokenService.getToken((LoginBean) res.getData());
			res.setToken(token);
			res.setData(lb);
		} catch (Exception e) {
			logger.error("reg", e);
			res.setResultError("系统异常，请联系管理员！");
			return res;
		}
		return res;
	}

	/**
	 * 注册
	 * 
	 * @param lb
	 * @return
	 */
	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public Response reg(@RequestBody LoginBean lb) {
		Response res = new Response();
		try {
			res = loginService.reg(lb);
		} catch (Exception e) {
			logger.error("reg", e);
			res.setResultError("系统异常，请联系管理员！");
			return res;
		}
		return res;
	}

	/**
	 * 修改密码
	 * 
	 * @param lb
	 * @return
	 */
	@UserLoginToken
	@RequestMapping(value = "/modifypassword", method = RequestMethod.POST)
	public Response modifyPassword(@RequestBody LoginBean lb) {
		Response res = new Response();
		try {
			res = loginService.modifyPassword(lb);
		} catch (Exception e) {
			logger.error("modifyPassword", e);
			res.setResultError("系统异常，请联系管理员！");
			return res;
		}
		return res;
	}

	@RequestMapping(value = "/updateuserinfoext", method = RequestMethod.POST)
	public Response updateUserInfoExt(@RequestBody UserInfo userInfo) {
		Response res = new Response();
		try {
			res = loginService.updateUserInfoExt(userInfo);
		} catch (Exception e) {
			logger.error("updateUserInfoExt", e);
			res.setResultError("系统异常，请联系管理员！");
			return res;
		}

		return res;
	}

	@RequestMapping(value = "/getuserinfo", method = RequestMethod.GET)
	public Response getUserInfo(@RequestBody UserInfo userinfo) {
		Response res = new Response();
		try {
			res = loginService.getUserInfo(userinfo);
		} catch (Exception e) {
			res.setResultError("操作异常，请联系客服或稍后再试");
		}
		return res;
	}

	/**
	 * 更新人员基本信息
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/updateuserinfo", method = RequestMethod.POST)
	public Response updateUserInfo(@RequestBody UserInfo userInfo) {
		Response res = new Response();
		try {
			res = loginService.updateUserInfo(userInfo);
		} catch (Exception e) {
			logger.error("updateUserInfo", e);
			res.setResultError("更新出现异常，请稍后再试");
			return res;
		}
		return res;
	}

	/**
	 * 查询用户扩展信息
	 * 
	 * @param userInfo
	 * @return
	 */
	@RequestMapping(value = "/getuserinfoext", method = RequestMethod.GET)
	public Response getUserInfoExt(@RequestBody UserInfo userInfo) {
		Response res = new Response();
		try {
			res = loginService.getUserInfoExt(userInfo);
		} catch (Exception e) {
			logger.error("getuserinfoext", e);
			res.setResultError("查询用户信息异常，请稍后再试");
			return res;
		}
		return res;
	}

}
