package com.shop.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.shop.model.po.IdeaGoodsSpecPrice;
import com.shop.util.MyMapper;

public interface IdeaGoodsSpecPriceMapper extends MyMapper<IdeaGoodsSpecPrice> {

	List<IdeaGoodsSpecPrice> getAllInfo(@Param("goodsId") Long goodsId);
	
	List<Map<String, Object>> getInfo(@Param("goodsId") Long goodsId);
	
	IdeaGoodsSpecPrice getInfoById(@Param("goodsId") Long goodsId, @Param("keys")String keys);
	
	IdeaGoodsSpecPrice getAllById(@Param("id") Long id);
	
	List<IdeaGoodsSpecPrice> listInfo(@Param("keys_name") String keys_name);
	
	void deleteByGoodsId(@Param("goodsId") Long goodsId);
}
