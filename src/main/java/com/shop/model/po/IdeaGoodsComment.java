package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 商品评论表
 * 偶木
 * 2018年8月22日
 */
public class IdeaGoodsComment extends BaseEntity {
	
	private Long order_id;//订单ID
	private Long goods_id;//商品ID
	private String spec_item;//规格
	private Long user_id;//会员ID
	private Integer rate;//星级
	private String pic;//图片
	private String info;//评论内容
	private Integer is_show;//显示
	private Long add_date;//评论日期
	private String reply_info;//回复内容
	private Long reply_date;//回复日期
	
	public Long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}
	public Long getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(Long goods_id) {
		this.goods_id = goods_id;
	}
	public String getSpec_item() {
		return spec_item;
	}
	public void setSpec_item(String spec_item) {
		this.spec_item = spec_item;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Integer getRate() {
		return rate;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
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
	public Integer getIs_show() {
		return is_show;
	}
	public void setIs_show(Integer is_show) {
		this.is_show = is_show;
	}
	public Long getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Long add_date) {
		this.add_date = add_date;
	}
	public String getReply_info() {
		return reply_info;
	}
	public void setReply_info(String reply_info) {
		this.reply_info = reply_info;
	}
	public Long getReply_date() {
		return reply_date;
	}
	public void setReply_date(Long reply_date) {
		this.reply_date = reply_date;
	}
}
