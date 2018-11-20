package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 订单商品表
 * 偶木
 * 2018年8月22日
 */
public class IdeaOrderGoods extends BaseEntity {

	private Long order_id;//订单ID
	private Long goods_id;//商品ID
	private String spec_key;//规格key(暂时没用)
	private String spec_item;//规格
	private Double price;//商品价格
	private Double rebate_price;//会员折扣价
	private Integer amount;//购买数量
	private Double total_price;//订单总价
	private Integer is_comment;//评论
	private Long add_date;//购买时间
	
	public Long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
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
	public String getSpec_item() {
		return spec_item;
	}
	public void setSpec_item(String spec_item) {
		this.spec_item = spec_item;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
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
	public Integer getIs_comment() {
		return is_comment;
	}
	public void setIs_comment(Integer is_comment) {
		this.is_comment = is_comment;
	}
	public Long getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Long add_date) {
		this.add_date = add_date;
	}
}
