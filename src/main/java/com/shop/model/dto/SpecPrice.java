package com.shop.model.dto;

public class SpecPrice {

	private Long id;
	private Double price;
	private String sku;
	private String stock;
	private Long item_id;
	private Long spec_id;
	
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
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public Long getItem_id() {
		return item_id;
	}
	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}
	public Long getSpec_id() {
		return spec_id;
	}
	public void setSpec_id(Long spec_id) {
		this.spec_id = spec_id;
	}
}
