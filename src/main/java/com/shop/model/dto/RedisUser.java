package com.shop.model.dto;

import java.io.Serializable;

public class RedisUser implements Serializable {

	private Long id;
	private String uid;
	private Long role_id;
	private Integer is_work;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Long getRole_id() {
		return role_id;
	}
	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}
	public Integer getIs_work() {
		return is_work;
	}
	public void setIs_work(Integer is_work) {
		this.is_work = is_work;
	}
}
