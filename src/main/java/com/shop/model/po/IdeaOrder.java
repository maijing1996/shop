package com.shop.model.po;

import javax.persistence.Transient;

import com.shop.model.common.BaseEntity;

/**
 * 订单表
 * 偶木
 * 2018年8月22日
 */
public class IdeaOrder extends BaseEntity {

	private String order_sn;//订单号
	private Long user_id;//用户ID
	private Double price;//订单金额
	private Double pay_price;//实付金额
	private Integer order_state;//订单状态 0:待付款 1:待发货 2:待收货 3:已完成
	private Integer pay_type;//支付方式 0:未选择 1:微信支付 2:支付宝 3:余额支付
	private String pay_sn;//交易号
	private String pay_date;//支付时间
	private Integer pay_terminal;//支付终端 0:手机端 1:pc端 2:小程序
	private Integer send_type;//0:快递 1:自提
	private String info;//订单备注
	private String name;//收货人
	private String tel;//电话
	private String address;//收货地址
	private String express_code;//快递公司编码
	private Double express_price;//运费
	private String express_title;//快递公司
	private String express_sn;//运单号
	private Long express_date;//发货时间
	private String pickup_sn;//提货号(自提订单)
	private String coupon_id;//优惠券ID
	private Double coupon_price;//优惠券金额
	private Double rebate_price;//会员折扣
	private Double discount_price;//订单优惠
	private Double jf_price;//积分抵扣
	private Double trim_price;//调整订单价格(正数为加，负数为减)
	private Long th_reason;//退货原因
	private Long is_comment;//是否评论
	private Double tc;//订单提成
	private Integer jf;//订单赠送积分
	private Long add_date;//添加日期
	private Long voucher_id;//代金券ID
	private Double voucher_price;//代金券金额
	private Integer use_jf;
	private String pic;//展示主图
	private Integer is_tc;//是否给予提成
	
	@Transient
	private Object goods;
	@Transient
	private Long specId;
	@Transient
	private Integer amount;
	
	public String getOrder_sn() {
		return order_sn;
	}
	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getPay_price() {
		return pay_price;
	}
	public void setPay_price(Double pay_price) {
		this.pay_price = pay_price;
	}
	public Integer getOrder_state() {
		return order_state;
	}
	public void setOrder_state(Integer order_state) {
		this.order_state = order_state;
	}
	public Integer getPay_type() {
		return pay_type;
	}
	public void setPay_type(Integer pay_type) {
		this.pay_type = pay_type;
	}
	public String getPay_sn() {
		return pay_sn;
	}
	public void setPay_sn(String pay_sn) {
		this.pay_sn = pay_sn;
	}
	public String getPay_date() {
		return pay_date;
	}
	public void setPay_date(String pay_date) {
		this.pay_date = pay_date;
	}
	public Integer getPay_terminal() {
		return pay_terminal;
	}
	public void setPay_terminal(Integer pay_terminal) {
		this.pay_terminal = pay_terminal;
	}
	public Integer getSend_type() {
		return send_type;
	}
	public void setSend_type(Integer send_type) {
		this.send_type = send_type;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getExpress_price() {
		return express_price;
	}
	public void setExpress_price(Double express_price) {
		this.express_price = express_price;
	}
	public String getExpress_title() {
		return express_title;
	}
	public void setExpress_title(String express_title) {
		this.express_title = express_title;
	}
	public String getExpress_sn() {
		return express_sn;
	}
	public void setExpress_sn(String express_sn) {
		this.express_sn = express_sn;
	}
	public Long getExpress_date() {
		return express_date;
	}
	public void setExpress_date(Long express_date) {
		this.express_date = express_date;
	}
	public String getPickup_sn() {
		return pickup_sn;
	}
	public void setPickup_sn(String pickup_sn) {
		this.pickup_sn = pickup_sn;
	}
	public String getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(String coupon_id) {
		this.coupon_id = coupon_id;
	}
	public Double getCoupon_price() {
		return coupon_price;
	}
	public void setCoupon_price(Double coupon_price) {
		this.coupon_price = coupon_price;
	}
	public Double getRebate_price() {
		return rebate_price;
	}
	public void setRebate_price(Double rebate_price) {
		this.rebate_price = rebate_price;
	}
	public Double getDiscount_price() {
		return discount_price;
	}
	public void setDiscount_price(Double discount_price) {
		this.discount_price = discount_price;
	}
	public Double getJf_price() {
		return jf_price;
	}
	public void setJf_price(Double jf_price) {
		this.jf_price = jf_price;
	}
	public Double getTrim_price() {
		return trim_price;
	}
	public void setTrim_price(Double trim_price) {
		this.trim_price = trim_price;
	}
	public Long getTh_reason() {
		return th_reason;
	}
	public void setTh_reason(Long th_reason) {
		this.th_reason = th_reason;
	}
	public Long getIs_comment() {
		return is_comment;
	}
	public void setIs_comment(Long is_comment) {
		this.is_comment = is_comment;
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
	public Long getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Long add_date) {
		this.add_date = add_date;
	}
	public Long getVoucher_id() {
		return voucher_id;
	}
	public void setVoucher_id(Long voucher_id) {
		this.voucher_id = voucher_id;
	}
	public Double getVoucher_price() {
		return voucher_price;
	}
	public void setVoucher_price(Double voucher_price) {
		this.voucher_price = voucher_price;
	}
	public Object getGoods() {
		return goods;
	}
	public void setGoods(Object goods) {
		this.goods = goods;
	}
	public Integer getUse_jf() {
		return use_jf;
	}
	public void setUse_jf(Integer use_jf) {
		this.use_jf = use_jf;
	}
	public Long getSpecId() {
		return specId;
	}
	public void setSpecId(Long specId) {
		this.specId = specId;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getExpress_code() {
		return express_code;
	}
	public void setExpress_code(String express_code) {
		this.express_code = express_code;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public Integer getIs_tc() {
		return is_tc;
	}
	public void setIs_tc(Integer is_tc) {
		this.is_tc = is_tc;
	}
}
