package com.shop.model.dto;

import com.shop.util.DateUtil;

public class TradeDetails {

	private Integer id;
	private String type;//类型(1:加余额2:减余额3:加积分4:减积分5:加佣金6:减佣金)
	private Double fee;//金额
	private String info;//说明
	private Double account_fee;//账号余额
	private String add_date;//添加日期
	private String oauth_nickname;//第三方昵称
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(Integer type) {
		if(type == null) {
			
		} else if(type == 1) {
			this.type = "增加余额";
		} else if(type == 2) {
			this.type = "减少余额";
		} else if(type == 3) {
			this.type = "增加积分";
		} else if(type == 4) {
			this.type = "减少积分";
		}
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
	public String getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Long add_date) {
		if(add_date != null) {
			this.add_date = DateUtil.format(add_date*1000, DateUtil.FORMAT_YYYY_MM_dd_hh_mm_ss);
		}
	}
	public String getOauth_nickname() {
		return oauth_nickname;
	}
	public void setOauth_nickname(String oauth_nickname) {
		this.oauth_nickname = oauth_nickname;
	}
}
