package com.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shop.model.dto.Brand;
import com.shop.model.po.IdeaBrand;
import com.shop.util.MyMapper;

public interface IdeaBrandMapper extends MyMapper<IdeaBrand> {

	List<Brand> listInfo(@Param("title") String title);
	
	int isExists(@Param("id") Long id, @Param("number") int number);
}
