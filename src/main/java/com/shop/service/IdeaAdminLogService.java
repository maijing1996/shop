package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.mapper.IdeaAdminLogMapper;
import com.shop.model.po.IdeaAdminLog;
import com.shop.service.common.BaseService;

@Service
public class IdeaAdminLogService extends BaseService<IdeaAdminLog, IdeaAdminLogMapper> {

	@Override
	protected String getTableName() {
		return "idea_admin_log";
	}

	/**
	 * 获得日志信息
	 * @param page
	 * @param size
	 * @param state
	 * @return
	 */
	public PageInfo<IdeaAdminLog> listInfo(Integer page, Integer size, Integer state){
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<IdeaAdminLog> list = mapper.listInfo(state);
		PageInfo<IdeaAdminLog> pageInfo = new PageInfo<IdeaAdminLog>(list);
		return pageInfo;
	}
}
