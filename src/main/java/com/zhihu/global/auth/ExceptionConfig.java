package com.zhihu.global.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常拦截
 */
//@RestControllerAdvice
public class ExceptionConfig {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionConfig.class);
	 
	@ExceptionHandler(value = Exception.class)
	public Message handleException(Exception e) {
		
//		自定义异常
		if (e instanceof UserLoginException) {
			UserLoginException userLoginException = (UserLoginException) e;
            return new Message(userLoginException.getErrorCode(), userLoginException.getMessage());
        }else {
            logger.error("【系统异常】{}", e);
            return new Message("204", "系统异常!");
        }
	}
}
