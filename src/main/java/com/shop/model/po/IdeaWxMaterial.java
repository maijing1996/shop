package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 素材表
 * 偶木
 * 2018年8月22日
 */
public class IdeaWxMaterial extends BaseEntity {

	private String title;//素材名称
	private String media_id;//素材id
	private Integer is_lock;//是否锁定
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public Integer getIs_lock() {
		return is_lock;
	}
	public void setIs_lock(Integer is_lock) {
		this.is_lock = is_lock;
	}
}
