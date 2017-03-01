package com.mockuai.mainweb.core.manager.impl;

import com.mockuai.mainweb.common.constant.ResponseCode;
import com.mockuai.mainweb.common.domain.dto.PublishPageDTO;
import com.mockuai.mainweb.core.dao.PublishPageDAO;
import com.mockuai.mainweb.core.domain.PublishPageDO;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.PublishPageManager;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * Created by Administrator on 2016/9/21.
 */
@Service
public class PublishPageManagerImpl implements PublishPageManager {

    @Resource
    private PublishPageDAO publishPageDAO;


    @Override
    public Long addPublishPage(PublishPageDTO publishPageDTO) throws MainWebException {
        PublishPageDO publishPage  = new PublishPageDO();
        BeanUtils.copyProperties(publishPageDTO,publishPage);
        try {
            return publishPageDAO.addPublishPage(publishPage);
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_INSERT,  e);
        }
    }

    @Override
    public PublishPageDTO getPublishPage(Long id) throws MainWebException {
        PublishPageDTO publishPageDTO = new PublishPageDTO();
        try {
           PublishPageDO publishPageDO = publishPageDAO.getPublishPage(id);
            BeanUtils.copyProperties(publishPageDO,publishPageDTO);
            return publishPageDTO;
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_QUERY,  e);
        }
    }

    @Override
    public Integer deletePublishPage(Long id) throws MainWebException {
        PublishPageDTO publishPageDTO = new PublishPageDTO();
        try {
            return publishPageDAO.deletePublishPage(id);

        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_QUERY,  e);
        }
    }
}
