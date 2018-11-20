package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 品牌类
 * 偶木
 * 2018年8月22日
 */
public class IdeaBrand extends BaseEntity {

	private Long cat_id;//分类ID
	private String title;//品牌名称
	private String pic;//品牌图片
	private String info;//品牌描述
	private Integer sequence;//排序
	private Integer is_top;//是否推荐
	private Integer is_show;//是否显示
	
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
	public Integer getIs_top() {
		return is_top;
	}
	public void setIs_top(Integer is_top) {
		this.is_top = is_top;
	}
	public Integer getIs_show() {
		return is_show;
	}
	public void setIs_show(Integer is_show) {
		this.is_show = is_show;
	}
}
