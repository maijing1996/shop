package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 微信菜单表
 * 偶木
 * 2018年8月22日
 */
public class IdeaWxMenu extends BaseEntity {

	private Long parent_id;//上级ID
	private String title;//菜单名称
	private Integer type;//类型(0:链接 1:关键词)
	private String info;//链接地址/关键词
	private Integer sequence;//排序
	private Integer is_show;//是否显示
	
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
	}
}
