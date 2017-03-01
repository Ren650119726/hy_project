package com.mockuai.mainweb.core.manager.publish;

import com.mockuai.mainweb.common.domain.dto.IndexPageDTO;
import com.mockuai.mainweb.common.domain.dto.PublishIndexPageDTO;
import com.mockuai.mainweb.common.domain.dto.PublishPageDTO;
import com.mockuai.mainweb.core.exception.MainWebException;

import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public interface PublishIndexPageManager {

    void addPage(PublishIndexPageDTO indexPageDTO) throws MainWebException;


    void addBizPage(PublishIndexPageDTO indexPageDTO) throws MainWebException;

    void deletePage(Long id) throws MainWebException;

    void deleteBizPage(Long id)throws MainWebException;


    IndexPageDTO getPage(Long pageId) throws MainWebException;

    List<IndexPageDTO> queryPublishPageNames() throws MainWebException;

    List<IndexPageDTO> queryPageNameList() throws MainWebException;


    PublishPageDTO generatePageJson(Long pageId, String appKey) throws MainWebException;
}
