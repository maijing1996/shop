package com.shop.model.dto;

public class GoodsSpecPrice {

	private Long id;
	private Long goods_id;//商品ID
	private String key;//规格名ID_规格项ID
	private String key_name;//规格名_规格项
	private Double price;//价格
	private String cname;
	private String goodsname;
	private String title;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(Long goods_id) {
		this.goods_id = goods_id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getKey_name() {
		return key_name;
	}
	public void setKey_name(String key_name) {
		this.key_name = key_name;
		if(key_name != null) {
			if(this.goodsname != null) {
				this.goodsname = this.goodsname + " -->" + key_name;
			} else {
				this.goodsname = key_name;
			}
		}
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getGoodsname() {
		return goodsname;
	}
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
		if(title != null) {
			if(this.goodsname != null) {
				this.goodsname = title + " -->" + this.goodsname;
			} else {
				this.goodsname = title;
			}
		}
	}
}
