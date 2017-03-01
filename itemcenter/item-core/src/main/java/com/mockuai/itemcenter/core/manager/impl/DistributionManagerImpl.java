package com.mockuai.itemcenter.core.manager.impl;

import com.mockuai.distributioncenter.client.DistributionClient;
import com.mockuai.itemcenter.core.manager.DistributionManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yindingyu on 16/5/31.
 */
@Service
public class DistributionManagerImpl implements DistributionManager {

    @Resource
    private DistributionClient distributionClient;
}
