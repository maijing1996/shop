package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 用户提现支付记录表
 * 偶木
 * 2018年8月22日
 */
public class IdeaUserPaylog extends BaseEntity {

	private Long user_id;//用户ID
	private Integer type;//类型(1:加余额2:减余额3:加积分4:减积分5:加分销佣金6:加推荐金7:用户提现)
	private Double fee;//金额
	private Integer pay_type;//付款方式(1:微信 2:支付宝3：余额)
	private String info;//说明
	private Double account_fee;//账号余额
	private Integer cash_type;//提现类型(0:到余额 1:到微信 2:到支付宝 3:到银行)
	private String cash_info;//收款账号
	private Integer pay_state;//完成状态(-1:拒绝/未完成 0:审核 1:通过/完成)
	private Long add_date;//添加日期
	private Integer state;//是否已经完成
	private Long order_id;//订单Id
	
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public Integer getPay_type() {
		return pay_type;
	}
	public void setPay_type(Integer pay_type) {
		this.pay_type = pay_type;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Double getAccount_fee() {
		return account_fee;
	}
	public void setAccount_fee(Double account_fee) {
		this.account_fee = account_fee;
	}
	public Integer getCash_type() {
		return cash_type;
	}
	public void setCash_type(Integer cash_type) {
		this.cash_type = cash_type;
	}
	public String getCash_info() {
		return cash_info;
	}
	public void setCash_info(String cash_info) {
		this.cash_info = cash_info;
	}
	public Integer getPay_state() {
		return pay_state;
	}
	public void setPay_state(Integer pay_state) {
		this.pay_state = pay_state;
	}
	public Long getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Long add_date) {
		this.add_date = add_date;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}
}
