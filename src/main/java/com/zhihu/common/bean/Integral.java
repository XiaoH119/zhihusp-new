package com.zhihu.common.bean;

import java.math.BigDecimal;

public class Integral {

	private String userid;
	private BigDecimal integral;
	private String integraltype;
	private String money;
	private String occurdate;
	private String alipay;
	private String factname;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public BigDecimal getIntegral() {
		return integral;
	}

	public void setIntegral(BigDecimal integral) {
		this.integral = integral;
	}

	public String getIntegraltype() {
		return integraltype;
	}

	public void setIntegraltype(String integraltype) {
		this.integraltype = integraltype;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getOccurdate() {
		return occurdate;
	}

	public void setOccurdate(String occurdate) {
		this.occurdate = occurdate;
	}

	public String getAlipay() {
		return alipay;
	}

	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}

	public String getFactname() {
		return factname;
	}

	public void setFactname(String factname) {
		this.factname = factname;
	}

}
