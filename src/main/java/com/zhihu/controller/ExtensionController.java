package com.zhihu.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.zhihu.annotation.UserLoginToken;
import com.zhihu.common.bean.Order;
import com.zhihu.common.bean.UserInfo;
import com.zhihu.global.bean.Response;
import com.zhihu.service.ExtensionService;

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
	@UserLoginToken
	@RequestMapping(value = "/insertorder", method = RequestMethod.POST)
	public Response insertOrder(@RequestBody Order order, HttpServletRequest request) {
		String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
		String userid = "";
		Response res = new Response();
		try {
			userid = JWT.decode(token).getAudience().get(0);
		} catch (JWTDecodeException j) {
			logger.error("getTakeUser", j);
			res.setResultError("亲，您是不是没登录，请您登录后再操作哦！");
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
	@UserLoginToken
	@RequestMapping(value = "/getorderpage/{pagenum}/{pagesize}", method = RequestMethod.GET)
	public Response getOrderPage(@RequestParam("ordertype") String ordertype,
			@RequestParam("orderstate") String orderstate, @RequestParam("checktype") String checktype,
			@PathVariable("pagenum") Integer pagenum, @PathVariable("pagesize") Integer pagesize,
			HttpServletRequest request) {
		String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
		String userid = "";
		Response res = new Response();
		try {
			userid = JWT.decode(token).getAudience().get(0);
		} catch (JWTDecodeException j) {
			logger.error("getTakeUser", j);
			res.setResultError("亲，您是不是没登录，请您登录后再操作哦！");
		}
		try {
			logger.info("userid=====" + userid);
			Order order = new Order();
			order.setUserid(Integer.parseInt(userid));
			order.setOrdertype(ordertype);
			order.setOrderstate(orderstate);
			order.setChecktype(checktype);
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
	@UserLoginToken
	@RequestMapping(value = "/getorderdetail", method = RequestMethod.GET)
	public Response getorderdetail(@RequestParam String orderid, HttpServletRequest request) {
		String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
		String userid = "";
		Response res = new Response();
		try {
			userid = JWT.decode(token).getAudience().get(0);
		} catch (JWTDecodeException j) {
			logger.error("getTakeUser", j);
			res.setResultError("亲，您是不是没登录，请您登录后再操作哦！");
		}
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
	@UserLoginToken
	@RequestMapping(value = "/takeorder", method = RequestMethod.POST)
	public Response takeOrder(@RequestBody Order order, HttpServletRequest request) {
		String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
		String userid = "";
		Response res = new Response();
		try {
			userid = JWT.decode(token).getAudience().get(0);
		} catch (JWTDecodeException j) {
			logger.error("getTakeUser", j);
			res.setResultError("亲，您是不是没登录，请您登录后再操作哦！");
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
	@UserLoginToken
	@RequestMapping(value = "/updatetakeorder", method = RequestMethod.POST)
	public Response updateTakeOrder(@RequestBody Order order, HttpServletRequest request) {
		String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
		String userid = "";
		Response res = new Response();
		try {
			userid = JWT.decode(token).getAudience().get(0);
		} catch (JWTDecodeException j) {
			logger.error("getTakeUser", j);
			res.setResultError("亲，您是不是没登录，请您登录后再操作哦！");
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
	 * 
	 * @param ordertype
	 * @param pagenum
	 * @param pagesize
	 * @param request
	 * @return
	 */
	@UserLoginToken
	@GetMapping(value = "/gettakeorderlist/{pagenum}/{pagesize}")
	public Response getTakeOrderList(@RequestParam String ordertype, @RequestParam String takestate,
			@PathVariable("pagenum") Integer pagenum, @PathVariable("pagesize") Integer pagesize,
			HttpServletRequest request) {
		String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
		String userid = "";
		Response res = new Response();
		try {
			userid = JWT.decode(token).getAudience().get(0);
		} catch (JWTDecodeException j) {
			logger.error("getTakeUser", j);
			res.setResultError("亲，您是不是没登录，请您登录后再操作哦！");
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
	 * 
	 * @param ordertype
	 * @param pagenum
	 * @param pagesize
	 * @return
	 */
	@UserLoginToken
	@RequestMapping(value = "/getorderlist/{pagenum}/{pagesize}", method = RequestMethod.GET)
	public Response getOrderList(@RequestParam("ordertype") String ordertype,
			@RequestParam("orderstate") String orderstate, @RequestParam("checktype") String checktype,
			@PathVariable("pagenum") Integer pagenum, @PathVariable("pagesize") Integer pagesize,
			HttpServletRequest request) {
		String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
		String userid = "";
		Response res = new Response();
		try {
			userid = JWT.decode(token).getAudience().get(0);
		} catch (JWTDecodeException j) {
			logger.error("getTakeUser", j);
			res.setResultError("亲，您是不是没登录，请您登录后再操作哦！");
		}
		try {
			Order order = new Order();
			order.setUserid(Integer.parseInt(userid));
			order.setOrdertype(ordertype);
			order.setOrderstate(orderstate);
			order.setChecktype(checktype);
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
	 * @param orderid
	 * @param request
	 * @return
	 */
	@UserLoginToken
	@GetMapping("/gettakeorder")
	public Response getTakeOrder(@RequestParam("orderid") String orderid, HttpServletRequest request) {
		String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
		String userid = "";
		Response res = new Response();
		try {
			userid = JWT.decode(token).getAudience().get(0);
		} catch (JWTDecodeException j) {
			logger.error("getTakeUser", j);
			res.setResultError("亲，您是不是没登录，请您登录后再操作哦！");
		}
		try {
			Order order = new Order();
			order.setUserid(Integer.parseInt(userid));
			order.setOrderid(orderid);
			res = extservice.getTakeOrder(order);
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
	@UserLoginToken
	@RequestMapping(value = "/getorderpageforjd/{pagenum}/{pagesize}", method = RequestMethod.GET)
	public Response getOrderPageForJD(@RequestParam("ordertype") String ordertype,
			@RequestParam("orderstate") String orderstate, @RequestParam("checktype") String checktype,
			@PathVariable("pagenum") Integer pagenum, @PathVariable("pagesize") Integer pagesize,
			HttpServletRequest request) {
		String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
		String userid = "";
		Response res = new Response();
		try {
			userid = JWT.decode(token).getAudience().get(0);
		} catch (JWTDecodeException j) {
			logger.error("getTakeUser", j);
			res.setResultError("亲，您是不是没登录，请您登录后再操作哦！");
		}
		try {
			logger.info("userid=====" + userid);
			Order order = new Order();
			order.setUserid(Integer.parseInt(userid));
			order.setOrdertype(ordertype);
			order.setOrderstate(orderstate);
			order.setChecktype(checktype);
			order.setPagenum(pagenum);
			order.setPagesize(pagesize);
			res = extservice.getOrderPageForJD(order);
		} catch (Exception e) {
			logger.error("reg", e);
			res.setResultError("系统异常，请联系管理员！");
			return res;
		}
		return res;
	}

	@UserLoginToken
	@GetMapping("/getopposepermit")
	public Response getOpposePermit(HttpServletRequest request) {
		String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
		String userid = "";
		Response res = new Response();
		try {
			userid = JWT.decode(token).getAudience().get(0);
			res = extservice.getOpposePermit(userid);
		} catch (JWTDecodeException j) {
			throw new RuntimeException("401");
		} catch (Exception e) {
			logger.error("getOpposePermit", e);
			res.setResultError("系统异常，请联系管理员！");
			return res;
		}
		return res;
	}

	@UserLoginToken
	@GetMapping("/gettakeuser")
	public Response getTakeUser(HttpServletRequest request) {
		String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
		String userid = "";
		Response res = new Response();
		try {
			userid = JWT.decode(token).getAudience().get(0);

		} catch (JWTDecodeException j) {
			logger.error("getTakeUser", j);
			res.setResultError("亲，您是不是没登录，请您登录后再操作哦！");
		}
		try {
			UserInfo userInfo = new UserInfo();
			userInfo.setUserid(Integer.parseInt(userid));
			res = extservice.getTakeUser(userInfo);
		} catch (Exception e) {
			logger.error("getTakeUser", e);
			res.setResultError("系统异常，请联系管理员！");
			return res;
		}
		return res;
	}

	@UserLoginToken
	@PostMapping("/inserttakeuser")
	public Response insertTakeUser(@RequestBody UserInfo userInfo, HttpServletRequest request) {
		String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
		String userid = "";
		Response res = new Response();
		try {
			userid = JWT.decode(token).getAudience().get(0);

		} catch (JWTDecodeException j) {
			logger.error("getTakeUser", j);
			res.setResultError("亲，您是不是没登录，请您登录后再操作哦！");
		}
		try {
//			UserInfo userInfo = new UserInfo();
			userInfo.setUserid(Integer.parseInt(userid));
			res = extservice.insertTakeUser(userInfo);
		} catch (Exception e) {
			logger.error("getTakeUser", e);
			res.setResultError("系统异常，请联系管理员！");
			return res;
		}
		return res;
	}

	@UserLoginToken
	@GetMapping("/getextpermit")
	public Response getExtPermit(HttpServletRequest request) {
		String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
		String userid = "";
		Response res = new Response();
		try {
			userid = JWT.decode(token).getAudience().get(0);
		} catch (JWTDecodeException j) {
			logger.error("getTakeUser", j);
			res.setResultError("亲，您是不是没登录，请您登录后再操作哦！");
		}
		try {
			res = extservice.getExtPermit(userid);
		} catch (Exception e) {
			logger.error("getOpposePermit", e);
			res.setResultError("系统异常，请联系管理员！");
			return res;
		}
		return res;
	}
}
