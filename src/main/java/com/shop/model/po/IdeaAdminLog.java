package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 管理员操作日志表
 * 偶木
 * 2018年8月22日
 */
public class IdeaAdminLog extends BaseEntity {

	private Long id;//id
	private String uid;//管理员用户名
	private String log_info;//操作内容
	private String log_ip;//操作ip
	private String log_url;//操作url
	private Integer log_type;//日志类型 0:操作日志 1:登录日志
	private Integer log_state;//日志状态 0:失败 1:成功
	private Long add_date;//添加日期
	
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
	public Integer getLog_type() {
		return log_type;
	}
	public void setLog_type(Integer log_type) {
		this.log_type = log_type;
	}
	public Integer getLog_state() {
		return log_state;
	}
	public void setLog_state(Integer log_state) {
		this.log_state = log_state;
	}
	public Long getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Long add_date) {
		this.add_date = add_date;
	}
}
