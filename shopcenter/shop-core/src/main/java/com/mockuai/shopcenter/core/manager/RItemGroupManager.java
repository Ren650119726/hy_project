package com.mockuai.shopcenter.core.manager;

import com.google.common.collect.Lists;
import com.mockuai.shopcenter.core.domain.RItemGroupDO;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.domain.dto.RItemGroupDTO;
import com.mockuai.shopcenter.domain.dto.ShopItemGroupDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by yindingyu on 16/5/11.
 */
public interface RItemGroupManager {


    public void addRItemGroup(RItemGroupDTO rItemGroupDTO) throws ShopException;

    public void deleteItemGroupByGroupId(Long groupId, Long sellerId, String bizCode) throws ShopException;

    public List<Long> queryItemIdList(Long groupId, Long sellerId, String bizCode) throws ShopException;

    public Map<Long,ShopItemGroupDTO> queryGroupItemCount(List<Long> groupIdList,Long sellerId,String bizCode) throws ShopException;

}
