package com.zhihu.global.bean;

import java.util.List;

import com.zhihu.common.bean.Coder;
import com.zhihu.common.bean.Order;
import com.zhihu.common.bean.UserInfo;

public class Response {
	private String code;// 返回代码(0正确完成,-1错误),
	private String message;// 错误信息
	
	private T data;
	
	private UserInfo userinfo;
	
	private Order order;
	
	private List<Coder> codelist;
	
	private List<Order> orderlist;
	
	public Response() {
		this.code = "0";
	}

	public void setResultError(String errormsg) {
		this.code = "-1";
		this.message = errormsg;
	}
	public String getReturncode() {
		return code;
	}

	public void setReturncode(String code) {
		this.code = code;
	}

	public String getErrormessage() {
		return errormessage;
	}

	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}

	public List<Coder> getCodelist() {
		return codelist;
	}

	public void setCodelist(List<Coder> codelist) {
		this.codelist = codelist;
	}

	public List<Order> getOrderlist() {
		return orderlist;
	}

	public void setOrderlist(List<Order> orderlist) {
		this.orderlist = orderlist;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public UserInfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}

}
