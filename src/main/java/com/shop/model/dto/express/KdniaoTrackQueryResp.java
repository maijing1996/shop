package com.shop.model.dto.express;

import java.util.List;

public class KdniaoTrackQueryResp {

	private String EBusinessID;//	String	用户ID	R
	private String OrderCode;//	String	订单编号	O
	private String ShipperCode;//	String	快递公司编码	R
	private String LogisticCode;//	String	物流运单号	O
	private Boolean Success;//	Bool	成功与否	R
	private String Reason;//	String	失败原因	O
	private String State;//	String	物流状态：2-在途中,3-签收,4-问题件
	private List<Traces> Traces;//物流详情
	
	public String getEBusinessID() {
		return EBusinessID;
	}
	public void setEBusinessID(String eBusinessID) {
		EBusinessID = eBusinessID;
	}
	public String getOrderCode() {
		return OrderCode;
	}
	public void setOrderCode(String orderCode) {
		OrderCode = orderCode;
	}
	public String getShipperCode() {
		return ShipperCode;
	}
	public void setShipperCode(String shipperCode) {
		ShipperCode = shipperCode;
	}
	public String getLogisticCode() {
		return LogisticCode;
	}
	public void setLogisticCode(String logisticCode) {
		LogisticCode = logisticCode;
	}
	public Boolean getSuccess() {
		return Success;
	}
	public void setSuccess(Boolean success) {
		Success = success;
	}
	public String getReason() {
		return Reason;
	}
	public void setReason(String reason) {
		Reason = reason;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public List<Traces> getTraces() {
		return Traces;
	}
	public void setTraces(List<Traces> traces) {
		Traces = traces;
	}
}
