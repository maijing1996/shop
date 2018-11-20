package com.shop.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.shop.model.dto.Coupon;
import com.shop.model.po.IdeaCoupon;
import com.shop.util.MyMapper;

@Service
public interface IdeaCouponMapper extends MyMapper<IdeaCoupon> {

	List<Coupon> listInfo(@Param("title") String title);
	
	List<Map<String, Object>> getVoucher(@Param("userId") Long userId);
	
	List<Map<String, Object>> getUseVoucher(@Param("userId") Long userId, @Param("price") Double price);
	
	List<Map<String, Object>> listVoucher(@Param("userId") Long userId, @Param("type") Integer type);
	
	List<IdeaCoupon> getInfoByIds(@Param("couponSn") List<String> couponSn, @Param("userId") Long userId);
	
	int updateUseCoupon(@Param("couponSn") List<String> couponSn, @Param("userId") Long userId);
}
