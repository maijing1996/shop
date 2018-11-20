package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.mapper.IdeaAdminMapper;
import com.shop.model.dto.Manager;
import com.shop.model.dto.RedisUser;
import com.shop.model.po.IdeaAdmin;
import com.shop.service.common.BaseService;

@Service
public class IdeaAdminService extends BaseService<IdeaAdmin, IdeaAdminMapper>{

	@Override
	protected String getTableName() {
		return "idea_admin";
	}
	
	/**
	 * 登陆操作
	 * @param account
	 * @param passwd
	 * @return
	 */
	public RedisUser login(String account, String passwd) {
		return mapper.login(account, passwd);
	}
	
	/**
	 * 获得管理员信息
	 * @param page
	 * @param size
	 * @return
	 */
	public PageInfo<Manager> listInfo(Integer page, Integer size) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<Manager> list = mapper.listInfo();
		PageInfo<Manager> pageInfo = new PageInfo<Manager>(list);
		return pageInfo;
	}
}
