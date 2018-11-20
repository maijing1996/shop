package com.shop.model.po;

import javax.persistence.Transient;

import com.shop.model.common.BaseEntity;

public class IdeaCashGoods extends BaseEntity {

	private Long user_id;
	private Long goods_id;
	private Integer amount;
	private Integer integral;
	private Double price;
	private String address;
	private Double express_price;
	private String express_title;
	private String express_sn;
	private Long express_date;
	private Long add_data;
	
	@Transient
	private String nickname;
	@Transient
	private String goods_name;
	
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Long getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(Long goods_id) {
		this.goods_id = goods_id;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getExpress_price() {
		return express_price;
	}
	public void setExpress_price(Double express_price) {
		this.express_price = express_price;
	}
	public String getExpress_title() {
		return express_title;
	}
	public void setExpress_title(String express_title) {
		this.express_title = express_title;
	}
	public String getExpress_sn() {
		return express_sn;
	}
	public void setExpress_sn(String express_sn) {
		this.express_sn = express_sn;
	}
	public Long getExpress_date() {
		return express_date;
	}
	public void setExpress_date(Long express_date) {
		this.express_date = express_date;
	}
	public Long getAdd_data() {
		return add_data;
	}
	public void setAdd_data(Long add_data) {
		this.add_data = add_data;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
}
