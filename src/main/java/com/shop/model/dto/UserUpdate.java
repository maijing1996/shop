package com.shop.model.dto;

public class UserUpdate {

	private Long id;
	private String passwd;
	private String nickname;
	private Integer integral;
	private Double user_money;
	private Integer is_work;
	private Integer is_fx;
	private String phone;
	private String email;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public Double getUser_money() {
		return user_money;
	}
	public void setUser_money(Double user_money) {
		this.user_money = user_money;
	}
	public Integer getIs_work() {
		return is_work;
	}
	public void setIs_work(Integer is_work) {
		this.is_work = is_work;
	}
	public Integer getIs_fx() {
		return is_fx;
	}
	public void setIs_fx(Integer is_fx) {
		this.is_fx = is_fx;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
