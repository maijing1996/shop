package com.shop.model.dto;

public class WxReply {

	private Long id;
	private String title;//标题
	private String trigger_type;//回复方式 0:关键词 1:关注后回复
	private String reply_type;//回复类型 0:文本 1:推广码
	private String keyword;//关键词
	private String info;//说明
	private Long mater_id;
	
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
	public String getTrigger_type() {
		return trigger_type;
	}
	public void setTrigger_type(Integer trigger_type) {
		if(trigger_type == null) {
			
		} else if(trigger_type == 0) {
			this.trigger_type = "关键词回复";
		} else if(trigger_type == 1) {
			this.trigger_type = "关注后回复";
		}
	}
	public String getReply_type() {
		return reply_type;
	}
	public void setReply_type(Integer reply_type) {
		if(reply_type == null) {
			
		} else if(reply_type == 0) {
			this.reply_type = "文本内容";
		} else if(reply_type == 1) {
			this.reply_type = "海报图片";
		}
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Long getMater_id() {
		return mater_id;
	}
	public void setMater_id(Long mater_id) {
		this.mater_id = mater_id;
	}
}
