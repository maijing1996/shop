package com.shop.util;

import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

public class XStreamNameCoder extends XmlFriendlyNameCoder {
	public XStreamNameCoder() {
		super("_-", "_");
	}
}
