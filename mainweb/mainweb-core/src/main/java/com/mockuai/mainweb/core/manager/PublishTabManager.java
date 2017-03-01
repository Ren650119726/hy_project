package com.mockuai.mainweb.core.manager;

import com.mockuai.mainweb.common.domain.dto.IndexPageDTO;
import com.mockuai.mainweb.common.domain.dto.PublishPageDTO;
import com.mockuai.mainweb.common.domain.dto.PublishTabDTO;
import com.mockuai.mainweb.core.exception.MainWebException;

import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 */
public interface PublishTabManager {
    void generateTab() throws MainWebException;

    Long addPublishTab(PublishTabDTO publishTab) throws MainWebException;

    PublishTabDTO getPublishTab() throws MainWebException;

    Integer updatePublishTab(PublishTabDTO publishTabDTO) throws MainWebException;

    List<PublishTabDTO> queryPageNameList()throws MainWebException;

    Long confirmRecordExist()throws MainWebException;;




}
