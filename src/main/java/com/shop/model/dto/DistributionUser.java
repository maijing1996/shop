package com.shop.model.dto;

import com.shop.util.DateUtil;

public class DistributionUser {
	
	private Long id;
	private String uid;//用户名
	private Integer integral;//积分
	private Double user_money;//余额
	private String oauth_nickname;//第三方昵称
	private String is_work;//激活
	private String name;//分销商身份
	private Integer distribution_lev;//分销商等级  如果为0 则还没开通  如果为负数 则为拒绝
	private String distribution_apply_add_time;//分销商身份申请时间
	private String distribution_pass_add_time;//分销商身份通过时间
	private String distribution_refunse_add_time;//申请被拒绝的时间
	private String pass_note;//备注
	private String mobile;//手机号
	private Long dcenter;//报单中心id
	private String real_name;//真实姓名
	private String dcenter_name;
	private Integer is_fx;
	private Integer is_apply;
	
	public String getPass_note() {
		return pass_note;
	}
	public void setPass_note(String pass_note) {
		this.pass_note = pass_note;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Long getDcenter() {
		return dcenter;
	}
	public void setDcenter(Long dcenter) {
		this.dcenter = dcenter;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
	public String getOauth_nickname() {
		return oauth_nickname;
	}
	public void setOauth_nickname(String oauth_nickname) {
		this.oauth_nickname = oauth_nickname;
	}
	public String getIs_work() {
		return is_work;
	}
	public void setIs_work(Integer is_work) {
		if(is_work == null) {
			
		} else if(is_work == 1) {
			this.is_work = "正常";
		} else {
			this.is_work = "锁定";
		}
	}
	public Integer getDistribution_lev() {
		return distribution_lev;
	}
	public void setDistribution_lev(Integer distribution_lev) {
		this.distribution_lev = distribution_lev;
	}
	public String getDistribution_apply_add_time() {
		return distribution_apply_add_time;
	}
	public void setDistribution_apply_add_time(Long distribution_apply_add_time) {
		if(distribution_apply_add_time != null) {
			this.distribution_apply_add_time = DateUtil.format(distribution_apply_add_time*1000, DateUtil.FORMAT_YYYY_MM_dd_hh_mm_ss);
		}
	}
	public String getDistribution_pass_add_time() {
		return distribution_pass_add_time;
	}
	public void setDistribution_pass_add_time(Long distribution_pass_add_time) {
		if(distribution_pass_add_time != null) {
			this.distribution_pass_add_time = DateUtil.format(distribution_pass_add_time*1000, DateUtil.FORMAT_YYYY_MM_dd_hh_mm_ss);
		}
	}
	public String getDistribution_refunse_add_time() {
		return distribution_refunse_add_time;
	}
	public void setDistribution_refunse_add_time(Long distribution_refunse_add_time) {
		if(distribution_refunse_add_time != null) {
			this.distribution_refunse_add_time = DateUtil.format(distribution_refunse_add_time*1000, DateUtil.FORMAT_YYYY_MM_dd_hh_mm_ss);
		}
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getDcenter_name() {
		return dcenter_name;
	}
	public void setDcenter_name(String dcenter_name) {
		this.dcenter_name = dcenter_name;
	}
	public Integer getIs_fx() {
		return is_fx;
	}
	public void setIs_fx(Integer is_fx) {
		this.is_fx = is_fx;
	}
	public Integer getIs_apply() {
		return is_apply;
	}
	public void setIs_apply(Integer is_apply) {
		this.is_apply = is_apply;
	}
}
