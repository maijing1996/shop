package com.shop.model.dto;

import java.util.List;

public class GoodsSpec {

	private Long id;
	private Long type_id;//所属模型，Id
	private String title;//名称
	private Integer sequence;//排序
	private String is_search;//是否筛选
	private String model;//所属模型，名称
	private String item;//规格
	private List<Spec> list;//规格列表
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	
	public String getIs_search() {
		return is_search;
	}
	public void setIs_search(Integer is_search) {
		if(is_search != null) {
			if(is_search.equals(1)) {
				this.is_search = "是";
			}else {
				this.is_search = "否";
			}
		}else {
			this.is_search = null;
		}
		
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public List<Spec> getList() {
		return list;
	}
	public void setList(List<Spec> list) {
		this.list = list;
	}
}
