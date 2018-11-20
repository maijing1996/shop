package com.shop.mapper;

import java.util.List;

import com.shop.model.dto.DistributionIdentity;
import com.shop.model.po.IdeaDistributionCategroy;
import com.shop.util.MyMapper;

public interface IdeaDistributionCategroyMapper extends MyMapper<IdeaDistributionCategroy> {
	
	List<DistributionIdentity> listInfo();
	
	List<IdeaDistributionCategroy> listInfoByUserId();
}
