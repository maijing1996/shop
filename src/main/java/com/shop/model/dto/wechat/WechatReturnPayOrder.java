package com.shop.model.dto.wechat;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class WechatReturnPayOrder {

	//通知参数
	//必然返回
	private String return_code;//返回状态码    String(16) SUCCESS/FAIL
	//选择返回
	private String return_msg; //String(128)	返回信息，如非空，为错误原因

	//成功后的返回
	//必然返回
	private String appid;		//公众账号ID String(32)
	private String mch_id;		//商户号 String(32)
	private String nonce_str;	//	随机字符串	String(32)	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	随机字符串，不长于32位
	private String sign;		//签名	String(32)	C380BEC2BFD727A4B6845133519F3AD6	签名，详见签名算法
	private String result_code;	//业务结果	String(16)	SUCCESS	SUCCESS/FAIL
	private String openid;		//用户标识	String(128)	wxd930ea5d5a258f4f	用户在商户appid下的唯一标识
	private String trade_type;	//交易类型	String(16)	JSAPI	JSAPI、NATIVE、APP
	private String bank_type;	//付款银行	String(16)	CMC	银行类型，采用字符串类型的银行标识，银行类型见银行列表
	private Integer total_fee;	//订单金额	Int	100	订单总金额，单位为分
	private Integer cash_fee;	//现金支付金额	Int	100	现金支付金额订单现金支付金额，详见支付金额
	private String transaction_id;//微信支付订单号	String(32)	1217752501201407033233368018	微信支付订单号
	private String out_trade_no;//商户订单号	String(32)	1212321211201407033568112322	商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
	private String time_end;	//支付完成时间 String(14)	20141030133525	支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
	
	//选择返回
	private String device_info;//	设备号 	String(32)	013467007045764	微信支付分配的终端设备号，
	private String sign_type;//签名类型	String(32)	HMAC-SHA256	签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
	private String err_code;//错误代码	String(32)	SYSTEMERROR	错误返回的信息描述
	private String err_code_des;//错误代码描述  String(128)	系统错误	错误返回的信息描述
	private String is_subscribe;//是否关注公众账号	tring(1)	Y	用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
	private String fee_type;//货币种类	String(8)	CNY	货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
	private String cash_fee_type;//现金支付货币类型	String(16)	CNY	货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
	private String attach;//商家数据包	String(128)	123456	商家数据包，原样返回
	private Integer settlement_total_fee;//应结订单金额	Int	100	应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额。
	private Integer coupon_fee;//总代金券金额	Int	10	代金券金额<=订单金额，订单金额-代金券金额=现金支付金额，详见支付金额
	private Integer coupon_count;//代金券使用数量     Int	1	代金券使用数量
	
	//代金券1
	private String coupon_type_0;//代金券类型	String	CASH  CASH--充值代金券  NO_CASH---非充值代金券 并且订单使用了免充值券后有返回（取值：CASH、NO_CASH）。$n为下标,从0开始编号，举例：coupon_type_0
	private String coupon_id_0;//代金券ID	 String(20)	10000	代金券ID,$n为下标，从0开始编号
	private Integer coupon_fee_0;//单个代金券支付金额	 Int	100	单个代金券支付金额,$n为下标，从0开始编号
	
	private String coupon_type_1;//代金券类型	String	CASH  CASH--充值代金券  NO_CASH---非充值代金券 并且订单使用了免充值券后有返回（取值：CASH、NO_CASH）。$n为下标,从0开始编号，举例：coupon_type_0
	private String coupon_id_1;//代金券ID	 String(20)	10000	代金券ID,$n为下标，从0开始编号
	private Integer coupon_fee_1;//单个代金券支付金额	 Int	100	单个代金券支付金额,$n为下标，从0开始编号
	
	private String coupon_type_2;//代金券类型	String	CASH  CASH--充值代金券  NO_CASH---非充值代金券 并且订单使用了免充值券后有返回（取值：CASH、NO_CASH）。$n为下标,从0开始编号，举例：coupon_type_0
	private String coupon_id_2;//代金券ID	 String(20)	10000	代金券ID,$n为下标，从0开始编号
	private Integer coupon_fee_2;//单个代金券支付金额	 Int	100	单个代金券支付金额,$n为下标，从0开始编号
	
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getBank_type() {
		return bank_type;
	}
	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}
	public Integer getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}
	public Integer getCash_fee() {
		return cash_fee;
	}
	public void setCash_fee(Integer cash_fee) {
		this.cash_fee = cash_fee;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getTime_end() {
		return time_end;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getErr_code_des() {
		return err_code_des;
	}
	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
	public String getIs_subscribe() {
		return is_subscribe;
	}
	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	public String getCash_fee_type() {
		return cash_fee_type;
	}
	public void setCash_fee_type(String cash_fee_type) {
		this.cash_fee_type = cash_fee_type;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public Integer getSettlement_total_fee() {
		return settlement_total_fee;
	}
	public void setSettlement_total_fee(Integer settlement_total_fee) {
		this.settlement_total_fee = settlement_total_fee;
	}
	public Integer getCoupon_fee() {
		return coupon_fee;
	}
	public void setCoupon_fee(Integer coupon_fee) {
		this.coupon_fee = coupon_fee;
	}
	public Integer getCoupon_count() {
		return coupon_count;
	}
	public void setCoupon_count(Integer coupon_count) {
		this.coupon_count = coupon_count;
	}
	public String getCoupon_type_0() {
		return coupon_type_0;
	}
	public void setCoupon_type_0(String coupon_type_0) {
		this.coupon_type_0 = coupon_type_0;
	}
	public String getCoupon_id_0() {
		return coupon_id_0;
	}
	public void setCoupon_id_0(String coupon_id_0) {
		this.coupon_id_0 = coupon_id_0;
	}
	public Integer getCoupon_fee_0() {
		return coupon_fee_0;
	}
	public void setCoupon_fee_0(Integer coupon_fee_0) {
		this.coupon_fee_0 = coupon_fee_0;
	}
	public String getCoupon_type_1() {
		return coupon_type_1;
	}
	public void setCoupon_type_1(String coupon_type_1) {
		this.coupon_type_1 = coupon_type_1;
	}
	public String getCoupon_id_1() {
		return coupon_id_1;
	}
	public void setCoupon_id_1(String coupon_id_1) {
		this.coupon_id_1 = coupon_id_1;
	}
	public Integer getCoupon_fee_1() {
		return coupon_fee_1;
	}
	public void setCoupon_fee_1(Integer coupon_fee_1) {
		this.coupon_fee_1 = coupon_fee_1;
	}
	public String getCoupon_type_2() {
		return coupon_type_2;
	}
	public void setCoupon_type_2(String coupon_type_2) {
		this.coupon_type_2 = coupon_type_2;
	}
	public String getCoupon_id_2() {
		return coupon_id_2;
	}
	public void setCoupon_id_2(String coupon_id_2) {
		this.coupon_id_2 = coupon_id_2;
	}
	public Integer getCoupon_fee_2() {
		return coupon_fee_2;
	}
	public void setCoupon_fee_2(Integer coupon_fee_2) {
		this.coupon_fee_2 = coupon_fee_2;
	}
}
