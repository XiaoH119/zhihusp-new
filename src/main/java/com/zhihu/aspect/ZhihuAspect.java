package com.zhihu.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

//import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

@Aspect
@Component
public class ZhihuAspect {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private Gson gson = new Gson();

	/**
	 * 
	 * -验证权限
	 */
	@Pointcut("execution(public * com.neusoft.controller..*.*(..))")
	public void checkPermit() {
	}

	@Before("checkPermit()")
	public void doBefore(JoinPoint joinPoint) {
		logger.info("------------DrugPlatform.checkPermit info begin------------");
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		attributes.getRequest();
//		attributes.getRequest();
		Gson gson = new Gson();
		// 记录下请求内容
//		logger.info("URL : " + request.getRequestURL().toString());
//		logger.info("HTTP_METHOD : " + request.getMethod());
		logger.info("REQUEST：" + gson.toJson(joinPoint.getArgs()[0]));
		// 验证
//		RootRequest rrequest = (RootRequest) joinPoint.getArgs()[0];
		logger.info("------------request info end--------------");
	}

	// 统一处理返回值
	@AfterReturning(returning = "object", pointcut = "checkPermit()")
	public void doAfterReturning(JoinPoint joinPoint, Object object) {
		logger.info("------------response info begin-----------");
		logger.info("RESPONSE={}", gson.toJson(object));
		logger.info("------------response info end-------------");
	}
}
