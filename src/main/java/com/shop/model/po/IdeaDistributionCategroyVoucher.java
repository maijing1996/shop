package com.shop.model.po;

import com.shop.model.common.BaseEntity;

public class IdeaDistributionCategroyVoucher extends BaseEntity {

	private Long cid;//分销员表  即distribution_categroy表的主键ID
	private Long vid;//代金券ID，即voucher表的主键ID
	private Integer unit;
	
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Long getVid() {
		return vid;
	}
	public void setVid(Long vid) {
		this.vid = vid;
	}
	public Integer getUnit() {
		return unit;
	}
	public void setUnit(Integer unit) {
		this.unit = unit;
	}
}
