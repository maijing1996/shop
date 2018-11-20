package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 用户等级表
 * 偶木
 * 2018年8月22日
 */
public class IdeaUserLevel extends BaseEntity {

	private String title;//等级名称
	private Double amount;//消费金额
	private Integer rebate;//折扣率
	private String info;//等级描述
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Integer getRebate() {
		return rebate;
	}
	public void setRebate(Integer rebate) {
		this.rebate = rebate;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
}
