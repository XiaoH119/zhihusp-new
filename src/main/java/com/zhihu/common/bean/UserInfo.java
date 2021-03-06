package com.zhihu.common.bean;

import java.math.BigDecimal;

public class UserInfo {

	/**
	 * 用户名称
	 */
	private String username;
	
	private String hangyename;
	
	private String password;
	
	private String alipay;
	
	private String factname;
	
	private BigDecimal integral;
	
	/**
	 * 用户id
	 */
	private int userid;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 微信号
	 */
	private String wechat;
	/**
	 * 平台类别
	 */
	private String pttype;
	/**
	 * 主页
	 */
	private String zhuye;
	/**
	 * 平台id
	 */
	private String ptid;
	/**
	 * 行业代码
	 */
	private String hangye;
	/**
	 * 粉丝数
	 */
	private int fensi;
	/**
	 * 赞同数
	 */
	private String zantong;
	/**
	 * 盐值
	 */
	private String yanzhi;
	
	/**
	 * 是否管理员
	 */
	private String isadmin;
	
	/**
	 * 平台昵称
	 */
	private String ptname;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public String getPttype() {
		return pttype;
	}
	public void setPttype(String pttype) {
		this.pttype = pttype;
	}
	public String getZhuye() {
		return zhuye;
	}
	public void setZhuye(String zhuye) {
		this.zhuye = zhuye;
	}
	public String getPtid() {
		return ptid;
	}
	public void setPtid(String ptid) {
		this.ptid = ptid;
	}
	public String getHangye() {
		return hangye;
	}
	public void setHangye(String hangye) {
		this.hangye = hangye;
	}
	public int getFensi() {
		return fensi;
	}
	public void setFensi(int fensi) {
		this.fensi = fensi;
	}
	public String getZantong() {
		return zantong;
	}
	public void setZantong(String zantong) {
		this.zantong = zantong;
	}
	public String getYanzhi() {
		return yanzhi;
	}
	public void setYanzhi(String yanzhi) {
		this.yanzhi = yanzhi;
	}
	public String getPtname() {
		return ptname;
	}
	public void setPtname(String ptname) {
		this.ptname = ptname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public BigDecimal getIntegral() {
		return integral;
	}
	public void setIntegral(BigDecimal integral) {
		this.integral = integral;
	}
	public String getHangyename() {
		return hangyename;
	}
	public void setHangyename(String hangyename) {
		this.hangyename = hangyename;
	}
	public String getIsadmin() {
		return isadmin;
	}
	public void setIsadmin(String isadmin) {
		this.isadmin = isadmin;
	}
	
}
