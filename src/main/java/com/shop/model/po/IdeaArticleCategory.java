package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 条款分类表
 * 偶木
 * 2018年8月22日
 */
public class IdeaArticleCategory extends BaseEntity {

	private Long parent_id=0L;//上级ID
	private String title;//名称
	private String wap_title;//手机名称
	private String info;//描述
	private String keywords;//关键词
	private String description;//seo描述
	private Integer sequence;//排序
	private Integer is_top;//推荐
	private Integer is_show;//显示
	
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
	public String getWap_title() {
		return wap_title;
	}
	public void setWap_title(String wap_title) {
		this.wap_title = wap_title;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public Integer getIs_top() {
		return is_top;
	}
	public void setIs_top(Integer is_top) {
		this.is_top = is_top;
	}
	public Integer getIs_show() {
		return is_show;
	}
	public void setIs_show(Integer is_show) {
		this.is_show = is_show;
	}
}
