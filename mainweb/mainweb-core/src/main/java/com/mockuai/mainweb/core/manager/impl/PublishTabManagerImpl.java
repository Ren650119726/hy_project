package com.mockuai.mainweb.core.manager.impl;

import com.mockuai.mainweb.common.constant.ResponseCode;
import com.mockuai.mainweb.common.domain.dto.IndexPageDTO;
import com.mockuai.mainweb.common.domain.dto.PageIdNamePairDTO;
import com.mockuai.mainweb.common.domain.dto.PublishTabDTO;
import com.mockuai.mainweb.core.dao.PublishTabDAO;
import com.mockuai.mainweb.core.domain.PublishTabDO;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.PageManager;
import com.mockuai.mainweb.core.manager.PublishTabManager;
import com.mockuai.mainweb.core.util.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 */
@Service
public class PublishTabManagerImpl implements PublishTabManager {


    @Resource
    private PublishTabDAO publishTabDAO;
    @Resource
    private PageManager pageManager;


    @Override
    public void generateTab() throws MainWebException {
        List<IndexPageDTO> pageDTOs = pageManager.queryPublishPageNames();
        List<PageIdNamePairDTO> pageNames = new ArrayList<>();
        for (IndexPageDTO pageDTO : pageDTOs){
            PageIdNamePairDTO pageIdNamePair = new PageIdNamePairDTO();
            pageIdNamePair.setId(pageDTO.getId());
            pageIdNamePair.setName(pageDTO.getName());
            pageNames.add(pageIdNamePair);
        }
        String content = JsonUtil.toJson(pageNames);

        PublishTabDTO pubTab = getPublishTab();
        // pubTab.setContent(content);
        //每次对publish_tab都是先删除再添加

        //Long id = publishTabManager.confirmRecordExist();
        if(pubTab == null){
            pubTab = new PublishTabDTO();
            pubTab.setContent(content);
            addPublishTab(pubTab);
        }else{
            pubTab.setContent(content);
            updatePublishTab(pubTab);
        }

    }

    @Override
    public Long addPublishTab(PublishTabDTO publishTab) throws MainWebException {
        PublishTabDO publishTabDO  = new PublishTabDO();
        BeanUtils.copyProperties(publishTab,publishTabDO);
        try {
            return publishTabDAO.addPublishTab(publishTabDO);
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_INSERT,  e);
        }
    }

    @Override
    public PublishTabDTO getPublishTab() throws MainWebException {
        PublishTabDTO entity = null ;
        try {
            PublishTabDO publishTabDO = publishTabDAO.getPublishTab();
            if (publishTabDO!=null) {
                 entity = new PublishTabDTO();
                BeanUtils.copyProperties(publishTabDO, entity);
            }
            return entity;
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_QUERY,  e);
        }
    }

    @Override
    public Integer updatePublishTab(PublishTabDTO  publishTabDTO) throws MainWebException {
        PublishTabDO publishTabDO  = new PublishTabDO();
        BeanUtils.copyProperties(publishTabDTO,publishTabDO);
        try {
            return publishTabDAO.updatePublishTab(publishTabDO);
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_UPDATE,  e);
        }
    }


    @Override
    public List<PublishTabDTO> queryPageNameList() throws MainWebException {
        List<PublishTabDTO> publishTabDTOs = new ArrayList<>();
        try {
            List<PublishTabDO> publishTabDOs =   publishTabDAO.queryTabNames();
            for (PublishTabDO publishTabDO : publishTabDOs){
                PublishTabDTO publishTabDTO = new PublishTabDTO();
                BeanUtils.copyProperties(publishTabDO,publishTabDTO);
                publishTabDTOs.add(publishTabDTO);
            }
            return publishTabDTOs;
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_QUERY,  e);
        }
    }

    @Override
    public Long confirmRecordExist() throws MainWebException {
        try{
            Long count = publishTabDAO.confirmRecordExist();
            return count;
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_QUERY,  e);
        }
    }

}
