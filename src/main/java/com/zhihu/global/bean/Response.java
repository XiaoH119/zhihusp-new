package com.zhihu.global.bean;

public class Response {
	private String code;// 返回代码(0正确完成,-1错误),
	private String message;// 错误信息
	private String token;// 错误信息

	private Object data;

	public Response() {
		this.code = "0";
		this.message = "正确完成";
	}

	public void setResultRight(String msg) {
		this.code = "0";
		this.message = msg;
	}

	public void setResultError(String errormsg) {
		this.setCode("-1");
		this.setMessage(errormsg);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
