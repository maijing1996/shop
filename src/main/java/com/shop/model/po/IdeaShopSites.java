package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 网站设置
 * 2018年9月5日
 */
public class IdeaShopSites extends BaseEntity {
	
	private Long openid;//管理员的id
	private String title;//网站名称
	private String logo;//网站logo
	private String name;//网站联系人
	private String phone;//联系电话
	private String tel;//联系手机
	private String qq;//在线咨询qq
	private String keywords;//网站关键词
	private String description;//网站描述
	private String record;//网站备案号
	private Integer pc;//启用（1：启用 0：不启用）
	private Integer upsize;//上传大小 单位为B
	private String uptype;//上传类型
	private Integer reg_integral;//注册赠送积分
	private Integer default_stock;//默认库存
	private Integer warning_stock;//库存预警
	private Integer cash;//允许提现
	private Integer mini_cash;//最低提现额
	
	
	public Integer getPc() {
		return pc;
	}
	public void setPc(Integer pc) {
		this.pc = pc;
	}
	public Integer getUpsize() {
		return upsize;
	}
	public void setUpsize(Integer upsize) {
		this.upsize = upsize;
	}
	public String getUptype() {
		return uptype;
	}
	public void setUptype(String uptype) {
		this.uptype = uptype;
	}
	public Integer getReg_integral() {
		return reg_integral;
	}
	public void setReg_integral(Integer reg_integral) {
		this.reg_integral = reg_integral;
	}
	public Integer getDefault_stock() {
		return default_stock;
	}
	public void setDefault_stock(Integer default_stock) {
		this.default_stock = default_stock;
	}
	public Integer getWarning_stock() {
		return warning_stock;
	}
	public void setWarning_stock(Integer warning_stock) {
		this.warning_stock = warning_stock;
	}
	public Integer getCash() {
		return cash;
	}
	public void setCash(Integer cash) {
		this.cash = cash;
	}
	public Integer getMini_cash() {
		return mini_cash;
	}
	public void setMini_cash(Integer mini_cash) {
		this.mini_cash = mini_cash;
	}
	public Long getOpenid() {
		return openid;
	}
	public void setOpenid(Long openid) {
		this.openid = openid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	
	
}
