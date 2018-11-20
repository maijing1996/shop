package com.shop.model.dto;

import com.shop.util.DateUtil;

public class Manager {

	private Long id;
	private String uid;
	private Long role_id;
	private String is_work;
	private String last_login_time;//最近登录时间
	private String last_login_ip;//最近登录Ip
	private String role_name;
	
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
		if(role_id == 0) {
			this.role_name = "超级管理员";
		}
	}
	public String getIs_work() {
		return is_work;
	}
	public void setIs_work(Integer is_work) {
		if(is_work != null) {
			this.is_work = "正常";
		}else {
			this.is_work = "异常";
		}
	}
	public String getLast_login_time() {
		return last_login_time;
	}
	public void setLast_login_time(Long last_login_time) {
		if(last_login_time != null) {
			this.last_login_time = DateUtil.format(last_login_time*1000, DateUtil.FORMAT_YYYY_MM_dd_hh_mm_ss);
		}
	}
	public String getLast_login_ip() {
		return last_login_ip;
	}
	public void setLast_login_ip(String last_login_ip) {
		this.last_login_ip = last_login_ip;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
}
