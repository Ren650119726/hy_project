package com.mockuai.mainweb.core.manager.publish;

import com.mockuai.mainweb.core.domain.PageGalleryDO;
import com.mockuai.mainweb.core.exception.MainWebException;

import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 */
public interface PublishPageGalleryManager {
    void addPageGallery(PageGalleryDO pageGalleryDO) throws MainWebException;

    void deletePageGallery(Long pageId) throws MainWebException;


    List<PageGalleryDO> queryPageGallery(Long blockId) throws MainWebException;
}
