package com.shop.model.dto;

import java.util.List;

import com.shop.util.DateUtil;

public class Order {

	private Long id;
	private Long order_sn;//订单号
	private String name;//收货人
	private Double price;//订单金额
	private Double pay_price;//实付金额
	private Integer pay_type;//支付方式 0:未选择 1:微信支付 2:支付宝 3:余额支付
	private Integer order_state;//订单状态 0:待付款 1:待发货 2:待收货 3:已完成
	private String add_date;//添加日期
	private String pay_date;//支付时间
	private String nickname;//会员昵称
	private Long user_id;//会员Id
	
	//private String name;//收货人
	private Integer send_type;//0:快递 1:自提
	private String info;//订单备注
	private String tel;//电话
	private String address;//收货地址
	
	private Double express_price;//运费
	private Double coupon_price;//优惠券金额
	private Double discount_price;//订单优惠
	private Double jf_price;//积分抵扣
	private Double voucher_price;//代金券金额
	private Double trim_price;//调整订单价格(正数为加，负数为减)
	//private Double pay_price;//实付金额
	//private Double price;//订单金额
	
	//而外字段
	private String type_name;
	private String state_name;
	private String send_name;
	List<OrderGoods> list;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrder_sn() {
		return order_sn;
	}
	public void setOrder_sn(Long order_sn) {
		this.order_sn = order_sn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getPay_price() {
		return pay_price;
	}
	public void setPay_price(Double pay_price) {
		this.pay_price = pay_price;
	}
	public Integer getPay_type() {
		return pay_type;
	}
	public void setPay_type(Integer pay_type) {
		this.pay_type = pay_type;
		if(pay_type == null) {
			
		} else if(pay_type == 1) {
			this.type_name = "微信支付";
		} else if(pay_type == 2) {
			this.type_name = "支付宝";
		} else if(pay_type == 3) {
			this.type_name = "余额支付";
		} else {
			this.type_name = "未选择";
		}
	}
	public Integer getOrder_state() {
		return order_state;
	}
	public void setOrder_state(Integer order_state) {
		this.order_state = order_state;
		if(order_state == null) {
			
		} else if(order_state == 0) {
			this.state_name = "等待付款";
		} else if(order_state == 1) {
			this.state_name = "等待发货";
		} else if(order_state == 2) {
			this.state_name = "等待收货";
		} else if(order_state == 3) {
			this.state_name = "交易完成";
		} else {
			this.state_name = "取消订单";
		}
	}
	public String getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Long add_date) {
		if(add_date != null) {
			this.add_date = DateUtil.format(add_date*1000, DateUtil.FORMAT_YYYY_MM_dd_hh_mm_ss);
		}
	}
	public Integer getSend_type() {
		return send_type;
	}
	public void setSend_type(Integer send_type) {
		this.send_type = send_type;
		if(send_type == null) {
			
		} else if(send_type == 0) {
			this.send_name ="快递";
		} else {
			this.send_name ="自提";
		}
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
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
	public Double getCoupon_price() {
		return coupon_price;
	}
	public void setCoupon_price(Double coupon_price) {
		this.coupon_price = coupon_price;
	}
	public Double getDiscount_price() {
		return discount_price;
	}
	public void setDiscount_price(Double discount_price) {
		this.discount_price = discount_price;
	}
	public Double getJf_price() {
		return jf_price;
	}
	public void setJf_price(Double jf_price) {
		this.jf_price = jf_price;
	}
	public Double getVoucher_price() {
		return voucher_price;
	}
	public void setVoucher_price(Double voucher_price) {
		this.voucher_price = voucher_price;
	}
	public Double getTrim_price() {
		return trim_price;
	}
	public void setTrim_price(Double trim_price) {
		this.trim_price = trim_price;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public String getState_name() {
		return state_name;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	public List<OrderGoods> getList() {
		return list;
	}
	public void setList(List<OrderGoods> list) {
		this.list = list;
	}
	public String getPay_date() {
		return pay_date;
	}
	public void setPay_date(Long pay_date) {
		if(pay_date != null) {
			this.pay_date = DateUtil.format(pay_date*1000, DateUtil.FORMAT_YYYY_MM_dd_hh_mm_ss);
		}
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getSend_name() {
		return send_name;
	}
	public void setSend_name(String send_name) {
		this.send_name = send_name;
	}
}
