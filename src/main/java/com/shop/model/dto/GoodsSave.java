package com.shop.model.dto;

import java.util.List;

public class GoodsSave {

	//基本信息
	private Long id;
	private String SPU;
	private String SKU;
	private String title;
	private String subtitle;
	private Long cat_id;
	private Long shop_id;
	private Long brand_id;
	private String pic;
	private Double markey_price;
	private Double price;
	private Double cost_price;
	private Integer stock;
	private Integer weight;
	private String info;

	//商品相册
	private String slide;
	//商品规格
	private Long specId;
	private String spec;
	//选填信息
	private Integer tc;
	private Integer jf;
	private Integer initial_sales;
	private Integer other_info;
	private Integer gg_info;
	private Integer is_top;
	private Integer is_free;
	private Integer is_new;
	private Integer is_hot;
	private Integer is_sale;
	private Integer is_jf;
	private Integer keywords;
	private Integer description;
	private Integer hits;
	private Integer sequence;
	//其他信息
	private Long add_date;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSPU() {
		return SPU;
	}
	public void setSPU(String sPU) {
		SPU = sPU;
	}
	public String getSKU() {
		return SKU;
	}
	public void setSKU(String sKU) {
		SKU = sKU;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public Long getCat_id() {
		return cat_id;
	}
	public void setCat_id(Long cat_id) {
		this.cat_id = cat_id;
	}
	public Long getShop_id() {
		return shop_id;
	}
	public void setShop_id(Long shop_id) {
		this.shop_id = shop_id;
	}
	public Long getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(Long brand_id) {
		this.brand_id = brand_id;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public Double getMarkey_price() {
		return markey_price;
	}
	public void setMarkey_price(Double markey_price) {
		this.markey_price = markey_price;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getCost_price() {
		return cost_price;
	}
	public void setCost_price(Double cost_price) {
		this.cost_price = cost_price;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getSlide() {
		return slide;
	}
	public void setSlide(String slide) {
		this.slide = slide;
	}
	public Long getSpecId() {
		return specId;
	}
	public void setSpecId(Long specId) {
		this.specId = specId;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public Integer getTc() {
		return tc;
	}
	public void setTc(Integer tc) {
		this.tc = tc;
	}
	public Integer getJf() {
		return jf;
	}
	public void setJf(Integer jf) {
		this.jf = jf;
	}
	public Integer getInitial_sales() {
		return initial_sales;
	}
	public void setInitial_sales(Integer initial_sales) {
		this.initial_sales = initial_sales;
	}
	public Integer getOther_info() {
		return other_info;
	}
	public void setOther_info(Integer other_info) {
		this.other_info = other_info;
	}
	public Integer getGg_info() {
		return gg_info;
	}
	public void setGg_info(Integer gg_info) {
		this.gg_info = gg_info;
	}
	public Integer getIs_top() {
		return is_top;
	}
	public void setIs_top(Integer is_top) {
		this.is_top = is_top;
	}
	public Integer getIs_free() {
		return is_free;
	}
	public void setIs_free(Integer is_free) {
		this.is_free = is_free;
	}
	public Integer getIs_new() {
		return is_new;
	}
	public void setIs_new(Integer is_new) {
		this.is_new = is_new;
	}
	public Integer getIs_hot() {
		return is_hot;
	}
	public void setIs_hot(Integer is_hot) {
		this.is_hot = is_hot;
	}
	public Integer getIs_sale() {
		return is_sale;
	}
	public void setIs_sale(Integer is_sale) {
		this.is_sale = is_sale;
	}
	public Integer getIs_jf() {
		return is_jf;
	}
	public void setIs_jf(Integer is_jf) {
		this.is_jf = is_jf;
	}
	public Integer getKeywords() {
		return keywords;
	}
	public void setKeywords(Integer keywords) {
		this.keywords = keywords;
	}
	public Integer getDescription() {
		return description;
	}
	public void setDescription(Integer description) {
		this.description = description;
	}
	public Integer getHits() {
		return hits;
	}
	public void setHits(Integer hits) {
		this.hits = hits;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public Long getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Long add_date) {
		this.add_date = add_date;
	}
	
}
