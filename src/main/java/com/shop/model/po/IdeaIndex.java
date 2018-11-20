package com.shop.model.po;

import java.util.List;

import javax.persistence.Transient;

import com.shop.model.common.BaseEntity;

public class IdeaIndex extends BaseEntity {

	private Integer pic_type;//图片类型 1自适应宽度模块 2等比宽度模块
	private String title;//标题
	private String url;//跳转地址
	private String pic;//显示的图片
	private Integer type;//模块类型  1专题模块  2商品类型模块  3文章模块 4XX模块  5人气模块英文标题
	private String value;//如果type为2时，则存储商品的类型
	private Integer sort;//排序
	private Integer jump_type;//跳转的类型  0没有设置 1跳转设置的链接 2跳转至商品分类
	private Integer b_type;
	
	@Transient
	private List<IdeaGoods> data;
	
	public Integer getPic_type() {
		return pic_type;
	}
	public void setPic_type(Integer pic_type) {
		this.pic_type = pic_type;
	}
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getJump_type() {
		return jump_type;
	}
	public void setJump_type(Integer jump_type) {
		this.jump_type = jump_type;
	}
	public List<IdeaGoods> getData() {
		return data;
	}
	public void setData(List<IdeaGoods> data) {
		this.data = data;
	}
	public Integer getB_type() {
		return b_type;
	}
	public void setB_type(Integer b_type) {
		this.b_type = b_type;
	}
}
