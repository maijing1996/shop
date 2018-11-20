package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 限时抢购
 * 偶木
 * 2018年8月22日
 */
public class IdeaFlashSale extends BaseEntity {

	private String title;//标题
	private Integer goods_id;//商品ID
	private String spec_key;//规格key
	private String spec_name;//规格名称
	private Double price;//价格
	private Integer amount;//数量
	private Integer per_amount;//每人限购数量
	private Integer sales;//已售数量
	private Integer sale_bdate;//开始时间
	private Integer sale_edate;//结束时间
	private Integer sequence;//排序
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(Integer goods_id) {
		this.goods_id = goods_id;
	}
	public String getSpec_key() {
		return spec_key;
	}
	public void setSpec_key(String spec_key) {
		this.spec_key = spec_key;
	}
	public String getSpec_name() {
		return spec_name;
	}
	public void setSpec_name(String spec_name) {
		this.spec_name = spec_name;
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
	public Integer getPer_amount() {
		return per_amount;
	}
	public void setPer_amount(Integer per_amount) {
		this.per_amount = per_amount;
	}
	public Integer getSales() {
		return sales;
	}
	public void setSales(Integer sales) {
		this.sales = sales;
	}
	public Integer getSale_bdate() {
		return sale_bdate;
	}
	public void setSale_bdate(Integer sale_bdate) {
		this.sale_bdate = sale_bdate;
	}
	public Integer getSale_edate() {
		return sale_edate;
	}
	public void setSale_edate(Integer sale_edate) {
		this.sale_edate = sale_edate;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
}
