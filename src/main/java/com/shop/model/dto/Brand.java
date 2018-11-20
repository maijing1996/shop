package com.shop.model.dto;

public class Brand {

	private Long id;
	private Long cat_id;//分类ID
	private String title;//品牌名称
	private String pic;//品牌图片
	private String info;//品牌描述
	private Integer sequence;//排序
	private String is_top;//是否推荐
	private String is_show;//是否显示
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCat_id() {
		return cat_id;
	}
	public void setCat_id(Long cat_id) {
		this.cat_id = cat_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public String getIs_top() {
		return is_top;
	}
	public void setIs_top(Integer is_top) {
		if(is_top == null) {
			
		} else if(is_top == 1) {
			this.is_top = "是";
		} else {
			this.is_top = "否";
		}
	}
	public String getIs_show() {
		return is_show;
	}
	public void setIs_show(Integer is_show) {
		if(is_show == null) {
			
		} else if(is_show == 1) {
			this.is_show = "是";
		} else {
			this.is_show = "否";
		}
	}
}
