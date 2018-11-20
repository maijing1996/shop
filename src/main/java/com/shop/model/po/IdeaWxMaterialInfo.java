package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 素材详情表
 * 偶木
 * 2018年8月22日
 */
public class IdeaWxMaterialInfo extends BaseEntity {

	private Long sort_id;//所属素材ID
	private String title;//文章名称
	private String pic_str;//图片
	private String note;//描述
	private String info;//内容
	private String url;//链接地址
	private Integer sequence;//排序
	private Integer is_lock;//是否锁定
	private Long add_date;//添加时间
	
	public Long getSort_id() {
		return sort_id;
	}
	public void setSort_id(Long sort_id) {
		this.sort_id = sort_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPic_str() {
		return pic_str;
	}
	public void setPic_str(String pic_str) {
		this.pic_str = pic_str;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public Integer getIs_lock() {
		return is_lock;
	}
	public void setIs_lock(Integer is_lock) {
		this.is_lock = is_lock;
	}
	public Long getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Long add_date) {
		this.add_date = add_date;
	}
}
