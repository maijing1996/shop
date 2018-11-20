package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 管理员角色表
 * 偶木
 * 2018年8月22日
 */
public class IdeaAdminRole extends BaseEntity {

	private String title;//角色名称
	private String info;//角色描述
	private String power;//角色权限
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
}
