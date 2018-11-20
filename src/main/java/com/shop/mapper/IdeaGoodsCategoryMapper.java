package com.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shop.model.dto.Category;
import com.shop.model.po.IdeaGoods;
import com.shop.model.po.IdeaGoodsCategory;
import com.shop.util.MyMapper;

public interface IdeaGoodsCategoryMapper extends MyMapper<IdeaGoodsCategory> {

	List<Category> listInfo(@Param("parent_id")Long parent_id);
	
	int isExists(Integer sequence);
	
	List<IdeaGoods> getGoodsById(@Param("id")Long id);
	
	List<Category> listAllInfo();
}
