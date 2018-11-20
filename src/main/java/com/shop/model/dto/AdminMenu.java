package com.shop.model.dto;

public class AdminMenu {

	private Long id;
	private Long parent_id;//上级ID
	private String parent_name;//上级name
	private String title;//名称
	private String controller;//控制器
	private String operation;//操作方法
	private String parameter;//其他参数
	private String ico;//图标
	private Integer sequence;//排序
	private String type;//0:系统菜单 1:自定义菜单
	private String is_turn;//点击顶级菜单是否调整
	private String is_show;//显示
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParent_id() {
		return parent_id;
	}
	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}
	public String getTitle() {
		return title;
	}
	public String getParent_name() {
		return parent_name;
	}
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getController() {
		return controller;
	}
	public void setController(String controller) {
		this.controller = controller;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getIco() {
		return ico;
	}
	public void setIco(String ico) {
		this.ico = ico;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public String getType() {
		return type;
	}
	public void setType(Integer type) {
		if(type == null) {
			
		} else if(type == 0) {
			this.type = "系统菜单 ";
		} else if(type == 1) {
			this.type = "用户菜单";
		}
	}
	public String getIs_turn() {
		return is_turn;
	}
	public void setIs_turn(Integer is_turn) {
		if(is_turn == null) {
			
		} else if(is_turn == 0) {
			this.is_turn = "否";
		} else if(is_turn == 1) {
			this.is_turn = "是";
		}
	}
	public String getIs_show() {
		return is_show;
	}
	public void setIs_show(Integer is_show) {
		if(is_show == null) {
			
		} else if(is_show == 0) {
			this.is_show = "否";
		} else if(is_show == 1) {
			this.is_show = "是";
		}
	}
}
