package com.shop.model.po;

import javax.persistence.Transient;

import com.shop.model.common.BaseEntity;
import com.shop.model.dto.SpecPrice;

/**
 * 规格详细表
 * 偶木
 * 2018年8月22日
 */
public class IdeaGoodsSpecItem extends BaseEntity {

	private Long spec_id;//规格名ID
	private String title;//规格项名称
	
	@Transient
	private String ftitle;
	@Transient
	private String res;
	@Transient
	private SpecPrice price;
	
	public Long getSpec_id() {
		return spec_id;
	}
	public void setSpec_id(Long spec_id) {
		this.spec_id = spec_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFtitle() {
		return ftitle;
	}
	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
	}
	public String getRes() {
		return res;
	}
	public void setRes(String res) {
		this.res = res;
	}
	public SpecPrice getPrice() {
		return price;
	}
	public void setPrice(SpecPrice price) {
		this.price = price;
	}
}
