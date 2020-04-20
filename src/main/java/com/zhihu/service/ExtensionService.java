package com.zhihu.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhihu.common.bean.Integral;
import com.zhihu.common.bean.Order;
import com.zhihu.common.bean.UserInfo;
import com.zhihu.common.bo.CommonBo;
import com.zhihu.common.bo.PageUtils;
import com.zhihu.global.bean.Constants;
import com.zhihu.global.bean.InitParam;
import com.zhihu.global.bean.Response;
import com.zhihu.mapper.ExtensionMapper;
import com.zhihu.mapper.LoginMapper;

/**
 * @author HX
 *
 */
@Service
public class ExtensionService {

	static Logger logger = LoggerFactory.getLogger(ExtensionService.class);

	@Autowired
	private ExtensionMapper extmapper;

	@Autowired
	private LoginMapper loginmapper;

	/**
	 * 发布订单
	 * 
	 * @param order
	 * @return
	 */
	@Transactional(rollbackFor = { Exception.class })
	public Response insertOrder(Order order) throws Exception {
		try {
			Response res = new Response();
			UserInfo userInfo = new UserInfo();
			userInfo.setUserid(order.getUserid());
			UserInfo user = loginmapper.getUserInfo(userInfo);
			if (user == null) {
				res.setResultError("您的登录好像有异常哦，请重新登录后再重试一下吧");
				return res;
			}
			String dhbl = InitParam.getSystemParam(Constants.SYSTEMPARAM_0001);
			BigDecimal needjf = order.getPrice().multiply(new BigDecimal(order.getNeedcnt()))
					.multiply(new BigDecimal(dhbl));
			logger.info("user.getIntegral()===" + user.getIntegral());
			logger.info("needjf=====" + needjf);
			if (needjf.compareTo(user.getIntegral()) > 0) {
				res.setResultError("您的余额不足，请自助充值或联系客服快速充值后再试吧");
				return res;
			}
			BigDecimal kouchujf = needjf.multiply(new BigDecimal(-1));
			logger.info("kouchujifen === " + kouchujf);
			Integral integral = new Integral();
			integral.setUserid(String.valueOf(order.getUserid()));
			integral.setIntegral(kouchujf);
			loginmapper.updateIntegral(integral);
			// 发布订单
			extmapper.insertOrder(order);
			res.setResultRight("发布成功");
			return res;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * 查询订单列表
	 * 
	 * @param order
	 * @return
	 */
	public Response getOrderPage(Order order) throws Exception {
		try {
			Response res = new Response();
			PageHelper.startPage(order.getPagenum(), order.getPagesize());
			List<Order> orderlist = extmapper.getOrderList(order);
			res.setData(PageUtils.getPageResult(new PageInfo<>(orderlist)));
			res.setResultRight("正确完成");
			return res;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * 查询订单明细
	 * 
	 * @param order
	 * @return
	 */
	public Response getOrderDetail(Order order) throws Exception {
		try {
			Response res = new Response();
			Order ordres = extmapper.getOrderDetail(order);
			res.setResultRight("正确完成");
			res.setData(ordres);
			return res;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * 接单
	 * 
	 * @param order
	 * @return
	 */
	@Transactional(rollbackFor = { Exception.class })
	public Response takeOrder(Order order) throws Exception {
		try {
			Response res = new Response();
			// 查询用户信息
			UserInfo userinfo = new UserInfo();
			userinfo.setUserid(order.getUserid());
			userinfo.setPttype("1");
			UserInfo userInfo = loginmapper.getUserInfoExt(userinfo);
			if (userInfo == null) {
				res.setResultError("请先完善基本信息再接单");
				return res;
			}
			Order orderdb = extmapper.getOrderDetail(order);
			if (orderdb == null) {
				res.setResultError("未查询到订单信息");
				return res;
			}

			if (order.getUserid() == orderdb.getUserid()) {
				res.setResultError("本人不能接自己推广的订单");
				return res;
			}

			if (orderdb.getNeedcnt() == orderdb.getHadcnt()) {
				res.setResultError("接单人数已满，请选择其他订单");
				return res;
			}

			if (userInfo.getFensi() < orderdb.getFensi()) {
				res.setResultError("粉丝数未达到指定要求，请选择其他订单");
				return res;
			}
			Order ordertake = extmapper.getTakeOrder(order);
			if (ordertake != null) {
				res.setResultError("已抢单，不能重复抢单");
				return res;
			}
			// 确认接单
			extmapper.takeOrder(order);
			// 更新订单接单人数
			extmapper.updateOrder(order);
			res.setResultRight("接单完成，尽快完成订单来获取佣金哦！");
			return res;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * 更新接单状态
	 * 
	 * @param order
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = { Exception.class })
	public Response updateTakeOrder(Order order) throws Exception {
		Response res = new Response();
		try {
			if (order.getTakestate() == null || order.getTakestate().equals("")) {
				res.setResultError("接单状态不能为空");
				return res;
			}
			if (!Constants.TAKESTATES.contains(order.getTakestate())) {
				res.setResultError("接单状态不正确");
				return res;
			}
			Order orderdetail = extmapper.getOrderDetail(order);
			if (orderdetail == null) {
				res.setResultError("未查询到订单信息，请确认订单存在");
				return res;
			}
			// 查询接单是否存在
			Order orderdb = extmapper.getTakeOrder(order);
			if (orderdb == null) {
				res.setResultError("未查到接单，请先接单！");
				return res;
			}
			if (orderdb.getTakestate().equals("3")) {
				res.setResultError("已完成接单，无需重复操作");
				return res;
			}
			if (order.getTakestate().equals("3")) {
				order.setFinishdate(CommonBo.getCurrentTime());
			}
			extmapper.updateTakeOrder(order);
			// 接单完成需要更新积分
			if (order.getTakestate().equals("3")) {
				// 对接单单价和积分进行转换
				// TODO
				// 现金兑换积分比例
				String xjtojf = InitParam.getSystemParam(Constants.SYSTEMPARAM_0001);
//				接单获取比例
				String jdbl = "";
				if ("1".equals(orderdb.getOrdertype())) {
					jdbl = InitParam.getSystemParam(Constants.SYSTEMPARAM_0002);
				} else if ("2".equals(orderdb.getOrdertype())) {
					jdbl = InitParam.getSystemParam(Constants.SYSTEMPARAM_0003);
				} else if ("3".equals(orderdb.getOrdertype())) {
					jdbl = InitParam.getSystemParam(Constants.SYSTEMPARAM_0004);
				}
//				String price = orderdb.getPrice().toString();
				String jf = orderdb.getPrice().multiply(new BigDecimal(xjtojf)).toString();
				logger.info("订单单价积分：" + jf);
				String jdjf = new BigDecimal(jf).multiply(new BigDecimal(jdbl)).setScale(2, RoundingMode.HALF_UP)
						.toString();
				logger.info("接单获得积分：" + jdjf);
//				logger.info("接单获得积分：" + jf);
				Map<String, String> m = new HashMap<String, String>();
				m.put("userid", String.valueOf(order.getUserid()));
				m.put("integral", jdjf);
				Integral integral = new Integral();
				integral.setUserid(String.valueOf(order.getUserid()));
				integral.setIntegral(new BigDecimal(jdjf));
				loginmapper.updateIntegral(integral);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		return res;
	}

	/**
	 * 查询接单列表
	 * 
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public Response getTakeOrderList(Order order) throws Exception {
		Response res = new Response();
		try {
			PageHelper.startPage(order.getPagenum(), order.getPagesize());
			List<Order> orderlist = extmapper.getTakeOrderList(order);
			res.setData(PageUtils.getPageResult(new PageInfo<>(orderlist)));
		} catch (Exception e) {
			throw new Exception(e);
		}
		return res;
	}

	/**
	 * 查询接单
	 * 
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public Response getTakeOrder(Order order) throws Exception {
		Response res = new Response();
		try {
			// 查询接单是否存在
			Order orderdb = extmapper.getTakeOrder(order);
			res.setData(orderdb);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return res;
	}

	/**
	 * 查询订单列表
	 * 
	 * @param order
	 * @return
	 */
	public Response getOrderPageForJD(Order order) throws Exception {
		try {
			Response res = new Response();
			PageHelper.startPage(order.getPagenum(), order.getPagesize());
			List<Order> orderlist = extmapper.getOrderListForJD(order);
			res.setData(PageUtils.getPageResult(new PageInfo<>(orderlist)));
			res.setResultRight("正确完成");
			return res;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * 查询是否拥有反对
	 * 
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public Response getOpposePermit(String userid) throws Exception {
		Response res = new Response();
		try {
			// 查询接单是否存在
			int cnt = extmapper.getOpponsePermit(userid);
			if (cnt == 0) {
				res.setResultError("开通反对接单权限，联系客服小姐姐哦～");
				return res;
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		return res;
	}

	/**
	 * 查询是否已经可以接单
	 * 
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public Response getTakeUser(UserInfo userInfo) throws Exception {
		Response res = new Response();
		try {
			int cnt = loginmapper.selectTakeUser(userInfo);
			if (cnt == 0) {
				res.setResultError("您还未开通接单权限，请联系客服小姐姐开通哦~~");
				return res;
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		return res;
	}

	/**
	 * 添加接单用户
	 * 
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public Response insertTakeUser(UserInfo userInfo) throws Exception {
		Response res = new Response();
		try {
			int cnt = loginmapper.selectTakeUser(userInfo);
			if (cnt > 0) {
				res.setResultRight("测试通过，可以去赚钱喽");
				return res;
			} else {
				// 查询用户平台ID
				userInfo.setPttype("1");
				UserInfo userext = loginmapper.getUserInfoExt(userInfo);
				if (userext == null) {
					res.setResultError("请先完善您的个人信息再接单哦！");
					return res;
				} else {
					if (!userext.getPtid().equals(userInfo.getPtid())) {
						res.setResultError("您绑定的知乎ID与测试的不一致哦，请您核实后重新测试~");
						return res;
					} else {
						loginmapper.insertTakeUser(userInfo);
					}
				}
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		res.setResultRight("测试通过，可以去赚钱喽");
		return res;
	}
	
	/**
	 * 查询发单权限
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public Response getExtPermit(String userid) throws Exception {
		Response res = new Response();
		try {
			// 查询发单权限是否存在
			int cnt = extmapper.getExtPermit(userid);
			if (cnt == 0) {
				res.setResultError("开通发单权限，联系客服小姐姐哦～");
				return res;
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		return res;
	}
}
