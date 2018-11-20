package com.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shop.model.dto.AdminMenu;
import com.shop.model.dto.Menu;
import com.shop.model.dto.MenuPackage;
import com.shop.model.po.IdeaAdminMenu;
import com.shop.util.MyMapper;

public interface IdeaAdminMenuMapper extends MyMapper<IdeaAdminMenu> {

	List<AdminMenu> listInfo(@Param("parentId") Long parentId);
	
	List<MenuPackage> menu();
	
	List<Menu> getMenu();
	
	List<Menu> getMenu2(@Param("ids") String ids);
}
