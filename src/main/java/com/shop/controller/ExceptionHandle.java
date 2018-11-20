package com.shop.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.model.common.BaseResponse;
import com.shop.util.LoggerUtil;

/**
 * 错误返回类
 * 2018年5月10日
 * @author oumu
 */
@ControllerAdvice
public class ExceptionHandle {
	
	private Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

	/**
	 * 系统错误统一返回方法
	 * @param request
	 * @param e
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public BaseResponse handle(HttpServletRequest request, Exception e) {
		String url = request.getRequestURI();
		Map<String, String[]> map = request.getParameterMap();
		LoggerUtil.error(logger, url, null, map, e);
		
		BaseResponse baseResponse = new BaseResponse();
		return baseResponse.setError(500, "系统异常");
	}
}
