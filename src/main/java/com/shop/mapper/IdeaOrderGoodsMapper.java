package com.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shop.model.dto.OrderGoods;
import com.shop.model.po.IdeaOrderGoods;
import com.shop.util.MyMapper;

public interface IdeaOrderGoodsMapper extends MyMapper<IdeaOrderGoods> {

	List<OrderGoods> getOrderGoods(@Param("orderId") Long orderId);
}
