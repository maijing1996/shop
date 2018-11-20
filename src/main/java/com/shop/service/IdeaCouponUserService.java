package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.mapper.IdeaCouponUserMapper;
import com.shop.model.dto.CouponUser;
import com.shop.model.po.IdeaCouponUser;
import com.shop.service.common.BaseService;

@Service
public class IdeaCouponUserService extends BaseService<IdeaCouponUser, IdeaCouponUserMapper> {

	@Override
	protected String getTableName() {
		return "idea_coupon_user";
	}

	/**
	 * 查看优惠券领取使用情况
	 * @param page
	 * @param size
	 * @param coupon_id
	 * @return
	 */
	public PageInfo<CouponUser> listInfo(Integer page, Integer size, long coupon_id) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<CouponUser> list = mapper.listInfo(coupon_id);
		PageInfo<CouponUser> pageInfo = new PageInfo<CouponUser>(list);
		return pageInfo;
	}
}
