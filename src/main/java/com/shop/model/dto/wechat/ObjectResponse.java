package com.shop.model.dto.wechat;

public class ObjectResponse {

	private Integer code;//成功：200，找不到：404，405：权限受限，错误：500
	private String msg;//返回说明
	private Object data;//返回对象，需要时返回
	private Long total=0L;//数据条数
	private Integer per_page=0;//首页
	private Integer current_page=0;//当前页
	private Integer last_page=0;//最后页
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		if(data == null) {
			this.data = "";
		} else {
			this.data = data;
		}
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Integer getPer_page() {
		return per_page;
	}
	public void setPer_page(Integer per_page) {
		this.per_page = per_page;
	}
	public Integer getCurrent_page() {
		return current_page;
	}
	public void setCurrent_page(Integer current_page) {
		this.current_page = current_page;
	}
	public Integer getLast_page() {
		return last_page;
	}
	public void setLast_page(Integer last_page) {
		this.last_page = last_page;
	}
}
