package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 收藏表
 * 偶木
 * 2018年8月22日
 */
public class IdeaCollect extends BaseEntity {

	private Long user_id;//用户ID
	private Long info_id;//商品ID
	private Integer type_id;//0:商品 1:店铺 2:专题
	
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Long getInfo_id() {
		return info_id;
	}
	public void setInfo_id(Long info_id) {
		this.info_id = info_id;
	}
	public Integer getType_id() {
		return type_id;
	}
	public void setType_id(Integer type_id) {
		this.type_id = type_id;
	}
}
