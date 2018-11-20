package com.shop.model.dto;

import com.shop.util.DateUtil;

public class AdminLog {
	private Long id;//id
	private String uid;//管理员用户名
	private String log_info;//操作内容
	private String log_ip;//操作ip
	private String log_url;//操作url
	private String log_state;//日志状态 0:失败 1:成功
	private String add_date;//添加日期
	
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
	public String getLog_info() {
		return log_info;
	}
	public void setLog_info(String log_info) {
		this.log_info = log_info;
	}
	public String getLog_ip() {
		return log_ip;
	}
	public void setLog_ip(String log_ip) {
		this.log_ip = log_ip;
	}
	public String getLog_url() {
		return log_url;
	}
	public void setLog_url(String log_url) {
		this.log_url = log_url;
	}
	public String getLog_state() {
		return log_state;
	}
	public void setLog_state(Integer log_state) {
		if(log_state == null) {
			
		} else if(log_state == 0) {
			this.log_state = "操作失败";
		} else if(log_state == 1) {
			this.log_state = "操作成功";
		}
	}
	public String getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Long add_date) {
		if(add_date != null) {
			this.add_date = DateUtil.format(add_date*1000, DateUtil.FORMAT_YYYY_MM_dd_hh_mm_ss);
		}
	}
}
