package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 收据表
 * 偶木
 * 2018年8月22日
 */
public class IdeaVoucher extends BaseEntity {

	private String name;//名称
	private Integer is_sale;//是否启用
	private Double amount;//金额
	private Long add_time;//添加时间
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIs_sale() {
		return is_sale;
	}
	public void setIs_sale(Integer is_sale) {
		this.is_sale = is_sale;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Long getAdd_time() {
		return add_time;
	}
	public void setAdd_time(Long add_time) {
		this.add_time = add_time;
	}
}
