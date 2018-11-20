package com.shop.mapper;

import org.apache.ibatis.annotations.Param;

import com.shop.model.po.IdeaExpressCompany;
import com.shop.util.MyMapper;

public interface IdeaExpressCompanyMapper extends MyMapper<IdeaExpressCompany> {

	String getCodeByName(@Param("expressTitle") String expressTitle);
}
