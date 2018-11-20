package com.shop.model.dto;

import java.util.List;

public class MenuPackage {

	private Long num;
	private String nickname;
	private List<Menus> list;
	
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public List<Menus> getList() {
		return list;
	}
	public void setList(List<Menus> list) {
		this.list = list;
	}
}
