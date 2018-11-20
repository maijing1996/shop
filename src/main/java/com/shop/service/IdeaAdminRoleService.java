package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaAdminRoleMapper;
import com.shop.model.po.IdeaAdminRole;
import com.shop.service.common.BaseService;

@Service
public class IdeaAdminRoleService extends BaseService<IdeaAdminRole, IdeaAdminRoleMapper> {

	@Override
	protected String getTableName() {
		return "idea_admin_role";
	}

	/**
	 * 获取角色信息
	 * @return
	 */
	public List<IdeaAdminRole> listInfo() {
		return mapper.listInfo();
	}
}
