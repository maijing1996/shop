package com.shop.model.po;

import com.shop.model.common.BaseEntity;

public class IdeaExpressCompany extends BaseEntity {

	private String code;//编码
	private String real_name;//真实公司名称
	private Integer is_use;//是否使用
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public Integer getIs_use() {
		return is_use;
	}
	public void setIs_use(Integer is_use) {
		this.is_use = is_use;
	}
}
