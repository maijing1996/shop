package com.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shop.model.dto.CouponUser;
import com.shop.model.po.IdeaCouponUser;
import com.shop.util.MyMapper;

public interface IdeaCouponUserMapper extends MyMapper<IdeaCouponUser> {

	List<CouponUser> listInfo(@Param("coupon_id") long coupon_id);
}
