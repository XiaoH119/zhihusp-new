package com.zhihu.common.bo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonBo {

	public static String getCurrentTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());
		return time;
	}
	
	public static void main(String[] args) {
		System.out.println(CommonBo.getCurrentTime());
	}
}
