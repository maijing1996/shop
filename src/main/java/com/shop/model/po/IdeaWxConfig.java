package com.shop.model.po;

import com.shop.model.common.BaseEntity;

public class IdeaWxConfig extends BaseEntity {

	private String service_url;
	private String title;
	private String token;
	private String appid;
	private String app_secret;
	private String qr_code;
	
	public String getService_url() {
		return service_url;
	}
	public void setService_url(String service_url) {
		this.service_url = service_url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getApp_secret() {
		return app_secret;
	}
	public void setApp_secret(String app_secret) {
		this.app_secret = app_secret;
	}
	public String getQr_code() {
		return qr_code;
	}
	public void setQr_code(String qr_code) {
		this.qr_code = qr_code;
	}
}
