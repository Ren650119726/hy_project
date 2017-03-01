package com.mockuai.mainweb.core.manager.impl;

import com.mockuai.mainweb.common.constant.ResponseCode;
import com.mockuai.mainweb.common.domain.dto.PageBlockDTO;
import com.mockuai.mainweb.core.dao.PageBlockDAO;
import com.mockuai.mainweb.core.domain.PageBlockDO;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.PageBlockManager;
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
public class PageBlockManagerImpl implements PageBlockManager {
    @Resource
    private PageBlockDAO pageBlockDAO;

    @Override
    public void addPageBlock(PageBlockDTO pageBlock) throws MainWebException {
        PageBlockDO pageBlockDO = new PageBlockDO();
        BeanUtils.copyProperties(pageBlock,pageBlockDO);
        try {
            pageBlockDAO.addPageBlock(pageBlockDO);
            pageBlock.setId(pageBlockDO.getId());
        } catch (SQLException e) {

            throw  new MainWebException(ResponseCode.SYS_E_DB_INSERT,  e);
        }
    }

    @Override
    public void addPageBlockList(List<PageBlockDTO> pageBlockList) throws MainWebException {
        List<PageBlockDO> pageBlockDOList = new ArrayList<>(pageBlockList.size());
        for(PageBlockDTO pageBlockDTO : pageBlockList){
            PageBlockDO pageBlockDO = new PageBlockDO();
            BeanUtils.copyProperties(pageBlockDTO,pageBlockDO);
            pageBlockDOList.add(pageBlockDO);
        }
        try {
            pageBlockDAO.addPageBlockList(pageBlockDOList);
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_INSERT,  e);

        }
    }

    @Override
    public void deletePageBlock(Long pageId) throws MainWebException {
        try {
            pageBlockDAO.deletePageBlock(pageId);
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_DELETE,  e);
        }
    }

    @Override
    public List<PageBlockDO> queryPageBlock(Long pageId) throws MainWebException {

        try {
            return pageBlockDAO.queryPageBlock(pageId);
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_QUERY,  e);
        }
    }


}
