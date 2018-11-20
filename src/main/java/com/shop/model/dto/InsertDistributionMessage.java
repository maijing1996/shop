package com.shop.model.dto;

public class InsertDistributionMessage {
	
	private Long id;
	private String name;//分销身份名称
	private Double commission;//获取佣金的百分比
	private Double apply_amount;//身份申请金额
	private Integer buy_discount;//购买商品的折扣
	private Double recommend_distribution_commission;//推荐分销代理奖励
	private Integer is_sale;//是否启用  1启用  0关闭
	private Long fid;//上级
	private String amount;//代金券(String类型，用来接收多个代金券)
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Double getCommission() {
		return commission;
	}
	public void setCommission(Double commission) {
		this.commission = commission;
	}
	public Double getApply_amount() {
		return apply_amount;
	}
	public void setApply_amount(Double apply_amount) {
		this.apply_amount = apply_amount;
	}
	public Integer getBuy_discount() {
		return buy_discount;
	}
	public void setBuy_discount(Integer buy_discount) {
		this.buy_discount = buy_discount;
	}
	public Double getRecommend_distribution_commission() {
		return recommend_distribution_commission;
	}
	public void setRecommend_distribution_commission(Double recommend_distribution_commission) {
		this.recommend_distribution_commission = recommend_distribution_commission;
	}
	public Integer getIs_sale() {
		return is_sale;
	}
	public void setIs_sale(Integer is_sale) {
		this.is_sale = is_sale;
	}
	public Long getFid() {
		return fid;
	}
	public void setFid(Long fid) {
		this.fid = fid;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	
}
