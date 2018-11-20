package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 管理员表
 * 偶木
 * 2018年8月22日
 */
public class IdeaAdmin extends BaseEntity {

	private String uid;//用户名，数字，字母
	private String pwd;//密码，数字，字母
	private String salt;//秘钥，加盐值
	private Long role_id;//角色Id
	private Integer is_work;//是否激活
	private Long last_login_time;//最近登录时间
	private String last_login_ip;//最近登录Ip
	private Long login_times;//登录次数
	private Long add_date;//注册日期
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
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
	public Long getLast_login_time() {
		return last_login_time;
	}
	public void setLast_login_time(Long last_login_time) {
		this.last_login_time = last_login_time;
	}
	public String getLast_login_ip() {
		return last_login_ip;
	}
	public void setLast_login_ip(String last_login_ip) {
		this.last_login_ip = last_login_ip;
	}
	public Long getLogin_times() {
		return login_times;
	}
	public void setLogin_times(Long login_times) {
		this.login_times = login_times;
	}
	public Long getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Long add_date) {
		this.add_date = add_date;
	}
}
