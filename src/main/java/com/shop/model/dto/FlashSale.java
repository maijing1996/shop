package com.shop.model.dto;

import com.shop.util.DateUtil;

public class FlashSale {

	private Long id;
	private String title;//标题
	private String goods_name;//商品ID
	private Double price;//价格
	private Integer amount;//数量
	private Integer per_amount;//每人限购数量
	private Integer sales;//已售数量
	private String sale_bdate;//开始时间
	private String sale_edate;//结束时间
	private Integer sequence;//排序
	private String spec_name;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
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
	public String getSale_bdate() {
		return sale_bdate;
	}
	public void setSale_bdate(Long sale_bdate) {
		if(sale_bdate != null) {
			this.sale_bdate = DateUtil.format(sale_bdate*1000, DateUtil.FORMAT_YYYY_MM_dd_hh_mm_ss);
		}
	}
	public String getSale_edate() {
		return sale_edate;
	}
	public void setSale_edate(Long sale_edate) {
		if(sale_edate != null) {
			this.sale_edate = DateUtil.format(sale_edate*1000, DateUtil.FORMAT_YYYY_MM_dd_hh_mm_ss);
		}
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public String getSpec_name() {
		return spec_name;
	}
	public void setSpec_name(String spec_name) {
		this.spec_name = spec_name;
	}
}
