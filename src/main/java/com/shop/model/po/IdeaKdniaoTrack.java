package com.shop.model.po;

import java.util.List;

import javax.persistence.Transient;

import com.shop.model.common.BaseEntity;

public class IdeaKdniaoTrack extends BaseEntity {

	private Long order_no;//	String	订单编号	O
	private String pic;//商品图片路径
	private String tel;//发货公司电话
	private String ebusiness_id;//	String	用户ID	R
	private String shipper_code;//	String	快递公司编码	R
	private String shipper_name;//	String	快递公司名称
	private String logistic_code;//	String	物流运单号	O
	private Boolean res;//	Bool	成功与否	R
	private String reason;//	String	失败原因	O
	private String state;//	String	物流状态：2-在途中,3-签收,4-问题件
	
	@Transient
	private List<IdeaKdniaoTrackTraces> list;
	
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEbusiness_id() {
		return ebusiness_id;
	}
	public void setEbusiness_id(String ebusiness_id) {
		this.ebusiness_id = ebusiness_id;
	}
	public Long getOrder_no() {
		return order_no;
	}
	public void setOrder_no(Long order_no) {
		this.order_no = order_no;
	}
	public String getShipper_code() {
		return shipper_code;
	}
	public void setShipper_code(String shipper_code) {
		this.shipper_code = shipper_code;
	}
	public String getShipper_name() {
		return shipper_name;
	}
	public void setShipper_name(String shipper_name) {
		this.shipper_name = shipper_name;
	}
	public String getLogistic_code() {
		return logistic_code;
	}
	public void setLogistic_code(String logistic_code) {
		this.logistic_code = logistic_code;
	}
	public Boolean getRes() {
		return res;
	}
	public void setRes(Boolean res) {
		this.res = res;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<IdeaKdniaoTrackTraces> getList() {
		return list;
	}
	public void setList(List<IdeaKdniaoTrackTraces> list) {
		this.list = list;
	}
}
