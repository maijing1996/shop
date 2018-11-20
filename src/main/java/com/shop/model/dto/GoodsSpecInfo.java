package com.shop.model.dto;

public class GoodsSpecInfo {
	
	private Long id;
	private Long type_id;//所属模型，Id
	private String title;//名称
	private Integer sequence;//排序
	private Integer is_search;//是否筛选
	private String model;//所属模型，名称
	private String item;//规格
	private Integer page;//页码
	private Integer size;//条数
	
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
	public Integer getIs_search() {
		return is_search;
	}
	public void setIs_search(Integer is_search) {
		this.is_search = is_search;
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
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
}
