package com.shop.model.dto;

import com.shop.util.DateUtil;

public class SellDetail {

	private Long id;
	private Integer ranking;
	private String sn;
	private Double price;
	private String add_date;
	private Integer amount;
	private String title;
	private Double ave;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getRanking() {
		return ranking;
	}
	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
	public void setAdd_date(String add_date) {
		this.add_date = add_date;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Long add_date) {
		if(add_date != null) {
			this.add_date = DateUtil.format(add_date*1000, DateUtil.FORMAT_YYYY_MM_dd_hh_mm_ss);
		}
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getAve() {
		return ave;
	}
	public void setAve(Double ave) {
		this.ave = ave;
	}
}
