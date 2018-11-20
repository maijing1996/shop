package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.mapper.IdeaAdminMenuMapper;
import com.shop.model.dto.AdminMenu;
import com.shop.model.dto.Menu;
import com.shop.model.dto.MenuPackage;
import com.shop.model.po.IdeaAdminMenu;
import com.shop.service.common.BaseService;

@Service
public class IdeaAdminMenuService extends BaseService<IdeaAdminMenu, IdeaAdminMenuMapper> {

	@Override
	protected String getTableName() {
		return "idea_admin_menu";
	}

	/**
	 * 查询后台菜单模块信息
	 * @param page
	 * @param size
	 * @param parentId
	 * @return
	 */
	public PageInfo<AdminMenu> listInfo(Integer page, Integer size, Long parentId) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<AdminMenu> list = mapper.listInfo(parentId);
		PageInfo<AdminMenu> pageInfo = new PageInfo<AdminMenu>(list);
		return pageInfo;
	}
	
	/**
	 * 获得菜单列表
	 * @return
	 */
	public List<MenuPackage> menu() {
		return mapper.menu();
	}
	
	/**
	 * 获得后台菜单列表
	 * @return
	 */
	public List<Menu> getMenu() {
		return mapper.getMenu();
	}
	
	/**
	 * 获得后台菜单列表
	 * @return
	 */
	public List<Menu> getMenu2(String ids) {
		return mapper.getMenu2(ids);
	}
}
