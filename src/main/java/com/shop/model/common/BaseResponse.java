package com.shop.model.common;

import java.io.Serializable;

public class BaseResponse  implements Serializable  {
	
	private static final long serialVersionUID = -940795926263067880L;

	private boolean success;//是否成功
	private Object result;//结果
	private String desc;//描述
	private int descCode = -99999;//描述编号
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getDescCode() {
		return descCode;
	}
	public void setDescCode(int descCode) {
		this.descCode = descCode;
	}
	
	/**
	 * 操作成功时的返回
	 * @param baseResponse
	 * @param result
	 * @param resultState
	 * @param resultMsg
	 * @return
	 */
	public BaseResponse setValue(String desc, Object result) {
		this.result = result;
		this.desc = desc;
		this.success = true;
		this.descCode = 200;
		return this;
	}
	
	/**
	 * 操作失败时的返回
	 * @param baseResponse
	 * @param resultState
	 * @param resultMsg
	 * @return
	 */
	public BaseResponse setError(int code, String desc) {
		this.descCode = code;
		this.desc = desc;
		this.result = null;
		this.success = false;
		return this;
	}
}
