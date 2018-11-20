package com.shop.model.dto.wechat;

public class PhoneNumber {

	private String phoneNumber;
	private String purePhoneNumber;
	private String countryCode;
	private AppIdTime watermark;
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPurePhoneNumber() {
		return purePhoneNumber;
	}
	public void setPurePhoneNumber(String purePhoneNumber) {
		this.purePhoneNumber = purePhoneNumber;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public AppIdTime getWatermark() {
		return watermark;
	}
	public void setWatermark(AppIdTime watermark) {
		this.watermark = watermark;
	}
}
