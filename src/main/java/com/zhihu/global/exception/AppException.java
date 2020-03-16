package com.zhihu.global.exception;

/**
 * 
 * -异常处理
 *
 */
public class AppException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String code;

	private String msg;

	public AppException(String errorCode, Throwable t) {
		super(t);
		this.packErroInfo(errorCode, errorCode);
	}

	public AppException(String errorCode, String errorMsg) {
		super(errorMsg);
		this.packErroInfo(errorCode, errorCode);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	private void packErroInfo(String errorCode, String errorMsg) {
		this.code = errorCode;
		this.msg = errorMsg;
	}

}
