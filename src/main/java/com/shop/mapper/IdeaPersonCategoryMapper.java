package com.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shop.model.dto.PersonCategory;
import com.shop.model.po.IdeaGoods;
import com.shop.model.po.IdeaPersonCategory;
import com.shop.util.MyMapper;

public interface IdeaPersonCategoryMapper extends MyMapper<IdeaPersonCategory> {

	List<PersonCategory> listInfo(@Param("parentId") Long parentId);
	
	List<PersonCategory> listAllInfo();
	
	List<IdeaGoods> getGoodsById(@Param("id") Long id);
	
	List<IdeaGoods> getGoodsByKeys(@Param("keyword") String keyword);
}
