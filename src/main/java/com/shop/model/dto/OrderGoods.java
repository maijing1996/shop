package com.shop.model.dto;

public class OrderGoods {

	private Double price;//商品价格
	private String sn;//商品ID
	private String title;//商品名称
	private String spec_item;//规格
	private Double rebate_price;//会员折扣价
	private Integer amount;//购买数量
	private Double total_price;//商品总价
	
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSpec_item() {
		return spec_item;
	}
	public void setSpec_item(String spec_item) {
		this.spec_item = spec_item;
	}
	public Double getRebate_price() {
		return rebate_price;
	}
	public void setRebate_price(Double rebate_price) {
		this.rebate_price = rebate_price;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Double getTotal_price() {
		return total_price;
	}
	public void setTotal_price(Double total_price) {
		this.total_price = total_price;
	}
}
