package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 优惠券表
 * 偶木
 * 2018年8月22日
 */
public class IdeaCoupon extends BaseEntity {

	private String title;//名称
	private Integer type;//类型(0:下单赠送 1:指定发放 2:免费领取 3:线下发放)
	private Double yh_price;//优惠金额
	private Double min_price;//优惠条件
	private Integer amount;//发放数量
	private Long send_bdate;//开始发放时间
	private Long send_edate;//结束发放时间
	private Long use_bdate;//开始使用时间
	private Long use_edate;//结束使用时间
	private Integer number;//
	private Integer validity;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Double getYh_price() {
		return yh_price;
	}
	public void setYh_price(Double yh_price) {
		this.yh_price = yh_price;
	}
	public Double getMin_price() {
		return min_price;
	}
	public void setMin_price(Double min_price) {
		this.min_price = min_price;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Long getSend_bdate() {
		return send_bdate;
	}
	public void setSend_bdate(Long send_bdate) {
		this.send_bdate = send_bdate;
	}
	public Long getSend_edate() {
		return send_edate;
	}
	public void setSend_edate(Long send_edate) {
		this.send_edate = send_edate;
	}
	public Long getUse_bdate() {
		return use_bdate;
	}
	public void setUse_bdate(Long use_bdate) {
		this.use_bdate = use_bdate;
	}
	public Long getUse_edate() {
		return use_edate;
	}
	public void setUse_edate(Long use_edate) {
		this.use_edate = use_edate;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getValidity() {
		return validity;
	}
	public void setValidity(Integer validity) {
		this.validity = validity;
	}
}
