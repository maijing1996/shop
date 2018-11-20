package com.shop.model.po;

import com.shop.model.common.BaseEntity;

public class IdeaDistributionCategroyPrice extends BaseEntity {

	private Long this_id;
	private Long target_id;
	private Long add_time;
	private Double price;
	private String target_name;
	
	public Long getThis_id() {
		return this_id;
	}
	public void setThis_id(Long this_id) {
		this.this_id = this_id;
	}
	public Long getTarget_id() {
		return target_id;
	}
	public void setTarget_id(Long target_id) {
		this.target_id = target_id;
	}
	public Long getAdd_time() {
		return add_time;
	}
	public void setAdd_time(Long add_time) {
		this.add_time = add_time;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getTarget_name() {
		return target_name;
	}
	public void setTarget_name(String target_name) {
		this.target_name = target_name;
	}
}
