package com.shop.model.dto;

import com.shop.util.DateUtil;

public class Comment {
	
	private Long id;
	private String uid;
	private String info;
	private String title;
	private String is_show;
	private String add_date;
	private String order_sn;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrder_sn() {
		return order_sn;
	}
	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIs_show() {
		return is_show;
	}
	public void setIs_show(Integer is_show) {
		if(is_show.equals(1)){
			this.is_show = "是";
		}else {
			this.is_show = "否";
		}
	}
	public String getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Long add_date) {
		if(add_date != null) {
			this.add_date = DateUtil.format(add_date*1000, DateUtil.FORMAT_YYYY_MM_dd_hh_mm_ss);
		}else {
			this.add_date = null;
		}
	}
}
