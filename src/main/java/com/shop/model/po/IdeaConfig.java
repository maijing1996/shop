package com.shop.model.po;

import com.shop.model.common.BaseEntity;

public class IdeaConfig extends BaseEntity {

	private String mkey;
	private String value;
	
	public String getMkey() {
		return mkey;
	}
	public void setMkey(String mkey) {
		this.mkey = mkey;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
