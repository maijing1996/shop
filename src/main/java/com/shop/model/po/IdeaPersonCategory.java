package com.shop.model.po;

import java.util.List;

import javax.persistence.Transient;

import com.shop.model.common.BaseEntity;

public class IdeaPersonCategory extends BaseEntity {

	private Long parent_id;//
	private String title;//
	private String wap_title;//
	private String pic;//
	private String slide_pic;//
	private String info;//
	private String keywords;//
	private String description;//
	private Integer sequence;//
	private Integer is_top;//
	private Integer is_show;//
	private String en_title;//
	private String custom_url;//
	
	@Transient
	private List<Object> data;
	
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
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getSlide_pic() {
		return slide_pic;
	}
	public void setSlide_pic(String slide_pic) {
		this.slide_pic = slide_pic;
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
	public String getEn_title() {
		return en_title;
	}
	public void setEn_title(String en_title) {
		this.en_title = en_title;
	}
	public String getCustom_url() {
		return custom_url;
	}
	public void setCustom_url(String custom_url) {
		this.custom_url = custom_url;
	}
	public List<Object> getData() {
		return data;
	}
	public void setData(List<Object> data) {
		this.data = data;
	}
}
