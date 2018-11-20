package com.shop.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.shop.model.dto.Goods;
import com.shop.model.dto.SellDetail;
import com.shop.model.po.IdeaGoods;
import com.shop.util.MyMapper;

public interface IdeaGoodsMapper extends MyMapper<IdeaGoods> {

	List<Goods> listInfo(@Param("keys") String keys, @Param("cat_id") Long cat_id, @Param("type") int type);
	
	int isExist(@Param("sequence") int sequence);
	
	List<SellDetail> getSellRanking();
	
	List<IdeaGoods> guessGoods();
	
	List<IdeaGoods> getNewGoods(@Param("size") Integer size, @Param("type") Integer type);
	
	List<IdeaGoods> getPopularity();

	List<IdeaGoods> info(@Param("person_id") Long person_id);
	
	List<Map<String, Object>> getAllByOrderId(@Param("orderId") Long orderId);
	
	int buyGoodsAmount(@Param("userId") Long userId, @Param("id") Long id);
	
	List<IdeaGoods> getCategroy(@Param("catId") Long catId);
	
	List<IdeaGoods> getPerson(@Param("personId") Long personId);
}
