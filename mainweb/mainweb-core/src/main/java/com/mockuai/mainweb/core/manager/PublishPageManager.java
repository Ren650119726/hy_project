package com.mockuai.mainweb.core.manager;

import com.mockuai.mainweb.common.domain.dto.PublishPageDTO;
import com.mockuai.mainweb.core.exception.MainWebException;

/**
 * Created by Administrator on 2016/9/21.
 */
public interface PublishPageManager {

    Long addPublishPage(PublishPageDTO publishPageDTO) throws MainWebException;

    PublishPageDTO getPublishPage(Long  id) throws MainWebException;

    Integer deletePublishPage(Long id) throws MainWebException;
}
