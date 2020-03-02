package com.zhihu.global.auth;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
	/**
	 * 根据token获取用户ID
	 * 
	 * @param request
	 * @return
	 */
	public String getUserId(HttpServletRequest request) {
 
		// 取得token
		String tokenHeader = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
		tokenHeader = tokenHeader.replace(JwtTokenUtil.TOKEN_PREFIX, "");
		return JwtTokenUtil.getObjectId(tokenHeader);
	}
}
