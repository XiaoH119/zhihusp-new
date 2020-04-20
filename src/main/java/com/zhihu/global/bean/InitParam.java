package com.zhihu.global.bean;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhihu.common.bean.SystemParam;
import com.zhihu.mapper.InitParamMapper;

@Component
public class InitParam {

	static Logger logger = LoggerFactory.getLogger(InitParam.class);
	
	@Autowired
	InitParamMapper initmapper;
	
	/**
	 * 系统参数
	 */
	private static Map<String, Map<String,String>> systemParamMap;
	
	public static String getSystemParam(String paramcode) {
        logger.debug("InitParam.getSystemParam");
        logger.info("systemParamMap.get(paramcode) === " + systemParamMap.get(paramcode));
        return systemParamMap.get(paramcode).get("paramvalue");
    }
	
	@PostConstruct
	public void init() {
		systemParamMap = initmapper.getSystemParam();
		logger.info("systemParamMap===" + systemParamMap.toString());
	}
	
}
