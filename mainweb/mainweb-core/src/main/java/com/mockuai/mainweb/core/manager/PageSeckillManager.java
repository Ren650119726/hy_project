package com.mockuai.mainweb.core.manager;

import com.mockuai.mainweb.common.domain.dto.PageSeckillDTO;
import com.mockuai.mainweb.core.exception.MainWebException;

import java.util.List;


/**
 * Created by Administrator on 2016/10/10.
 */
public interface PageSeckillManager {
    void addPageSeckill(PageSeckillDTO pageSeckillDTO) throws MainWebException;

    void deletePageSeckill(Long pageId) throws MainWebException;

    List<PageSeckillDTO> queryPageSeckill(Long pageId) throws MainWebException;

    List<String> queryPageSeckillTitles(Long pageId) throws MainWebException;
}
