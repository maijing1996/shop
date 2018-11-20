package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 微信回复表
 * 偶木
 * 2018年8月22日
 */
public class IdeaWxReply extends BaseEntity {

	private String title;//标题
	private Integer trigger_type;//回复方式 0:关键词 1:关注后回复
	private Integer reply_type;//回复类型 0:文本 1:推广码
	private String keyword;//关键词
	private String info;//说明
	private Long mater_id;//mater_id
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getTrigger_type() {
		return trigger_type;
	}
	public void setTrigger_type(Integer trigger_type) {
		this.trigger_type = trigger_type;
	}
	public Integer getReply_type() {
		return reply_type;
	}
	public void setReply_type(Integer reply_type) {
		this.reply_type = reply_type;
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
