package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 代金券表
 * 偶木
 * 2018年8月22日
 */
public class IdeaUserVoucher extends BaseEntity {

	private Long uid;//用户Id
	private Long vid;//代金券ID，对应voucher表的ID
	private Long add_time;//添加时间
	private Integer is_use;//是否已使用  0未使用  1已使用
	private Long use_time;//代金券使用时间
	private String order_sn;//使用订单SN
	
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getVid() {
		return vid;
	}
	public void setVid(Long vid) {
		this.vid = vid;
	}
	public Long getAdd_time() {
		return add_time;
	}
	public void setAdd_time(Long add_time) {
		this.add_time = add_time;
	}
	public Integer getIs_use() {
		return is_use;
	}
	public void setIs_use(Integer is_use) {
		this.is_use = is_use;
	}
	public Long getUse_time() {
		return use_time;
	}
	public void setUse_time(Long use_time) {
		this.use_time = use_time;
	}
	public String getOrder_sn() {
		return order_sn;
	}
	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}
}
