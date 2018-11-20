package com.shop.util;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSONObject;

/**
 * 日志打印公共类
 * 2018年3月15日
 * @author oumu
 */
public class LoggerUtil {

	/**
	 * 出现错误的打印日志
	 * @param logger
	 * @param location
	 * @param description
	 * @param params
	 * @param e
	 */
	public static void error(Logger logger, String location, String description, Object params, Exception e){
		logger.error("------------------错误------------------");
		logger.error("------所在方法：" + location);
		logger.error("------详情描述：" + description);
		logger.error("------错误原因：" + e);
		logger.error("------参数详情：{}" + JSONObject.toJSONString(params));
		logger.error("------------------错误------------------");
	}
	
	/**
	 * 平常日志答应
	 * @param logger
	 * @param location
	 * @param description
	 * @param params
	 * @param e
	 */
	public static void info(Logger logger, String location, String description, Object params){
		logger.info("------------------信息------------------");
		logger.info("------所在方法：" + location);
		logger.info("------详情描述：" + description);
		logger.info("------参数详情：{}" + JSONObject.toJSONString(params));
		logger.info("------------------信息------------------");
	}
	
	/**
	 * 单行的文字说明
	 * @param logger
	 * @param description
	 */
	public static void line(Logger logger, String description) {
		logger.info("------------------信息------------------");
		logger.info("------详情描述：" + description);
		logger.info("------------------信息------------------");
	}
}
