package com.shop.model.dto;

public class WxMenu {

	private Long id;
	private Long parent_id;//上级ID
	private String title;//菜单名称
	private Integer type;//类型(0:链接 1:关键词)
	private String info;//链接地址/关键词
	private Integer sequence;//排序
	private Integer is_show;//是否显示
	private String show;
	private String ftitle;
	
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
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public Integer getIs_show() {
		return is_show;
	}
	public void setIs_show(Integer is_show) {
		this.is_show = is_show;
		if(is_show == null) {
			
		} else if(is_show == 0) {
			this.show = "否";
		} else if(is_show == 1) {
			this.show = "是";
		}
	}
	public String getShow() {
		return show;
	}
	public void setShow(String show) {
		this.show = show;
	}
	public String getFtitle() {
		return ftitle;
	}
	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
	}
}
