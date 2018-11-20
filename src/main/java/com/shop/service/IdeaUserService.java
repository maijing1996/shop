package com.shop.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.exception.BusinessException;
import com.shop.mapper.IdeaUserMapper;
import com.shop.model.dto.DistributionUser;
import com.shop.model.dto.MemberRanking;
import com.shop.model.dto.User;
import com.shop.model.dto.UserStatis;
import com.shop.model.po.IdeaUser;
import com.shop.model.po.IdeaUserPaylog;
import com.shop.service.common.BaseService;
import com.shop.util.DateUtil;

@Service
public class IdeaUserService extends BaseService<IdeaUser, IdeaUserMapper> {

	@Autowired
	private IdeaUserPaylogService ideaUserPaylogService;
	
	@Override
	protected String getTableName() {
		return "idea_user";
	}

	/**
	 * 获取用户信息
	 * @param page
	 * @param size
	 * @param nickName
	 * @return
	 */
	public PageInfo<User> listInfo(Integer page, Integer size, String nickName) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<User> list = mapper.listInfo(nickName);
		PageInfo<User> pageInfo = new PageInfo<User>(list);
		return pageInfo;
	}
	
	/**
	 * 查询分销商列表数据
	 * @return
	 */
	public PageInfo<DistributionUser> listDistributionUser(Integer page, Integer size, String uid, Long id){
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		List<DistributionUser> list = mapper.listDistributionUser(uid,id);
		PageInfo<DistributionUser> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	/**
	 * 获得会员排行信息
	 * @param page
	 * @param size
	 * @return
	 */
	public PageInfo<MemberRanking> getRanking(Integer page, Integer size, String nickName) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<MemberRanking> list = mapper.getRanking(nickName);
		PageInfo<MemberRanking> pageInfo = new PageInfo<MemberRanking>(list);
		if(pageInfo.getList() != null && !pageInfo.getList().isEmpty()) {
			for(int i=0; i < pageInfo.getList().size(); i++) {
				MemberRanking ranking = pageInfo.getList().get(i);
				ranking.setRanking(i+1);
			}
		}
		return pageInfo;
	}
	
	/**
	 * 根据第三方 unionid 判断是否已经授权过，是否已有数据
	 * 没有授权信息，返回：null
	 * @param unionid
	 * @return
	 */
	public IdeaUser isExist(String unionId, String openId) {
		return mapper.isExist(unionId, openId);
	}
	
	/**
	 * 获得用户信息
	 * @param openId
	 * @return
	 */
	public IdeaUser getUser(String openId) {
		return mapper.getUser(openId);
	}
	
	/**
	 * 调整用户积分或余额
	 * @param ideaUser
	 * @param ideaUserPaylog
	 * @throws BusinessException 
	 */
	@Transactional(rollbackFor = BusinessException.class)
	public void updateAll(IdeaUser ideaUser, IdeaUserPaylog ideaUserPaylog) throws BusinessException {
		this.update(ideaUser);
		ideaUserPaylogService.save(ideaUserPaylog);
	}
	
	/**
	 * 获得新增会员统计
	 * @param start
	 * @return
	 */
	public List<UserStatis> statistic(Long start) {
		Long now = DateUtil.getTimestamp();
		if(start == null) {
			Long end = now;
			start = now - 2592000;
			return mapper.statistic(start, end);
		} else {
			Long end = start + 2592000;
			if(now < end) {
				end = now;
			}
			return mapper.statistic(start, end);
		}
	}
	
	/**
	 * 修改所用指定分销商类型的名称
	 * @param dcId
	 * @param name
	 */
	public int updateAllNote(Long dcId, String name) {
		return mapper.updateAllNote(dcId, name);
	}
	
	/**
	 * 解除推荐信息
	 * @param id
	 * @return
	 */
	public int cancelBind(Long id) {
		return mapper.cancelBind(id);
	}
	
	/**
	 * 获得当前用户的下级信息
	 * @param ids
	 * @return
	 */
	public List<IdeaUser> getUserByIds(String ids) {
		return mapper.getUserByIds(ids);
	}
	
	/**
	 * 获得用户分销商信息
	 * @param id
	 * @return
	 */
	public Map<String, Object> getMerchant(Long id) {
		return mapper.getMerchant(id);
	}
}
