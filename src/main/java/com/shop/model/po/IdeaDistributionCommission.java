package com.shop.model.po;

import com.shop.model.common.BaseEntity;

public class IdeaDistributionCommission extends BaseEntity {

	private Long cid;
	private Long parent_id;
	private Double commission;
	
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Long getParent_id() {
		return parent_id;
	}
	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}
	public Double getCommission() {
		return commission;
	}
	public void setCommission(Double commission) {
		this.commission = commission;
	}
}
