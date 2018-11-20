package com.shop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.exception.BusinessException;
import com.shop.mapper.IdeaGoodsSpecMapper;
import com.shop.model.dto.GoodsSpec;
import com.shop.model.dto.SpecPrice;
import com.shop.model.po.IdeaGoodsSpec;
import com.shop.model.po.IdeaGoodsSpecItem;
import com.shop.service.common.BaseService;
import com.shop.util.StringUtil;

@Service
public class IdeaGoodsSpecService extends BaseService<IdeaGoodsSpec, IdeaGoodsSpecMapper> {

	@Autowired
	private IdeaGoodsSpecItemService ideaGoodsSpecItemService;
	
	@Override
	protected String getTableName() {
		return "idea_goods_spec";
	}

	/**
	 * 获得所有规格信息
	 * @param page
	 * @param size
	 * @param type_id
	 * @return
	 */
	public PageInfo<GoodsSpec> listInfo(Integer page, Integer size, Long typeId) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<GoodsSpec> list = mapper.listInfo(typeId);
		PageInfo<GoodsSpec> pageInfo = new PageInfo<GoodsSpec>(list);
		return pageInfo;
	}
	
	/**
	 * 添加商品规格信息
	 * @param ideaGoodsSpec
	 * @param item
	 * @throws BusinessException 
	 */
	@Transactional(rollbackFor={BusinessException.class})
	public void saveSpec(IdeaGoodsSpec ideaGoodsSpec, String item) throws BusinessException {
		this.save(ideaGoodsSpec);
		if(item != null) {
			String[] split = item.split("\\n");
			List<IdeaGoodsSpecItem> list = new ArrayList<IdeaGoodsSpecItem>();
			for(String val : split) {
				IdeaGoodsSpecItem ideaGoodsSpecItem = new IdeaGoodsSpecItem();
				ideaGoodsSpecItem.setTitle(val);
				ideaGoodsSpecItem.setSpec_id(ideaGoodsSpec.getId());
				list.add(ideaGoodsSpecItem);
			}
			//插入操作
			if(!list.isEmpty()) {
				ideaGoodsSpecItemService.insertList(list);
			}
		}
	}
	
	/**
	 * 修改商品规格信息
	 * 1：保存，2：更新，3：删除
	 * @param ideaGoodsSpec
	 * @param item
	 * @throws BusinessException
	 */
	@Transactional(rollbackFor={BusinessException.class})
	public void updateSpec(IdeaGoodsSpec ideaGoodsSpec, String item) throws BusinessException {
		this.update(ideaGoodsSpec);
		if(item != null) {
			StringBuffer delete = new StringBuffer();
			List<IdeaGoodsSpecItem> list = new ArrayList<IdeaGoodsSpecItem>();
			String[] split = item.split("\\n");
			for(int i=0; i < split.length; i=i+3) {
				if(split[i].equals("1")) {
					IdeaGoodsSpecItem ideaGoodsSpecItem = new IdeaGoodsSpecItem();
					ideaGoodsSpecItem.setId(StringUtil.strToLong(split[i+1]));
					ideaGoodsSpecItem.setTitle(split[i+2]);
					list.add(ideaGoodsSpecItem);
				} else if(split[i].equals("2")) {
					IdeaGoodsSpecItem ideaGoodsSpecItem = new IdeaGoodsSpecItem();
					ideaGoodsSpecItem.setId(StringUtil.strToLong(split[i+1]));
					ideaGoodsSpecItem.setTitle(split[i+2]);
					//更新操作
					ideaGoodsSpecItemService.update(ideaGoodsSpecItem);
				} else if(split[i].equals("3")) {
					delete.append(split[i+1]);
					delete.append(",");
				} else {
					throw new BusinessException();
				}
			}
			//插入操作
			if(!list.isEmpty()) {
				ideaGoodsSpecItemService.insertList(list);
			}
			//删除操作
			if(delete.length() > 0) {
				delete.deleteCharAt(delete.length()-1);
				ideaGoodsSpecItemService.deleteByIds(delete.toString());
			}
		}
	}
	
	/**
	 * 判断相应的排序数值是否存在
	 * @param sequence
	 * @return
	 */
	public boolean isExist(int sequence) {
		int ic = mapper.isExist(sequence);
		if(ic > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public List<SpecPrice> getAllInfoByGoods(Long goods_id) {
		return mapper.getAllInfoByGoods(goods_id);
	}
}