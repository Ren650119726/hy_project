package com.mockuai.mainweb.core.manager;

import com.mockuai.mainweb.common.domain.dto.IndexPageDTO;
import com.mockuai.mainweb.common.domain.dto.PublishPageDTO;
import com.mockuai.mainweb.common.domain.dto.publish.ContentDTO;
import com.mockuai.mainweb.common.domain.qto.PageQTO;
import com.mockuai.mainweb.core.exception.MainWebException;

import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public interface PageManager {

    void addPage(IndexPageDTO indexPageDTO) throws MainWebException;

    void addBizPage(IndexPageDTO indexPageDTO) throws  MainWebException;


    void deletePage(Long id) throws MainWebException;

    void deleteBizPage(Long id)throws MainWebException;

    void updatePage(IndexPageDTO indexPageQTO) throws MainWebException;

    IndexPageDTO getPage(Long pageId) throws MainWebException;

    IndexPageDTO getPage(IndexPageDTO indexPageDTO, String appKey) throws MainWebException;

    List<IndexPageDTO> queryPublishPageNames() throws MainWebException;

    List<IndexPageDTO> queryPageNameList() throws MainWebException;


    PublishPageDTO generatePageJson(Long pageId, String appKey) throws MainWebException;
    List<IndexPageDTO> showPageList(PageQTO pageQTO) throws MainWebException;

    void cancelPage(Long id)throws MainWebException;

//    List<ContentDTO> previewPages(Long id ,String appKey) throws MainWebException;

}
