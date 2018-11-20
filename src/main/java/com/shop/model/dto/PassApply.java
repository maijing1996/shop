package com.shop.model.dto;

import com.shop.util.DateUtil;

public class PassApply {
	private Long id;//
	private String oauth_nickname;//昵称
	private String uid;//用户名
	private String mobile;//手机号
	private String real_name;//真是姓名
	private String distribution_apply_add_time;//分销商身份申请时间
	private String name;//报单中心
	private String note;//备注
	private Integer distribution_lev;//分销商等级  如果为0 则还没开通  如果为负数 则为拒绝
	private String distribution_pass_add_time;//分销商身份通过时间
	private String distribution_refunse_add_time;//申请被拒绝的时间
	
	
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
	public Integer getDistribution_lev() {
		return distribution_lev;
	}
	public void setDistribution_lev(Integer distribution_lev) {
		this.distribution_lev = distribution_lev;
	}
	public String getDistribution_pass_add_time() {
		return distribution_pass_add_time;
	}
	public void setDistribution_pass_add_time(Long distribution_pass_add_time) {
		if(distribution_pass_add_time != null) {
			this.distribution_pass_add_time = DateUtil.format(distribution_pass_add_time*1000, DateUtil.FORMAT_YYYY_MM_dd_hh_mm_ss);
		}else {
			this.distribution_pass_add_time = null;
		}
	}
	public String getDistribution_refunse_add_time() {
		return distribution_refunse_add_time;
	}
	public void setDistribution_refunse_add_time(Long distribution_refunse_add_time) {
		if(distribution_refunse_add_time != null) {
			this.distribution_refunse_add_time = DateUtil.format(distribution_refunse_add_time*1000, DateUtil.FORMAT_YYYY_MM_dd_hh_mm_ss);
		}else {
			this.distribution_refunse_add_time = null;
		}
	}
	public String getOauth_nickname() {
		return oauth_nickname;
	}
	public void setOauth_nickname(String oauth_nickname) {
		this.oauth_nickname = oauth_nickname;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getDistribution_apply_add_time() {
		return distribution_apply_add_time;
	}
	public void setDistribution_apply_add_time(Long distribution_apply_add_time) {
		if(distribution_apply_add_time != null) {
			this.distribution_apply_add_time = DateUtil.format(distribution_apply_add_time*1000, DateUtil.FORMAT_YYYY_MM_dd_hh_mm_ss);
		}else {
			this.distribution_apply_add_time = null;
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
