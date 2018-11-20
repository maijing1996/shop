package com.shop.model.dto;

import com.shop.util.DateUtil;

public class Paylog {
	
	private Integer id;
	private Double fee;//金额
	private String info;//说明
	private Double account_fee;//账号余额
	private String pay_state;//提现状态(-1:拒绝 0:审核 1:通过)
	private String add_date;//添加日期
	private String oauth_nickname;//第三方昵称
	
	public String getOauth_nickname() {
		return oauth_nickname;
	}
	public void setOauth_nickname(String oauth_nickname) {
		this.oauth_nickname = oauth_nickname;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Double getAccount_fee() {
		return account_fee;
	}
	public void setAccount_fee(Double account_fee) {
		this.account_fee = account_fee;
	}
	public String getPay_state() {
		return pay_state;
	}
	public void setPay_state(Integer pay_state) {
		if(pay_state == null) {
			
		} else if(pay_state == -1) {
			this.pay_state = "拒绝";
		} else if(pay_state == 1) {
			this.pay_state = "通过";
		} else if(pay_state == 0) {
			this.pay_state = "审核";
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
