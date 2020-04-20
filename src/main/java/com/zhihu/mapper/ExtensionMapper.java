package com.zhihu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.zhihu.common.bean.Order;

@Mapper
public interface ExtensionMapper {

	/**
	 * 发布订单
	 * @param order
	 */
	void insertOrder(Order order);
	
	/**
	 * 获取订单列表
	 * @param order
	 * @return
	 */
	List<Order> getOrderList(Order order);
	
	/**
	 * 获取订单详情
	 * @param order
	 * @return
	 */
	Order getOrderDetail(Order order);
	
	/**
	 * 确认接单
	 * @param order
	 */
	void takeOrder(Order order);
	
	/**
	 * 更新订单状态
	 * @param order
	 */
	void updateOrder(Order order);
	
	/**
	 * 更新接单状态
	 * @param order
	 */
	void updateTakeOrder(Order order);
	
	/**
	 * 查询接单信息
	 * @param order
	 * @return
	 */
	Order getTakeOrder(Order order);
	
	/**
	 * 更新积分
	 * @param m
	 */
	void updateIntegral(Map m);
	
	/**
	 * 查询接单列表
	 * @param order
	 * @return
	 */
	List<Order> getTakeOrderList(Order order);
	
	/**
	 * 获取订单接单列表
	 * @param order
	 * @return
	 */
	List<Order> getOrderListForJD(Order order);
	
	/**
	 * 查询反对权限
	 * @param userid
	 * @return
	 */
	int getOpponsePermit(String userid);
	
	/**
	 * 查询发单权限
	 * @param userid
	 * @return
	 */
	int getExtPermit(String userid);
}
