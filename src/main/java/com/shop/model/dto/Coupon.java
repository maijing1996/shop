package com.shop.model.dto;

import com.shop.util.DateUtil;

public class Coupon {

	private Long id;
	private String title;//名称
	private String type;//类型类型(0:下单赠送 1:指定发放 2:免费领取 3:线下发放，4、代金券，5新人优惠券
	private Double yh_price;//优惠金额
	private Double min_price;//优惠条件
	private Integer amount;//发放数量
	private Long send_bdate1;//开始发放时间
	private String send_bdate;//开始发放时间
	private String send_edate;//结束发放时间
	private String use_bdate;//开始使用时间
	private String use_edate;//结束使用时间
	private Integer use_amount;//使用
	private Integer validity;
	private Integer gain;//领取
	
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
	public String getType() {
		return type;
	}
	public void setType(Integer type) {
		if(type == null) {
		} else if(type == 0) {
			this.type = "下单赠送";
		} else if(type == 1) {
			this.type = "指定发放";
		} else if(type == 2) {
			this.type = "免费领取 ";
		} else if(type == 3) {
			this.type = "线下发放";
		} else if(type == 4) {
			this.type = "分销获得";
		} else if(type == 5) {
			this.type = "新人礼券";
		}
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
	public String getSend_bdate() {
		return send_bdate;
	}
	public void setSend_bdate(Long send_bdate) {
		if(send_bdate != null) {
			this.send_bdate1 = send_bdate;
			this.send_bdate = DateUtil.format(send_bdate*1000, DateUtil.FORMAT_YYYY_MM_dd_hh_mm_ss);
		}
	}
	public String getSend_edate() {
		return send_edate;
	}
	public void setSend_edate(Long send_edate) {
		if(send_edate != null) {
			this.send_edate = DateUtil.format(send_edate*1000, DateUtil.FORMAT_YYYY_MM_dd_hh_mm_ss);
		}
	}
	public String getUse_bdate() {
		return use_bdate;
	}
	public void setUse_bdate(Long use_bdate) {
		if(use_bdate != null) {
			this.use_bdate = DateUtil.format(use_bdate*1000, DateUtil.FORMAT_YYYY_MM_dd_hh_mm_ss);
		}
	}
	public String getUse_edate() {
		return use_edate;
	}
	public void setUse_edate(Long use_edate) {
		if(use_edate != null) {
			this.use_edate = DateUtil.format(use_edate*1000, DateUtil.FORMAT_YYYY_MM_dd_hh_mm_ss);
		}
	}
	public Integer getUse_amount() {
		return use_amount;
	}
	public void setUse_amount(Integer use_amount) {
		this.use_amount = use_amount;
	}
	public Integer getGain() {
		return gain;
	}
	public void setGain(Integer gain) {
		this.gain = gain;
	}
	public Integer getValidity() {
		return validity;
	}
	public void setValidity(Integer validity) {
		this.validity = validity;
	}
	public Long getSend_bdate1() {
		return send_bdate1;
	}
	public void setSend_bdate1(Long send_bdate1) {
		this.send_bdate1 = send_bdate1;
	}
}
