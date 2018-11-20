package com.shop.model.dto;

public class LoginInfo {

	private String account;//账户
	private String passwd;//密码(MD5加密一次，不加盐）
	private String vercode;//验证码
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getVercode() {
		return vercode;
	}
	public void setVercode(String vercode) {
		this.vercode = vercode;
	}
}
