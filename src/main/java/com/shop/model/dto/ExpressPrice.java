package com.shop.model.dto;

public class ExpressPrice {

	private Long id;
	private Double price;
	private Double trim_price;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getTrim_price() {
		return trim_price;
	}
	public void setTrim_price(Double trim_price) {
		this.trim_price = trim_price;
	}
}
