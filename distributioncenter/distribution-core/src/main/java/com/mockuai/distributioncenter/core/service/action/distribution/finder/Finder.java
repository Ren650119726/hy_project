package com.mockuai.distributioncenter.core.service.action.distribution.finder;

import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.service.action.distribution.finder.checker.Checker;

/**
 * Created by duke on 16/5/16.
 */
public interface Finder<T> {
    T find(final T start, Checker<T> checker) throws DistributionException;
}