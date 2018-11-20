package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 分销员表
 * 偶木
 * 2018年8月22日
 */
public class IdeaDistributionCategroy extends BaseEntity {

	private String name;//分销身份名称
	private Double commission;//获取佣金的百分比
	private Long fid;//上层分销商的ID
	private Long add_time;//添加时间
	private Integer is_sale;//是否启用  1启用  0关闭
	private Double apply_amount;//身份申请金额
	private Double get_amount;//身份申请成功后获得代金券的金额
	private Integer buy_discount;//购买商品的折扣
	private Double recommend_distribution_commission;//推荐分销代理奖励
	private Integer is_apply;
	
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
	public Long getFid() {
		return fid;
	}
	public void setFid(Long fid) {
		this.fid = fid;
	}
	public Long getAdd_time() {
		return add_time;
	}
	public void setAdd_time(Long add_time) {
		this.add_time = add_time;
	}
	public Integer getIs_sale() {
		return is_sale;
	}
	public void setIs_sale(Integer is_sale) {
		this.is_sale = is_sale;
	}
	public Double getApply_amount() {
		return apply_amount;
	}
	public void setApply_amount(Double apply_amount) {
		this.apply_amount = apply_amount;
	}
	public Double getGet_amount() {
		return get_amount;
	}
	public void setGet_amount(Double get_amount) {
		this.get_amount = get_amount;
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
	public Integer getIs_apply() {
		return is_apply;
	}
	public void setIs_apply(Integer is_apply) {
		this.is_apply = is_apply;
	}
}
