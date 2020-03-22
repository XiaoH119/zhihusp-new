package com.zhihu.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.fasterxml.jackson.core.JsonParseException;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.zhihu.common.bean.LoginBean;
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
	public Response insertOrder(@RequestBody Order order, HttpServletRequest request) {
		String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
		String userid = "";
		Response res = new Response();
		try {
			userid = JWT.decode(token).getAudience().get(0);
		} catch (JWTDecodeException j) {
			throw new RuntimeException("401");
		}

		try {
			order.setUserid(Integer.parseInt(userid));
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
	public Response getOrderPage(@RequestParam("ordertype") String ordertype, @PathVariable("pagenum") Integer pagenum,
			@PathVariable("pagesize") Integer pagesize) {
		Response res = new Response();
		try {
			Order order = new Order();
			order.setOrdertype(ordertype);
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
	public Response getorderdetail(@RequestParam String orderid) {
		Response res = new Response();
		try {
			Order order = new Order();
			order.setOrderid(orderid);
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
	public Response takeOrder(@RequestBody Order order, HttpServletRequest request) {
		String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
		String userid = "";
		Response res = new Response();
		try {
			userid = JWT.decode(token).getAudience().get(0);
		} catch (JWTDecodeException j) {
			throw new RuntimeException("401");
		}
		try {
			order.setUserid(Integer.parseInt(userid));
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
	public Response updateTakeOrder(@RequestBody Order order, HttpServletRequest request) {
		String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
		String userid = "";
		Response res = new Response();
		try {
			userid = JWT.decode(token).getAudience().get(0);
		} catch (JWTDecodeException j) {
			throw new RuntimeException("401");
		}
		try {
			order.setUserid(Integer.parseInt(userid));
			res = extservice.updateTakeOrder(order);
		} catch (Exception e) {
			logger.error("updatetakeorder", e);
			res.setResultError("操作出现异常，请联系管理员或稍后再试");
			return res;
		}
		return res;
	}

	/**
	 * 获取接单列表
	 * @param ordertype
	 * @param pagenum
	 * @param pagesize
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/gettakeorderlist/{pagenum}/{pagesize}")
	public Response getTakeOrderList(@RequestParam String ordertype,@RequestParam String takestate, @PathVariable("pagenum") Integer pagenum,
			@PathVariable("pagesize") Integer pagesize, HttpServletRequest request) {
		String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
		String userid = "";
		Response res = new Response();
		try {
			userid = JWT.decode(token).getAudience().get(0);
		} catch (JWTDecodeException j) {
			throw new RuntimeException("401");
		}
		try {
			Order order = new Order();
			order.setOrdertype(ordertype);
			order.setTakestate(takestate);
			order.setUserid(Integer.parseInt(userid));
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

	/**
	 * 查询我推广的订单
	 * @param ordertype
	 * @param pagenum
	 * @param pagesize
	 * @return
	 */
	@RequestMapping(value = "/getorderlist/{pagenum}/{pagesize}", method = RequestMethod.GET)
	public Response getOrderList(@RequestParam("ordertype") String ordertype,@RequestParam("orderstate") String orderstate, @PathVariable("pagenum") Integer pagenum,
			@PathVariable("pagesize") Integer pagesize,HttpServletRequest request) {
		String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
		String userid = "";
		Response res = new Response();
		try {
			userid = JWT.decode(token).getAudience().get(0);
		} catch (JWTDecodeException j) {
			throw new RuntimeException("401");
		}
		try {
			Order order = new Order();
			order.setUserid(Integer.parseInt(userid));
			order.setOrdertype(ordertype);
			order.setOrderstate(orderstate);
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
}
