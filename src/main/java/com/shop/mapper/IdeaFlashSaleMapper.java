package com.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shop.model.dto.FlashSale;
import com.shop.model.po.IdeaFlashSale;
import com.shop.util.MyMapper;

public interface IdeaFlashSaleMapper extends MyMapper<IdeaFlashSale> {

	List<FlashSale> listInfo(@Param("title") String title);
}
