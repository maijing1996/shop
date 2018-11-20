package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaOrderGoodsMapper;
import com.shop.model.dto.OrderGoods;
import com.shop.model.po.IdeaOrderGoods;
import com.shop.service.common.BaseService;

@Service
public class IdeaOrderGoodsService extends BaseService<IdeaOrderGoods, IdeaOrderGoodsMapper> {

	@Override
	protected String getTableName() {
		return "idea_order_goods";
	}

	/**
	 * 根据订单Id获得商品信息
	 * @param orderId
	 * @return
	 */
	public List<OrderGoods> getOrderGoods(Long orderId) {
		return mapper.getOrderGoods(orderId);
	}
}
