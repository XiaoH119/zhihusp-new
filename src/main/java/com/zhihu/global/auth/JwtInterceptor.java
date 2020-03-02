package com.zhihu.global.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class JwtInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
 
		// 取得token
		String tokenHeader = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
		if (tokenHeader == null || !tokenHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
			throw new UserLoginException("201","未登录");
		}
		tokenHeader = tokenHeader.replace(JwtTokenUtil.TOKEN_PREFIX, "");
		// 验证token是否有效--无效已做异常抛出，由全局异常处理后返回对应信息
		JwtTokenUtil.getTokenBody(tokenHeader);
		return true;
	}
 
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}
 
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}
