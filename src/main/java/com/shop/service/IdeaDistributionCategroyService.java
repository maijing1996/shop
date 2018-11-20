package com.shop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.exception.BusinessException;
import com.shop.mapper.IdeaDistributionCategroyMapper;
import com.shop.model.dto.DistributionIdentity;
import com.shop.model.po.IdeaDistributionCategroy;
import com.shop.model.po.IdeaDistributionCategroyPrice;
import com.shop.model.po.IdeaDistributionCategroyVoucher;
import com.shop.service.common.BaseService;
import com.shop.util.StringUtil;

@Service
public class IdeaDistributionCategroyService extends BaseService<IdeaDistributionCategroy, IdeaDistributionCategroyMapper> {
	
	@Autowired
	private IdeaDistributionCategroyVoucherService ideaDistributionCategroyVoucherService;
	@Autowired
	private IdeaDistributionCategroyPriceService ideaDistributionCategroyPriceService;
	@Autowired
	private IdeaUserService ideaUserService;
	
	@Override
	protected String getTableName() {
		return "idea_distribution_categroy";
	}
	
	/**
	 * 查询所有的分销身份管理信息
	 * @return
	 */
	public PageInfo<DistributionIdentity> listInfo(Integer page, Integer size){
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<DistributionIdentity> list = mapper.listInfo();
		PageInfo<DistributionIdentity> pageInfo = new PageInfo<DistributionIdentity>(list);
		return pageInfo;
	}
	
	/**
	 * 删除分销商
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@Transactional(rollbackFor = BusinessException.class)
	public boolean deleteAll(Long id) throws BusinessException {
		this.deleteById(id);
		ideaDistributionCategroyVoucherService.deleteByParentId(id);
		return true;
	}

	/**
	 * 修改分銷商信息	 
	 * @throws BusinessException 
	 */
	@Transactional(rollbackFor = BusinessException.class)
	public boolean updateDistribution(Map<String, String> map, IdeaDistributionCategroy ideaDistributionCategroy) throws BusinessException {
		this.update(ideaDistributionCategroy);
		
		ideaDistributionCategroyVoucherService.deleteByParentId(ideaDistributionCategroy.getId());
		String[] arr = map.get("vouchers").split(",");
		if(arr != null && arr.length > 0 && !"".equals(arr[0])) {
			List<IdeaDistributionCategroyVoucher> list = new ArrayList<IdeaDistributionCategroyVoucher>();
			for(int i=0; i < arr.length; i++) {
				IdeaDistributionCategroyVoucher ideaDistributionCategroyVoucher = new IdeaDistributionCategroyVoucher();
				ideaDistributionCategroyVoucher.setCid(ideaDistributionCategroy.getId());
				ideaDistributionCategroyVoucher.setVid(StringUtil.strToLong(arr[i]));
				ideaDistributionCategroyVoucher.setUnit(StringUtil.strToInt(map.get(arr[i]+"")));
				
				list.add(ideaDistributionCategroyVoucher);
			}
			ideaDistributionCategroyVoucherService.insertList(list);
		}
		
		String[] ids = map.get("ids").split(",");
		String[] prices = map.get("price").split(",");
		if(ids != null && prices != null) {
			for(int i=0; i < ids.length; i++) {
				Long id = StringUtil.strToLong(ids[i]);
				if(id != null) {
					Double price = StringUtil.strToDouble(prices[i]);
					if(price == null || price < 0) {
						price = 0.0;
					}
					IdeaDistributionCategroyPrice ideaDistributionCategroyPrice = new IdeaDistributionCategroyPrice();
					ideaDistributionCategroyPrice.setId(id);
					ideaDistributionCategroyPrice.setPrice(price);
					ideaDistributionCategroyPriceService.update(ideaDistributionCategroyPrice);
				}
			}
		}
		
		//同步更改特定用户的分销商名称
		if(ideaDistributionCategroy.getName() != null && !"".equals(ideaDistributionCategroy.getName())) {
			ideaUserService.updateAllNote(ideaDistributionCategroy.getId(), ideaDistributionCategroy.getName());
		}
		return true;
	}
	
	/**
	 * 获取分销商列表
	 * @param userId
	 * @return
	 */
	public List<IdeaDistributionCategroy> listInfoByUserId() {
		return mapper.listInfoByUserId();
	}
}