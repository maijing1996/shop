package com.shop.model.dto;

import com.shop.util.DateUtil;

/**
 * 商品图片表
 * 偶木
 * 2018年8月22日
 */
public class Topic {

	private Long id;
	private Long cat_id;//分类ID
	private String title;//标题
	private String subtitle;//副标题
	private String source;//来源
	private String pic;//图片
	private String slide;//幻灯片
	private String starting_price;//起价
	private String goods_ids;//商品ID
	private String info;//内容
	private String url;//外链地址
	private String keywords;//关键词
	private String description;//seo描述
	private String is_top;//推荐
	private String is_show;//是否发布
	private Integer hits;//阅读量
	private Integer zan;//点赞数
	private String add_date;//添加日期
	
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
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getSlide() {
		return slide;
	}
	public void setSlide(String slide) {
		this.slide = slide;
	}
	public String getStarting_price() {
		return starting_price;
	}
	public void setStarting_price(String starting_price) {
		this.starting_price = starting_price;
	}
	public String getGoods_ids() {
		return goods_ids;
	}
	public void setGoods_ids(String goods_ids) {
		this.goods_ids = goods_ids;
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
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIs_top() {
		return is_top;
	}
	public void setIs_top(Integer is_top) {
		if(is_top == null) {
			
		} else if(is_top == 0) {
			this.is_top = "否";
		} else if(is_top == 1) {
			this.is_top = "是";
		}
	}
	public String getIs_show() {
		return is_show;
	}
	public void setIs_show(Integer is_show) {
		if(is_show == null) {
			
		} else if(is_show == 0) {
			this.is_show = "否";
		} else if(is_show == 1) {
			this.is_show = "是";
		}
	}
	public Integer getHits() {
		return hits;
	}
	public void setHits(Integer hits) {
		this.hits = hits;
	}
	public Integer getZan() {
		return zan;
	}
	public void setZan(Integer zan) {
		this.zan = zan;
	}
	public String getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Long add_date) {
		if(add_date != null) {
			this.add_date = DateUtil.format(add_date*1000, DateUtil.FORMAT_YYYY_MM_dd_hh_mm_ss);
		}
	}
}
