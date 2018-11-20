package com.shop.model.dto;

import java.util.List;

public class Menu {

	private String title;
	private String name;
	private String icon;
	private String jump;
	private Integer is_turn;
	private List<MenuDetails> list;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getJump() {
		return jump;
	}
	public void setJump(String jump) {
		this.jump = jump;
	}
	public Integer getIs_turn() {
		return is_turn;
	}
	public void setIs_turn(Integer is_turn) {
		this.is_turn = is_turn;
	}
	public List<MenuDetails> getList() {
		return list;
	}
	public void setList(List<MenuDetails> list) {
		this.list = list;
	}
}
