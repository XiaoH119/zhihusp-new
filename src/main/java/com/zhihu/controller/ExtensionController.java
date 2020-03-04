package com.zhihu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zhihu.common.bean.Order;
import com.zhihu.common.bean.UserInfo;
import com.zhihu.global.bean.Response;
import com.zhihu.service.ExtensionService;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ExtensionController {

	static Logger logger = LoggerFactory.getLogger(ExtensionController.class);

	@Autowired
	private ExtensionService extservice;

	/**
	 * 发布订单
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/insertorder", method = RequestMethod.POST)
	public Response extension(@RequestBody Order order) {
		Response res = new Response();
		try {
			res = extservice.insertOrder(order);
		} catch (Exception e) {
			logger.error("reg", e);
			res.setResultError("系统异常，请联系管理员！");
			return res;
		}
		return res;

	}

	/**
	 * 查询订单列表
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/getorderpage/{pagenum}/{pagesize}", method = RequestMethod.GET)
	public Response getOrderPage(@RequestBody Order order, @PathVariable("pagenum") Integer pagenum,
			@PathVariable("pagesize") Integer pagesize) {
		Response res = new Response();
		try {
			order.setPagenum(pagenum);
			order.setPagesize(pagesize);
			res = extservice.getOrderPage(order);
		} catch (Exception e) {
			logger.error("reg", e);
			res.setResultError("系统异常，请联系管理员！");
			return res;
		}
		return res;
	}

	/**
	 * 查询 订单详情
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/getorderdetail", method = RequestMethod.GET)
	public Response requestMethodName(@RequestBody Order order) {
		Response res = new Response();
		try {
			res = extservice.getOrderDetail(order);
		} catch (Exception e) {
			logger.error("reg", e);
			res.setResultError("系统异常，请联系管理员！");
			return res;
		}
		return res;
	}

	/**
	 * 接单
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/takeorder", method = RequestMethod.POST)
	public Response takeOrder(@RequestBody Order order) {
		Response res = new Response();
		try {
			res = extservice.takeOrder(order);
		} catch (Exception e) {
			logger.error("takeOrder", e);
			res.setResultError("操作出现异常，请联系管理员或稍后再试");
			return res;
		}
		return res;
	}

	/**
	 * 更新接单状态
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/updatetakeorder", method = RequestMethod.POST)
	public Response updateTakeOrder(@RequestBody Order order) {
		Response res = new Response();
		try {
			res = extservice.updateTakeOrder(order);
		} catch (Exception e) {
			logger.error("updatetakeorder", e);
			res.setResultError("操作出现异常，请联系管理员或稍后再试");
			return res;
		}
		return res;
	}

	@GetMapping(value = "/gettakeorderlist/{pagenum}/{pagesize}")
	public Response getTakeOrderList(@RequestBody Order order, @PathVariable("pagenum") Integer pagenum,
			@PathVariable("pagesize") Integer pagesize) {
		Response res = new Response();
		try {
			order.setPagenum(pagenum);
			order.setPagesize(pagesize);
			res = extservice.getTakeOrderList(order);
		} catch (Exception e) {
			logger.error("getTakeOrderList", e);
			res.setResultError("查询出现异常，请稍后再试");
			return res;
		}
		return res;
	}

	private boolean checkUser(UserInfo userinfo) {
		boolean result = true;

		return result;
	}
}
