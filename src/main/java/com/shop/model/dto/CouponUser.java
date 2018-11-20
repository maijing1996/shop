package com.shop.model.dto;

import com.shop.util.DateUtil;

public class CouponUser {

	private Long id;
	private String title;//优惠券名称
	private String nickname;
	private String coupon_sn;//优惠券序列号
	private String is_use;//是否使用
	private String use_date;//使用时间
	private String order_sn;//使用订单SN
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getCoupon_sn() {
		return coupon_sn;
	}
	public void setCoupon_sn(String coupon_sn) {
		this.coupon_sn = coupon_sn;
	}
	public String getIs_use() {
		return is_use;
	}
	public void setIs_use(Integer is_use) {
		if(is_use == null) {
			
		} else if(is_use == 0) {
			this.is_use = "未使用";
		} else if(is_use == 1) {
			this.is_use = "已使用";
		}
	}
	public String getUse_date() {
		return use_date;
	}
	public void setUse_date(Long use_date) {
		if(use_date != null) {
			this.use_date = DateUtil.format(use_date*1000, DateUtil.FORMAT_YYYY_MM_dd_hh_mm_ss);
		}
	}
	public String getOrder_sn() {
		return order_sn;
	}
	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}
}
