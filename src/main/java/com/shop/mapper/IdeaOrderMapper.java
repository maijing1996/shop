package com.shop.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.shop.model.dto.Order;
import com.shop.model.dto.OrderInfo;
import com.shop.model.dto.OrderStatic;
import com.shop.model.dto.SellDetail;
import com.shop.model.po.IdeaOrder;
import com.shop.util.MyMapper;

public interface IdeaOrderMapper extends MyMapper<IdeaOrder> {

	List<Order> listInfo(@Param("startTime") Long startTime, @Param("endTime")Long endTime,
			@Param("nickName")String nickName, @Param("status")Integer status);
	
	List<SellDetail> sellDetail(@Param("startTime") Long startTime, @Param("endTime")Long endTime,
			@Param("title")String title);
	
	Order details(@Param("id") Long id);
	
	IdeaOrder getOrderBySn(@Param("orderNo") String orderNo);
	
	List<IdeaOrder> getInfoByOpenId(@Param("userId") Long userId, @Param("state") Integer state);
	
	int getCount(@Param("userId") Long userId);
	
	List<OrderInfo> getDistributionOrder(@Param("userId") Long userId);
	
	List<OrderInfo> listMyGroupOrder(@Param("userId") Long userId);
	
	List<Map<String, Integer>> getOrderCount(@Param("userId")Long userId);
	
	int verifyInfoIsNull(@Param("wechatOrderId") String wechatOrderId);
	
	IdeaOrder getByOrderSn(@Param("orderSn") String orderSn);
	
	List<IdeaOrder> getNotPayOrder(@Param("unpaid") int unpaid);
	
	String getOneGoodsPic(@Param("orderId") Long orderId);
	
	List<OrderStatic> statistics(@Param("startTime") Long start, @Param("endTime") Long end);
	
	List<IdeaOrder> getGainGoods(@Param("shipments") long shipments);
}
