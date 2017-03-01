package com.mockuai.shopcenter.core.manager.impl;

import com.google.common.collect.Lists;
import com.mockuai.shopcenter.core.dao.RItemGroupDAO;
import com.mockuai.shopcenter.core.domain.RItemGroupDO;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.RItemGroupManager;
import com.mockuai.shopcenter.domain.dto.RItemGroupDTO;
import com.mockuai.shopcenter.domain.dto.ShopItemGroupDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by yindingyu on 16/5/11.
 */
@Service
public class RItemGroupManagerImpl implements RItemGroupManager {

    @Resource
    private RItemGroupDAO rItemGroupDAO;

    @Override
    public void addRItemGroup(RItemGroupDTO rItemGroupDTO)  throws ShopException {

        RItemGroupDO rItemGroupDO = new RItemGroupDO();
        BeanUtils.copyProperties(rItemGroupDTO,rItemGroupDO);
        rItemGroupDAO.addRItemGroup(rItemGroupDO);
    }

    @Override
    public void deleteItemGroupByGroupId(Long groupId,Long sellerId,String bizCode) throws ShopException {

        RItemGroupDO query = new RItemGroupDO();
        query.setGroupId(groupId);
        query.setSellerId(sellerId);
        query.setBizCode(bizCode);

        rItemGroupDAO.deleteRItemGroupByGroupId(query);
    }

    @Override
    public List<Long> queryItemIdList(Long groupId,Long sellerId,String bizCode)  throws ShopException{

        RItemGroupDO query = new RItemGroupDO();
        query.setGroupId(groupId);
        query.setSellerId(sellerId);
        query.setBizCode(bizCode);

        List<RItemGroupDO> rItemGroupDOList = rItemGroupDAO.queryRItemGroupByGroupId(query);

        if(CollectionUtils.isEmpty(rItemGroupDOList)){
            return Collections.EMPTY_LIST;
        }


        List<Long> itemIdList = Lists.newArrayList();

        for(RItemGroupDO rItemGroupDO : rItemGroupDOList){
            itemIdList.add(rItemGroupDO.getItemId());
        }

        return itemIdList;

    }

    @Override
    public Map<Long,ShopItemGroupDTO> queryGroupItemCount(List<Long> groupIdList, Long sellerId, String bizCode) throws ShopException {
        return rItemGroupDAO.queryGroupItemCount(groupIdList,sellerId,bizCode);
    }
}
