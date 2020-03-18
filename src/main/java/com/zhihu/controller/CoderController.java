package com.zhihu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhihu.common.bean.Coder;
import com.zhihu.global.bean.Response;
import com.zhihu.service.CoderService;


@RestController
public class CoderController {
	
	@Autowired
	private CoderService coderservice;

	@RequestMapping(value="/getcoder", method=RequestMethod.GET)
	public Response requestMethodName(@RequestParam String codename,@RequestParam String codevalue) {
		Coder cd = new Coder();
		cd.setCodename(codename);
		cd.setCodevalue(codevalue);
		return coderservice.getCoder(cd);
	}
	
}
