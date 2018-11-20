package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 交易设置
 * 2018年9月5日
 */
public class IdeaShopTransaction extends BaseEntity {
	
	private Double full_cut;//满多少免邮(订单满这么多后免邮)
	private Integer unpaid;//下单未支付	
	private Integer shipments;//发货多少天自动收货
	private Integer stock_reduce_time;//减库存时间
	private Integer integral_exchange;//积分兑换(1:开启 0:关闭)
	private Double integral_exchange_proportion;//积分兑换比例
	private Integer mini_integral;//最低使用积分
	private Double use_ratio_integral;//积分使用比例
	
	public Double getFull_cut() {
		return full_cut;
	}
	public void setFull_cut(Double full_cut) {
		this.full_cut = full_cut;
	}
	public Integer getUnpaid() {
		return unpaid;
	}
	public void setUnpaid(Integer unpaid) {
		this.unpaid = unpaid;
	}
	public Integer getShipments() {
		return shipments;
	}
	public void setShipments(Integer shipments) {
		this.shipments = shipments;
	}
	public Integer getStock_reduce_time() {
		return stock_reduce_time;
	}
	public void setStock_reduce_time(Integer stock_reduce_time) {
		this.stock_reduce_time = stock_reduce_time;
	}
	public Integer getIntegral_exchange() {
		return integral_exchange;
	}
	public void setIntegral_exchange(Integer integral_exchange) {
		this.integral_exchange = integral_exchange;
	}
	public Double getIntegral_exchange_proportion() {
		return integral_exchange_proportion;
	}
	public void setIntegral_exchange_proportion(Double integral_exchange_proportion) {
		this.integral_exchange_proportion = integral_exchange_proportion;
	}
	public Integer getMini_integral() {
		return mini_integral;
	}
	public void setMini_integral(Integer mini_integral) {
		this.mini_integral = mini_integral;
	}
	public Double getUse_ratio_integral() {
		return use_ratio_integral;
	}
	public void setUse_ratio_integral(Double use_ratio_integral) {
		this.use_ratio_integral = use_ratio_integral;
	}
}
