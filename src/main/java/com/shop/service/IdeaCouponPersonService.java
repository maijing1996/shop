package com.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.exception.BusinessException;
import com.shop.mapper.IdeaCouponPersonMapper;
import com.shop.model.po.IdeaCoupon;
import com.shop.model.po.IdeaCouponPerson;
import com.shop.model.po.IdeaCouponUser;
import com.shop.service.common.BaseService;
import com.shop.util.DateUtil;
import com.shop.util.StringUtil;

@Service
public class IdeaCouponPersonService extends BaseService<IdeaCouponPerson, IdeaCouponPersonMapper> {

	@Autowired
	private IdeaCouponService ideaCouponService;
	@Autowired
	private IdeaCouponUserService ideaCouponUserService;
	
	@Override
	protected String getTableName() {
		return "idea_coupon_person";
	}

	/**
	 * 领取新人券
	 * @param openId
	 * @throws BusinessException 
	 */
	@Transactional(rollbackFor = BusinessException.class)
	public void gainCoupon(String openId, Long userId) throws BusinessException {
		IdeaCoupon ideaCoupon = new IdeaCoupon();
		ideaCoupon.setType(5);
		List<IdeaCoupon> list = ideaCouponService.getAll(ideaCoupon, null);
		if(list != null && !list.isEmpty()) {
			for(IdeaCoupon coupon : list) {
				Long now = DateUtil.getTimestamp();
				for(int i=0; i < coupon.getNumber(); i++) {
					String sn = StringUtil.getCouponSn(8);
					
					IdeaCouponUser ideaCouponUser = new IdeaCouponUser();
					ideaCouponUser.setAdd_date(now);
					ideaCouponUser.setCoupon_id(coupon.getId());
					ideaCouponUser.setCoupon_sn(sn);
					ideaCouponUser.setIs_delete(0);
					ideaCouponUser.setIs_use(0);
					ideaCouponUser.setUser_id(userId);
					
					ideaCouponUserService.save(ideaCouponUser);
				}
			}
			
			IdeaCouponPerson ideaCouponPerson = new IdeaCouponPerson();
			ideaCouponPerson.setOpen_id(openId);
			ideaCouponPerson.setAdd_date(DateUtil.getTimestamp());
			ideaCouponPerson.setIs_gain(1);
			ideaCouponPerson.setUser_id(userId);
			this.save(ideaCouponPerson);
		}
	}
}
