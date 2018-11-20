package com.shop.model.dto;

import com.shop.util.DateUtil;

public class User {

	private Long id;
	private String uid;
	private String oauth_nickname;
	private Double integral;
	private Double user_money;
	private String is_work;
	private String is_fx;
	private String lev;
	private String add_date;
	private String openid="0";
	private String xcx_openid="0";
	private String email;
	private String tel;
	private String nickname;
	
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
	public String getOauth_nickname() {
		return oauth_nickname;
	}
	public void setOauth_nickname(String oauth_nickname) {
		this.oauth_nickname = oauth_nickname;
	}
	public Double getIntegral() {
		return integral;
	}
	public void setIntegral(Double integral) {
		this.integral = integral;
	}
	public Double getUser_money() {
		return user_money;
	}
	public void setUser_money(Double user_money) {
		this.user_money = user_money;
	}
	public String getIs_work() {
		return is_work;
	}
	public void setIs_work(Integer is_work) {
		if(is_work == null) {
			
		} else if(is_work == 0) {
			this.is_work = "注销";
		} else if(is_work == 1) {
			this.is_work = "正常";
		}
	}
	public String getIs_fx() {
		return is_fx;
	}
	public void setIs_fx(Integer is_fx) {
		if(is_fx == null) {
			
		} else if(is_fx == 0) {
			this.is_fx = "否";
		} else if(is_fx == 1) {
			this.is_fx = "是";
		}
	}
	public String getLev() {
		return lev;
	}
	public void setLev(String lev) {
		this.lev = lev;
	}
	public String getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Long add_date) {
		if(add_date != null) {
			this.add_date = DateUtil.format(add_date*1000, DateUtil.FORMAT_YYYY_MM_dd_hh_mm_ss);
		}
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		if(openid != null) {
			this.openid = openid;
		}
	}
	public String getXcx_openid() {
		return xcx_openid;
	}
	public void setXcx_openid(String xcx_openid) {
		if(xcx_openid != null) {
			this.xcx_openid = xcx_openid;
		}
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
