package com.shop.model.po;

import javax.persistence.Transient;

import com.shop.model.common.BaseEntity;

/**
 * 商品表
 * 偶木
 * 2018年8月22日
 */
public class IdeaGoods extends BaseEntity {
	
	private Long cat_id;//分类ID
	private Long shop_id;//所属店铺
	private Long brand_id;//品牌ID
	private String title;//名称
	private String subtitle;//副标题
	private String sn;//商品编号
	private String spu;//商品种类
	private String sku;//商品型号
	private String pic;//图片
	private String slide;//幻灯片
	private Double markey_price;//市场价
	private Double price;//本站价
	private Double cost_price;//成本价
	private Integer stock;//库存
	private Integer weight;//重量:克
	private Integer initial_sales;//原始销量
	private Integer sales;//总销量
	private String info;//商品介绍
	private String keywords;//关键词
	private String description;//seo描述
	private Double tc;//提成
	private Integer jf;//购买积分
	private Integer use_jf;//使用积分
	private String gg_info;//规格描述
	private String other_info;//包装等说明
	private Long spec_type;//所属规格分类模型Id
	private Integer prom_type;//0:无优惠 1:限时抢购 2:团购 3: 促销优惠
	private Long prom_id;//参与的活动id
	private Integer hits;//点击量
	private Integer sequence;//排序
	private Integer is_top;//推荐
	private Integer is_free;//免邮
	private Integer is_new;//新品
	private Integer is_hot;//热销
	private Integer is_sale;//上架
	private Integer is_delete;//删除
	private Integer is_jf;//是否是积分商品
	private Long add_date;//添加日期
	private Long person_id;
	private Integer is_index;
	private Integer is_restrict;//是否限购  1限购  0不限购  临时字段
	private Integer restrict_unit;//限购数量
	
	@Transient
	private String stocks;
	@Transient
	private String spec_key_ids;
	@Transient
	private String prices;
	
	public Long getCat_id() {
		return cat_id;
	}
	public void setCat_id(Long cat_id) {
		this.cat_id = cat_id;
	}
	public Long getShop_id() {
		return shop_id;
	}
	public void setShop_id(Long shop_id) {
		this.shop_id = shop_id;
	}
	public Long getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(Long brand_id) {
		this.brand_id = brand_id;
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
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getSpu() {
		return spu;
	}
	public void setSpu(String spu) {
		this.spu = spu;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
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
	public Double getMarkey_price() {
		return markey_price;
	}
	public void setMarkey_price(Double markey_price) {
		this.markey_price = markey_price;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getCost_price() {
		return cost_price;
	}
	public void setCost_price(Double cost_price) {
		this.cost_price = cost_price;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Integer getInitial_sales() {
		return initial_sales;
	}
	public void setInitial_sales(Integer initial_sales) {
		this.initial_sales = initial_sales;
	}
	public Integer getSales() {
		return sales;
	}
	public void setSales(Integer sales) {
		this.sales = sales;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
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
	public Double getTc() {
		return tc;
	}
	public void setTc(Double tc) {
		this.tc = tc;
	}
	public Integer getJf() {
		return jf;
	}
	public void setJf(Integer jf) {
		this.jf = jf;
	}
	public Integer getUse_jf() {
		return use_jf;
	}
	public void setUse_jf(Integer use_jf) {
		this.use_jf = use_jf;
	}
	public String getGg_info() {
		return gg_info;
	}
	public void setGg_info(String gg_info) {
		this.gg_info = gg_info;
	}
	public String getOther_info() {
		return other_info;
	}
	public void setOther_info(String other_info) {
		this.other_info = other_info;
	}
	public Long getSpec_type() {
		return spec_type;
	}
	public void setSpec_type(Long spec_type) {
		this.spec_type = spec_type;
	}
	public Integer getProm_type() {
		return prom_type;
	}
	public void setProm_type(Integer prom_type) {
		this.prom_type = prom_type;
	}
	public Long getProm_id() {
		return prom_id;
	}
	public void setProm_id(Long prom_id) {
		this.prom_id = prom_id;
	}
	public Integer getHits() {
		return hits;
	}
	public void setHits(Integer hits) {
		this.hits = hits;
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
	public Integer getIs_free() {
		return is_free;
	}
	public void setIs_free(Integer is_free) {
		this.is_free = is_free;
	}
	public Integer getIs_new() {
		return is_new;
	}
	public void setIs_new(Integer is_new) {
		this.is_new = is_new;
	}
	public Integer getIs_hot() {
		return is_hot;
	}
	public void setIs_hot(Integer is_hot) {
		this.is_hot = is_hot;
	}
	public Integer getIs_sale() {
		return is_sale;
	}
	public void setIs_sale(Integer is_sale) {
		this.is_sale = is_sale;
	}
	public Integer getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(Integer is_delete) {
		this.is_delete = is_delete;
	}
	public Integer getIs_jf() {
		return is_jf;
	}
	public void setIs_jf(Integer is_jf) {
		this.is_jf = is_jf;
	}
	public Long getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Long add_date) {
		this.add_date = add_date;
	}
	public Long getPerson_id() {
		return person_id;
	}
	public void setPerson_id(Long person_id) {
		this.person_id = person_id;
	}
	public Integer getIs_index() {
		return is_index;
	}
	public void setIs_index(Integer is_index) {
		this.is_index = is_index;
	}
	public Integer getIs_restrict() {
		return is_restrict;
	}
	public void setIs_restrict(Integer is_restrict) {
		this.is_restrict = is_restrict;
	}
	public Integer getRestrict_unit() {
		return restrict_unit;
	}
	public void setRestrict_unit(Integer restrict_unit) {
		this.restrict_unit = restrict_unit;
	}
	public String getStocks() {
		return stocks;
	}
	public void setStocks(String stocks) {
		this.stocks = stocks;
	}
	public String getSpec_key_ids() {
		return spec_key_ids;
	}
	public void setSpec_key_ids(String spec_key_ids) {
		this.spec_key_ids = spec_key_ids;
	}
	public String getPrices() {
		return prices;
	}
	public void setPrices(String prices) {
		this.prices = prices;
	}
}
