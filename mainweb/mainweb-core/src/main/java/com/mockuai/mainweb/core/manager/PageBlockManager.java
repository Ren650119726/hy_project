package com.mockuai.mainweb.core.manager;

import com.mockuai.mainweb.common.domain.dto.PageBlockDTO;
import com.mockuai.mainweb.core.domain.PageBlockDO;
import com.mockuai.mainweb.core.exception.MainWebException;

import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 */
public interface PageBlockManager {

    void addPageBlock(PageBlockDTO pageBlockDO) throws MainWebException;
    void addPageBlockList(List<PageBlockDTO> pageBlockList) throws MainWebException;

    void deletePageBlock(Long pageId) throws MainWebException;

    List<PageBlockDO> queryPageBlock(Long pageId) throws MainWebException;

}
