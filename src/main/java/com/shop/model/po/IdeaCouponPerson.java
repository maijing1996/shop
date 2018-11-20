package com.shop.model.po;

import com.shop.model.common.BaseEntity;

public class IdeaCouponPerson extends BaseEntity {

	//idea_coupon_person
	private Long user_id;
	private String open_id;
	private Integer is_gain;
	private Long add_date;
	
	public String getOpen_id() {
		return open_id;
	}
	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Integer getIs_gain() {
		return is_gain;
	}
	public void setIs_gain(Integer is_gain) {
		this.is_gain = is_gain;
	}
	public Long getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Long add_date) {
		this.add_date = add_date;
	}
}
