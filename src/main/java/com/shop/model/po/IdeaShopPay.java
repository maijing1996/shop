package com.shop.model.po;

import com.shop.model.common.BaseEntity;
/**
 * 支付设置
 * 2018年9月5日
 */
public class IdeaShopPay extends BaseEntity {
	
	private Integer wx_pay;//微信支付(1:开启 0:关闭)
	private String wx_mchid;//商户号
	private String wx_appkey;//支付秘钥
	private String wx_notify_url;//通知地址
	private Integer ali_pay;//支付宝支付(1:开启 0:关闭)
	private Integer ali_appid;//应用APPID
	private String ali_public_key;//支付宝公钥
	private String ali_private_key;//应用私钥
	private String ali_notify_url;//应用网关
	private String ali_return_url;//回调地址
	
	public Integer getWx_pay() {
		return wx_pay;
	}
	public void setWx_pay(Integer wx_pay) {
		this.wx_pay = wx_pay;
	}
	public String getWx_mchid() {
		return wx_mchid;
	}
	public void setWx_mchid(String wx_mchid) {
		this.wx_mchid = wx_mchid;
	}
	public String getWx_appkey() {
		return wx_appkey;
	}
	public void setWx_appkey(String wx_appkey) {
		this.wx_appkey = wx_appkey;
	}
	public String getWx_notify_url() {
		return wx_notify_url;
	}
	public void setWx_notify_url(String wx_notify_url) {
		this.wx_notify_url = wx_notify_url;
	}
	public Integer getAli_pay() {
		return ali_pay;
	}
	public void setAli_pay(Integer ali_pay) {
		this.ali_pay = ali_pay;
	}
	public Integer getAli_appid() {
		return ali_appid;
	}
	public void setAli_appid(Integer ali_appid) {
		this.ali_appid = ali_appid;
	}
	public String getAli_public_key() {
		return ali_public_key;
	}
	public void setAli_public_key(String ali_public_key) {
		this.ali_public_key = ali_public_key;
	}
	public String getAli_private_key() {
		return ali_private_key;
	}
	public void setAli_private_key(String ali_private_key) {
		this.ali_private_key = ali_private_key;
	}
	public String getAli_notify_url() {
		return ali_notify_url;
	}
	public void setAli_notify_url(String ali_notify_url) {
		this.ali_notify_url = ali_notify_url;
	}
	public String getAli_return_url() {
		return ali_return_url;
	}
	public void setAli_return_url(String ali_return_url) {
		this.ali_return_url = ali_return_url;
	}
	
}
