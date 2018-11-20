package com.shop.model.po;

import com.shop.model.common.BaseEntity;

public class IdeaPickUp extends BaseEntity {

	private String pickup_addr;
	private Integer is_pickup;
	private String pickup_name;
	private String pickup_tel;
	
	public String getPickup_addr() {
		return pickup_addr;
	}
	public void setPickup_addr(String pickup_addr) {
		this.pickup_addr = pickup_addr;
	}
	public Integer getIs_pickup() {
		return is_pickup;
	}
	public void setIs_pickup(Integer is_pickup) {
		this.is_pickup = is_pickup;
	}
	public String getPickup_name() {
		return pickup_name;
	}
	public void setPickup_name(String pickup_name) {
		this.pickup_name = pickup_name;
	}
	public String getPickup_tel() {
		return pickup_tel;
	}
	public void setPickup_tel(String pickup_tel) {
		this.pickup_tel = pickup_tel;
	}
}
