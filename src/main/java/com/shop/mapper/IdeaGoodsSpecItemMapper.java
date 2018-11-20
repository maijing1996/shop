package com.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shop.model.po.IdeaGoodsSpecItem;
import com.shop.util.MyMapper;

public interface IdeaGoodsSpecItemMapper extends MyMapper<IdeaGoodsSpecItem> {

	List<IdeaGoodsSpecItem> getInfo(@Param("type")Integer type, @Param("key")String key, @Param("key2")String key2);
	
	IdeaGoodsSpecItem getAllInfo(@Param("id")Long id);
}
