package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 会员海报表
 * 偶木
 * 2018年8月22日
 */
public class IdeaWxPoster extends BaseEntity {

	private Long user_id;//会员ID
	private Long media_id;//海报素材ID
	private Long add_date;//海报生成时间
	
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Long getMedia_id() {
		return media_id;
	}
	public void setMedia_id(Long media_id) {
		this.media_id = media_id;
	}
	public Long getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Long add_date) {
		this.add_date = add_date;
	}
}
