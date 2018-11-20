package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.mapper.IdeaDcenterMapper;
import com.shop.model.po.IdeaDcenter;
import com.shop.service.common.BaseService;

@Service
public class IdeaDcenterService extends BaseService<IdeaDcenter, IdeaDcenterMapper> {

	@Override
	protected String getTableName() {
		return "idea_dcenter";
	}
	
	/**
	 * 查询报单中心的接口信息
	 * @param id 
	 * @return
	 */
	public PageInfo<IdeaDcenter> listInfo(Integer page,Integer size){
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		}else {
			PageHelper.startPage(1, 20);
		}
		List<IdeaDcenter> list = mapper.listInfo();
		PageInfo<IdeaDcenter> pageInfo = new PageInfo<IdeaDcenter>(list);
		return pageInfo;
	}
	
	/**
	 * 根据名称查询是否已经存在
	 * @param name
	 * @return
	 */
	public boolean isEmpty(String name) {
		int ic = mapper.isEmpty(name);
		if(ic > 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 删除
	 */
	public void deleteByUserId(Long userId) {
		mapper.deleteByUserId(userId);
	}
}
