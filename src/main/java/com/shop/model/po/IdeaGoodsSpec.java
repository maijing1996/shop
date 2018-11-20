package com.shop.model.po;

import java.util.List;

import javax.persistence.Transient;

import com.shop.model.common.BaseEntity;

/**
 * 商品规格表
 * 偶木
 * 2018年8月22日
 */
public class IdeaGoodsSpec extends BaseEntity {

	private Long type_id;//分类ID
	private String title;//规格名
	private Integer sequence;//排序
	private Integer is_search;//筛选
	
	@Transient
	private List<IdeaGoodsSpecItem> list;
	@Transient
	private String res;
	
	public Long getType_id() {
		return type_id;
	}
	public void setType_id(Long type_id) {
		this.type_id = type_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public Integer getIs_search() {
		return is_search;
	}
	public void setIs_search(Integer is_search) {
		this.is_search = is_search;
	}
	public List<IdeaGoodsSpecItem> getList() {
		return list;
	}
	public void setList(List<IdeaGoodsSpecItem> list) {
		this.list = list;
	}
	public String getRes() {
		return res;
	}
	public void setRes(String res) {
		this.res = res;
	}
}
