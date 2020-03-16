package com.zhihu.global.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.zhihu.global.bean.Response;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * Controller全局异常处理
 * 
 */
@ControllerAdvice
public class ExceptionHandle {

	private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);
	private Gson gson = new Gson();

	/**
	 * -处理所有不可知的异常
	 * 
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Response handle(Exception e) throws Exception {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		logger.error("发生ExceptionHandle.Exception--Exception" + sw.toString());
		pw.close();
		sw.close();
		Response response = new Response();
		response.setCode("403");
		response.setMessage(e.getMessage());
		logger.info("RESPONSE=", gson.toJson(response));
		return response;
	}

//
	/**
	 * 处理所有业务异常
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(AppException.class)
	@ResponseBody
	public Response handleBusinessException(AppException e) {
		logger.error("发生ExceptionHandle.handleBusinessException");
		Response response = new Response();
		response.setCode(e.getCode());
		response.setMessage(e.getMessage());
		logger.info("RESPONSE=", gson.toJson(response));
		return response;
	}

}
