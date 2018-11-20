package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.mapper.IdeaWxMenuMapper;
import com.shop.model.dto.WxMenu;
import com.shop.model.po.IdeaWxMenu;
import com.shop.service.common.BaseService;

@Service
public class IdeaWxMenuService extends BaseService<IdeaWxMenu, IdeaWxMenuMapper> {

	@Override
	protected String getTableName() {
		return "idea_wx_menu";
	}

	/**
	 * 获取微信菜单信息
	 * @param page
	 * @param size
	 * @param parent
	 * @return
	 */
	public PageInfo<WxMenu> listInfo(Integer page, Integer size, Long parentId) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<WxMenu> list = mapper.listInfo(parentId);
		PageInfo<WxMenu> pageInfo = new PageInfo<WxMenu>(list);
		return pageInfo;
	}
}
