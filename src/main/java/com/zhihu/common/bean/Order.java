package com.zhihu.common.bean;

import java.math.BigDecimal;

public class Order {
	
	private int pagenum;
	
	private int pagesize;
	
	private String hangye;
	
	private String finishdate;
	private String takestate;
	private String orderid;
	private String orderurl;
	private String ordertitle;
	private String ordertype;
	private int fensi;
	private BigDecimal price;
	private int needcnt;
	private int hadcnt;
	private String occurdate;
	private int userid;
	private String orderstate;

	private String pttype;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getOrderurl() {
		return orderurl;
	}

	public void setOrderurl(String orderurl) {
		this.orderurl = orderurl;
	}

	public String getOrdertitle() {
		return ordertitle;
	}

	public void setOrdertitle(String ordertitle) {
		this.ordertitle = ordertitle;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public int getFensi() {
		return fensi;
	}

	public void setFensi(int fensi) {
		this.fensi = fensi;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getNeedcnt() {
		return needcnt;
	}

	public void setNeedcnt(int needcnt) {
		this.needcnt = needcnt;
	}

	public int getHadcnt() {
		return hadcnt;
	}

	public void setHadcnt(int hadcnt) {
		this.hadcnt = hadcnt;
	}

	public String getOccurdate() {
		return occurdate;
	}

	public void setOccurdate(String occurdate) {
		this.occurdate = occurdate;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getOrderstate() {
		return orderstate;
	}

	public void setOrderstate(String orderstate) {
		this.orderstate = orderstate;
	}

	public String getPttype() {
		return pttype;
	}

	public void setPttype(String pttype) {
		this.pttype = pttype;
	}

	public String getTakestate() {
		return takestate;
	}

	public void setTakestate(String takestate) {
		this.takestate = takestate;
	}

	public String getFinishdate() {
		return finishdate;
	}

	public void setFinishdate(String finishdate) {
		this.finishdate = finishdate;
	}

	public int getPagenum() {
		return pagenum;
	}

	public void setPagenum(int pagenum) {
		this.pagenum = pagenum;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public String getHangye() {
		return hangye;
	}

	public void setHangye(String hangye) {
		this.hangye = hangye;
	}

}
