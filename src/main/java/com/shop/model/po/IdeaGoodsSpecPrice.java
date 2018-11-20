package com.shop.model.po;

import javax.persistence.Transient;

import com.shop.model.common.BaseEntity;

/**
 * 商品规格价目表
 * 偶木
 * 2018年8月22日
 */
public class IdeaGoodsSpecPrice extends BaseEntity {
	
	private Long goods_id;//商品ID
	private String mkey;//规格名ID_规格项ID
	private String key_name;//规格名_规格项
	private Double price;//价格
	private Integer stock;//库存
	private Integer sales;//销量
	private String sku;//类型号(条形码)
	
	@Transient
	private String key;
	
	public Long getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(Long goods_id) {
		this.goods_id = goods_id;
	}
	public String getMkey() {
		return mkey;
	}
	public void setMkey(String mkey) {
		this.mkey = mkey;
	}
	public String getKey_name() {
		return key_name;
	}
	public void setKey_name(String key_name) {
		this.key_name = key_name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Integer getSales() {
		return sales;
	}
	public void setSales(Integer sales) {
		this.sales = sales;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
