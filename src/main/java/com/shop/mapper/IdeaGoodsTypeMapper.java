package com.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shop.model.dto.OptionBox;
import com.shop.model.po.IdeaGoodsType;
import com.shop.util.MyMapper;

public interface IdeaGoodsTypeMapper extends MyMapper<IdeaGoodsType> {

	List<IdeaGoodsType> listInfo(@Param("title") String title);
	
	List<OptionBox> listOptionBox();
}
