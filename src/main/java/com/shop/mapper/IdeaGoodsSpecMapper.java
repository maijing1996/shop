package com.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shop.model.dto.GoodsSpec;
import com.shop.model.dto.SpecPrice;
import com.shop.model.po.IdeaGoodsSpec;
import com.shop.util.MyMapper;

public interface IdeaGoodsSpecMapper extends MyMapper<IdeaGoodsSpec> {

	List<GoodsSpec> listInfo(@Param("typeId") Long typeId);
	
	int isExist(@Param("sequence") int sequence);
	
	List<SpecPrice> getAllInfoByGoods(@Param("goodsId") Long goods_id);
}
