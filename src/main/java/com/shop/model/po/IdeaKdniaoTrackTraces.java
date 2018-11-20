package com.shop.model.po;

import com.shop.model.common.BaseEntity;

public class IdeaKdniaoTrackTraces extends BaseEntity {

	private Long parent_id;
	private String accept_time;//	String	时间	R
	private String accept_station;//	String	描述	R
	private String remark;//	String	备注
	private Integer number;//状态
	private Integer num;//节点
	
	public Long getParent_id() {
		return parent_id;
	}
	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}
	public String getAccept_time() {
		return accept_time;
	}
	public void setAccept_time(String accept_time) {
		this.accept_time = accept_time;
	}
	public String getAccept_station() {
		return accept_station;
	}
	public void setAccept_station(String accept_station) {
		this.accept_station = accept_station;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
}
