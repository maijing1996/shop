package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 条款表
 * 偶木
 * 2018年8月22日
 */
public class IdeaArticle extends BaseEntity {

	private Long cat_id;//分类ID
	private String title;//标题
	private String source;//来源
	private String pic;//图片
	private String summary;//摘要
	private String info;//内容
	private String tags;//标签
	private String url;//外链地址
	private String keywords;//关键词
	private String description;//seo描述
	private Integer is_top;//是否推荐
	private Integer is_show;//是否发布
	private Integer hits;//阅读量
	private Integer zan;//点赞数
	private Integer sequence;//排序
	private Long add_date;//添加日期
	private Integer is_jp;
	
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
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
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
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public Long getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Long add_date) {
		this.add_date = add_date;
	}
	public Integer getIs_jp() {
		return is_jp;
	}
	public void setIs_jp(Integer is_jp) {
		this.is_jp = is_jp;
	}
}
