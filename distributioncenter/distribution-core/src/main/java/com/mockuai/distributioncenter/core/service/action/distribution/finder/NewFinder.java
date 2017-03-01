package com.mockuai.distributioncenter.core.service.action.distribution.finder;

import com.mockuai.distributioncenter.core.exception.DistributionException;

/**
 * Created by duke on 16/5/16.
 */
public interface NewFinder {
    Long find(final Long userID) throws DistributionException;
}