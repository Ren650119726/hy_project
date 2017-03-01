package com.mockuai.distributioncenter.core.service.action.distribution.finder;

import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.SellerConfigDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerRelationshipDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerConfigManager;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.manager.SellerRelationshipManager;
import com.mockuai.distributioncenter.core.service.action.distribution.finder.checker.Checker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by duke on 16/5/16.
 */
public class SimpleFinder extends AbstractFinder<SellerDTO> {
    private static final Logger log = LoggerFactory.getLogger(SimpleFinder.class);

    public SimpleFinder(SellerRelationshipManager relationshipManager, SellerManager sellerManager) {
        super(relationshipManager, sellerManager, new HashMap<Long, SellerDTO>(), new HashMap<Long, Long>());
    }

    @Override
    public SellerDTO find(final SellerDTO start, Checker<SellerDTO> checker) throws DistributionException {
        // 跟踪关系，用于检测关系中的环
        Set<Long> trace = new HashSet<Long>();

        // 先缓存，避免再次访问数据库
        cache.put(start.getUserId(), start);

        Long currentUserId = start.getUserId();
        trace.add(currentUserId);
        Long parentUserId = getParentId(currentUserId);

        // 判断是否有环
        if (trace.contains(parentUserId)) {
            log.error("relationship has circle, {} <-> {}", currentUserId, parentUserId);
            throw new DistributionException(ResponseCode.DISTRIBUTION_ERROR, "关系中存在环，分拥失败");
        } else {
            trace.add(parentUserId);
        }

        while (true) {
            if (parentUserId == 0) {
                // 如果父节点的卖家ID为0，则表示该卖家是根节点，结束遍历
                SellerDTO seller = getSeller(currentUserId);
                // 标记该卖家是平台卖家
                seller.setMaster(true);
                return seller;
            }

            SellerDTO parentObj = getSeller(parentUserId);
            if (checker.check(start, parentObj)) {
                // 检查满足条件的父亲节点是不是根节点，如果是，则直接返回
                Long parentId = getParentId(parentObj.getUserId());
                if (parentId == 0) {
                    parentObj.setMaster(true);
                }
                return parentObj;
            }
            currentUserId = parentUserId;
            parentUserId = getParentId(currentUserId);
            // 判断是否有环
            if (trace.contains(parentUserId)) {
                log.error("relationship has circle, {} <-> {}", currentUserId, parentUserId);
                throw new DistributionException(ResponseCode.DISTRIBUTION_ERROR, "关系中存在环，分拥失败");
            } else {
                trace.add(parentUserId);
            }
        }
    }

    private SellerDTO getSeller(Long userId) throws DistributionException {
        SellerDTO seller;
        if (cache != null && cache.containsKey(userId)) {
            seller = cache.get(userId);
        } else {
            // 如果卖家没有缓存，则从数据库获取
            seller = sellerManager.getByUserId(userId);
            if (seller == null) {
                log.error("no seller exists, userId: {}", userId);
                throw new DistributionException(ResponseCode.DISTRIBUTION_ERROR, "no seller exists");
            }
            if (cache != null) {
                cache.put(seller.getUserId(), seller);
            }
        }
        return seller;
    }

    private Long getParentId(Long userId) throws DistributionException {
        Long parentUserId;
        // 查询上级
        if (treeMap != null && treeMap.containsKey(userId)) {
            // 获得父节点的userId
            parentUserId = treeMap.get(userId);
        } else {
            // 如果不包含，则从数据库获取
            SellerRelationshipDTO relationshipDTO = relationshipManager.getByUserId(userId);
            if (relationshipDTO == null) {
                log.error("no relationship find, userId: {}", userId);
                throw new DistributionException(ResponseCode.DISTRIBUTION_ERROR, "no relationship find");
            }
            if (treeMap != null) {
                treeMap.put(userId, relationshipDTO.getParentId());
            }
            parentUserId = relationshipDTO.getParentId();
        }
        return parentUserId;
    }
}
