package com.shop.model.dto;

public class Category {

	private Long id;
	private String title;
	private String pic;
	private Integer sequence;
	private String is_top;
	private String is_show;
	private Long parent_id;
	private String wap_title;
	private String slide_pic;
	private String info;
	private String keywords;
	private String description;
	private String custom_url;
	private String en_title;
	private Long sid;
	private String stitle;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	
	public String getIs_top() {
		return is_top;
	}
	public void setIs_top(Integer is_top) {
		if(is_top == null) {
			
		} else if(is_top == 1) {
			this.is_top = "是";
		}else {
			this.is_top = "否";
		}
	}
	public String getIs_show() {
		return is_show;
	}
	public void setIs_show(Integer is_show) {
		if(is_show == null) {
			
		} else if(is_show == 1) {
			this.is_show = "是";
		}else {
			this.is_show = "否";
		}
	}
	public Long getParent_id() {
		return parent_id;
	}
	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}
	public String getWap_title() {
		return wap_title;
	}
	public void setWap_title(String wap_title) {
		this.wap_title = wap_title;
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
	public String getCustom_url() {
		return custom_url;
	}
	public void setCustom_url(String custom_url) {
		this.custom_url = custom_url;
	}
	public String getEn_title() {
		return en_title;
	}
	public void setEn_title(String en_title) {
		this.en_title = en_title;
	}
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	public String getStitle() {
		return stitle;
	}
	public void setStitle(String stitle) {
		this.stitle = stitle;
	}
}
