package com.shop.model.dto;

public class Ads {

	private Long id;
	private String title;//标题
	private String url;//链接地址
	private String pic;//图片
	private String info;//描述
	private String type;//广告类型 1：首页幻灯片，2：积分中心幻灯片，3：分销申请幻灯片
	private Integer sequence;//排序
	private String is_show;//是否显示
	
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getType() {
		return type;
	}
	public void setType(Integer type) {
		if(type == null) {
			
		} else if(type == 1) {
			this.type = "首页幻灯片";
		} else if(type == 2) {
			this.type = "积分中心幻灯片";
		} else if(type == 3) {
			this.type = "分销申请幻灯片";
		}
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
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
