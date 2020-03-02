package com.zhihu.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhihu.common.bean.Coder;
import com.zhihu.global.bean.Response;
import com.zhihu.mapper.CoderMapper;

@Service
public class CoderService {

	@Autowired
	private CoderMapper codermapper;

	private final static Logger logger = LoggerFactory.getLogger(CoderService.class);

	/**
	 * 获取代码
	 * @param coder
	 * @return
	 */
	public Response getCoder(Coder coder) {
		Response res = new Response();
		List<Coder> codelist = codermapper.getcoder(coder);
		System.out.println("codelist=" + codelist.size());
		logger.debug("codelist==debug==" + codelist.size());
		logger.info("codelist==info==" + codelist.size());
		logger.error("codelist==error==" + codelist.size());
		logger.trace("codelist==trace==" + codelist.size());
//		if (codelist.size() > 0) {
//			res.setCodelist(codelist);
//		}
		res.setData(codelist);
		return res;
	}
}
