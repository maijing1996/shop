package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 物流表
 * 偶木
 * 2018年8月22日
 */
public class IdeaCart extends BaseEntity {

	private Long user_id;//用户ID
	private String session_id;//临时用户ID
	private Long goods_id;//商品ID
	private String spec_key;//规格项key
	private String spec_key_name;//规格项名称
	private Long shop_id;//店铺ID
	private Double price;//单价
	private Integer amount;//数量
	private Long add_date;//添加时间
	
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	public Long getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(Long goods_id) {
		this.goods_id = goods_id;
	}
	public String getSpec_key() {
		return spec_key;
	}
	public void setSpec_key(String spec_key) {
		this.spec_key = spec_key;
	}
	public String getSpec_key_name() {
		return spec_key_name;
	}
	public void setSpec_key_name(String spec_key_name) {
		this.spec_key_name = spec_key_name;
	}
	public Long getShop_id() {
		return shop_id;
	}
	public void setShop_id(Long shop_id) {
		this.shop_id = shop_id;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Long getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Long add_date) {
		this.add_date = add_date;
	}
}
