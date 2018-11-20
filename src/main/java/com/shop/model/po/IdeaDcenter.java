package com.shop.model.po;

import com.shop.model.common.BaseEntity;

public class IdeaDcenter extends BaseEntity {
	
	private String name;
	private Long user_id;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
}
