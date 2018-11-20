package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 优惠券使用表
 * 偶木
 * 2018年8月22日
 */
public class IdeaCouponUser extends BaseEntity {

	private Long coupon_id;//优惠券ID
	private Long user_id;//用户ID
	private String coupon_sn;//优惠券序列号
	private Integer is_use;//是否使用
	private Long use_date;//使用时间
	private String order_sn;//使用订单SN
	private Integer is_delete;//是否删除
	private Long add_date;//领取时间
	
	public Long getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(Long coupon_id) {
		this.coupon_id = coupon_id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getCoupon_sn() {
		return coupon_sn;
	}
	public void setCoupon_sn(String coupon_sn) {
		this.coupon_sn = coupon_sn;
	}
	public Integer getIs_use() {
		return is_use;
	}
	public void setIs_use(Integer is_use) {
		this.is_use = is_use;
	}
	public Long getUse_date() {
		return use_date;
	}
	public void setUse_date(Long use_date) {
		this.use_date = use_date;
	}
	public String getOrder_sn() {
		return order_sn;
	}
	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}
	public Integer getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(Integer is_delete) {
		this.is_delete = is_delete;
	}
	public Long getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Long add_date) {
		this.add_date = add_date;
	}
}
