package com.shop.model.dto;

import java.util.List;

import com.shop.model.po.IdeaCoupon;

public class DistributionIdentity {
	
	private Long id;
	private String name;//分销身份名称
	private Double commission;//获取佣金的百分比
	private Double apply_amount;//身份申请金额
	private Double buy_discount;//购买商品的折扣
	private List<IdeaCoupon> list;
	private String is_sale;//是否启用  1启用  0关闭
	private String fname;//上级名称
	private Double amount;//代金券
	private Long fid;
	private Integer is_apply;
	
	public Long getFid() {
		return fid;
	}
	public void setFid(Long fid) {
		this.fid = fid;
	}
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
	public Double getBuy_discount() {
		return buy_discount;
	}
	public void setBuy_discount(Double buy_discount) {
		this.buy_discount = buy_discount;
	}
	public String getIs_sale() {
		return is_sale;
	}
	public void setIs_sale(Integer is_sale) {
		if(is_sale != null) {
			if(is_sale.equals(1)) {
				this.is_sale = "启用";
			}else {
				this.is_sale = "关闭";
			}
		} else {
			this.is_sale = null;
		}
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public List<IdeaCoupon> getList() {
		return list;
	}
	public void setList(List<IdeaCoupon> list) {
		this.list = list;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public Integer getIs_apply() {
		return is_apply;
	}
	public void setIs_apply(Integer is_apply) {
		this.is_apply = is_apply;
	}
}
