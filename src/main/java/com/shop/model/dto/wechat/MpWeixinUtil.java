package com.shop.model.dto.wechat;

import com.mysql.jdbc.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.SortedMap;

public class MpWeixinUtil {

	private String token;

	public MpWeixinUtil(String token) {
		this.token = token;
	}

	/**
	 * 验证消息真实性，微信服务器发送的GET请求，包含signature、timestamp、nonce、echostr4个参数
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return true-消息请求来自微信服务器，原样返回echostr参数<br>
	 *         false-消息验证失败
	 */
	public boolean checkSignature(String signature, String timestamp,
			String nonce) {

		if (StringUtils.isNullOrEmpty(token)
				|| StringUtils.isNullOrEmpty(signature)
				|| StringUtils.isNullOrEmpty(timestamp)
				|| StringUtils.isNullOrEmpty(nonce)) {
			return false;
		}

		if (signature != null) {
			// ConfigUtil.TOKEN指服务器配置中用于生成签名的Token
			String[] tmpArr = { token, timestamp, nonce };
			Arrays.sort(tmpArr);

			StringBuilder buf = new StringBuilder();
			for (int i = 0; i < tmpArr.length; i++) {
				buf.append(tmpArr[i]);
			}

			if (signature.equals(encryptSHA1(buf.toString()))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 对字符串进行sha1加密
	 * 
	 * @param strSrc
	 *            - 要加密的字符串
	 * @return 加密后的字符串
	 */
	private String encryptSHA1(String strSrc) {
		MessageDigest md = null;
		String strDes = null;

		byte[] bt = strSrc.getBytes();
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(bt);
			strDes = bytes2Hex(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Invalid algorithm.");
			return null;
		}
		return strDes;
	}

	private String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

	/**
	 * 将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），使用URL键值对的格式（即key1=value1&key2=value2…
	 * ）拼接成字符串stringA
	 * 
	 * @param map
	 * @return
	 */
	public static String sortAscii(SortedMap<String, String> map) {
		String result = "";
		if (map != null) {
			for (Entry<String, String> entry : map.entrySet()) {
				if (!StringUtils.isNullOrEmpty(entry.getValue())) {
					result += (StringUtils.isNullOrEmpty(result) ? "" : "&")
							+ entry.getKey() + "=" + entry.getValue();
				}
			}
		}
		return result;
	}
}
