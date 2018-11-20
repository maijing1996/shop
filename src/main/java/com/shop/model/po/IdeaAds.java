package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 广告表
 * 偶木
 * 2018年8月22日
 */
public class IdeaAds extends BaseEntity {

	private String title;//标题
	private String url;//链接地址
	private String pic;//图片
	private String info;//描述
	private Integer type;//广告类型 0:普通广告 1:pc幻灯片
	private Integer sequence;//排序
	private Integer is_show;//是否显示
	
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
