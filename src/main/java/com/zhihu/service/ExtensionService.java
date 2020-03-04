package com.zhihu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhihu.common.bean.Order;
import com.zhihu.common.bean.UserInfo;
import com.zhihu.common.bo.CommonBo;
import com.zhihu.common.bo.PageUtils;
import com.zhihu.global.bean.Constants;
import com.zhihu.global.bean.Response;
import com.zhihu.mapper.ExtensionMapper;
import com.zhihu.mapper.LoginMapper;

/**
 * @author HX
 *
 */
@Service
public class ExtensionService {

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
	public Response insertOrder(Order order) throws Exception {
		try {
			Response res = new Response();
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
	public Response takeOrder(Order order) throws Exception {
		try {
			Response res = new Response();
			// 查询用户信息
			UserInfo userinfo = new UserInfo();
			userinfo.setUserid(order.getUserid());
			userinfo.setPttype(order.getPttype());
			UserInfo userInfo = loginmapper.getUserInfo(userinfo);
			if (userInfo == null) {
				res.setResultError("请先完善基本信息再接单");
				return res;
			}
			Order orderdb = extmapper.getOrderDetail(order);
			if (orderdb == null) {
				res.setResultError("未查询到订单信息");
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
				Map<String, String> m = new HashMap<String, String>();
				m.put("userid", order.getUserid());
				m.put("integral", "5");
				extmapper.updateIntegral(m);
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
}
