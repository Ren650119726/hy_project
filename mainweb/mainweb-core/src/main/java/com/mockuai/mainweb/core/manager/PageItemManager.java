package com.mockuai.mainweb.core.manager;

import com.mockuai.mainweb.common.domain.dto.PageItemDTO;
import com.mockuai.mainweb.core.domain.PageItemDO;
import com.mockuai.mainweb.core.exception.MainWebException;

import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public interface PageItemManager {


    void addPageItem(List<PageItemDO> pageItemList) throws MainWebException;


    List<PageItemDTO> queryPageItem(Long categoryId) throws MainWebException;


    Integer deleteByPageId(Long id) throws MainWebException;

    void deleteByItemId(Long itemId)throws MainWebException;

    List<PageItemDTO> queryPageByItemId(Long itemId) throws MainWebException;
}
