package com.zhihu.common.bean;

public class SendSMS {
//	{"returnstatus":"success","code":"0","taskID":"1584672766942fd47"}
	private String returnstatus;
	private String code;
	private String taskID;

	public String getReturnstatus() {
		return returnstatus;
	}

	public void setReturnstatus(String returnstatus) {
		this.returnstatus = returnstatus;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTaskID() {
		return taskID;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}

}
