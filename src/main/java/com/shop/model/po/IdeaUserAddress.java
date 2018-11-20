package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 收货地址表
 * 偶木
 * 2018年8月22日
 */
public class IdeaUserAddress extends BaseEntity {

	private Long user_id;//用户ID
	private String name;//收货人
	private String province;//省
	private String city;//市
	private String county;//县
	private String address;//地址
	private String tel;//电话
	private Integer is_default;//默认
	
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Integer getIs_default() {
		return is_default;
	}
	public void setIs_default(Integer is_default) {
		this.is_default = is_default;
	}
}
