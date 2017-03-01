package com.mockuai.mainweb.core.manager.publish;

import com.mockuai.mainweb.core.domain.PageItemCategoryDO;
import com.mockuai.mainweb.core.exception.MainWebException;

/**
 * Created by Administrator on 2016/9/21.
 */
public interface PublishPageItemCategoryManager {

    Long addPageItemCategory(PageItemCategoryDO pageItemCategoryDO) throws MainWebException;

    PageItemCategoryDO getPageItemCategory(Long pageBlockId) throws MainWebException;

    Integer deleteByPageId(Long id) throws MainWebException;
}
