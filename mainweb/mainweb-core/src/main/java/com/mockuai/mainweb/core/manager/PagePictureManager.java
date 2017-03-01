package com.mockuai.mainweb.core.manager;

import com.mockuai.mainweb.common.domain.dto.PagePictureDTO;
import com.mockuai.mainweb.core.domain.PageGalleryDO;
import com.mockuai.mainweb.core.domain.PagePictureDO;
import com.mockuai.mainweb.core.exception.MainWebException;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by hsq on 2016/9/21.
 */
public interface PagePictureManager {
    void addPagePictureBlock(PagePictureDTO pagePictureDTO) throws MainWebException;

    void deletePagePicture(Long pageId) throws MainWebException;

    List<PagePictureDTO> queryPagePicture(Long blockId) throws MainWebException;
}
