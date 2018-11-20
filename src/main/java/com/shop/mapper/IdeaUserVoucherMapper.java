package com.shop.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.shop.model.po.IdeaUserVoucher;
import com.shop.util.MyMapper;

public interface IdeaUserVoucherMapper extends MyMapper<IdeaUserVoucher> {

	List<Map<String, Object>> getVoucher(@Param("userId") Long userId);
	
	List<Map<String, String>> getVoucherName(@Param("userId") Long userId);
	
	List<Map<String, Object>> listVoucher(@Param("userId") Long userId, @Param("type") Integer type);
}
