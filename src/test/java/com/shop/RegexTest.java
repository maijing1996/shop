package com.shop;

import com.shop.util.RegexUtil;

public class RegexTest {

	public static void main(String[] args) {
		//System.out.println(RegexUtil.matches(RegexUtil.IDS_REGEX, "1,2,2"));
		System.out.println(RegexUtil.matches("\\{[(\\w|\\W)]*\\}", "{sdfs}"));
	}
}
