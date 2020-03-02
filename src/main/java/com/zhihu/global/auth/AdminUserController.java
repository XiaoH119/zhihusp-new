package com.zhihu.global.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zhihu.common.bean.UserInfo;
import com.zhihu.service.LoginService;

@RestController
@RequestMapping(value = "/admin")
public class AdminUserController extends BaseController {
	@Autowired
	private LoginService loginService;

	/**
	 * 查询管理用户名
	 * 
	 * @param name
	 * @return
	 */
	
	@RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
	public Message adminLogin(HttpServletResponse response, String userName, String password) {
		// 此次简单处理，直接用登录名查找对象，不验证密码
		UserInfo user = loginService.getUserInfo(userName, password);
		if (user == null) {
			return new Message("-1","未查询到用户信息");
		}

		String token = JwtTokenUtil.createToken(user.getUserid(), user.getUsername(), false);
		// 放到响应头部
		response.setHeader(JwtTokenUtil.TOKEN_HEADER, JwtTokenUtil.TOKEN_PREFIX + token);
		return new Message("0", "登录成功");
	}

//	/**
//	 * 退出
//	 * 
//	 * @param name
//	 * @return
//	 */
//	@RequestMapping(value = "/adminLoginOut", method = RequestMethod.POST)
//	public Message adminLoginOut(HttpServletRequest request) {
//
//		String userId = getUserId(request);
//		adminUserServiceImpl.removeAdminUserLoginInfo(userId);
//		return new Message(SystemCodeAndMsg.SUCCESS.getCode(), SystemCodeAndMsg.SUCCESS.getMsg());
//	}
}
