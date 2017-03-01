package com.mockuai.mainweb.core.manager.impl;

import com.mockuai.mainweb.common.constant.ResponseCode;
import com.mockuai.mainweb.common.domain.dto.PageItemDTO;
import com.mockuai.mainweb.core.dao.PageItemDAO;
import com.mockuai.mainweb.core.domain.PageItemDO;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.PageItemManager;
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
public class PageItemManagerImpl implements PageItemManager {

    @Resource
    private PageItemDAO pageItemDAO;


    @Override
    public void addPageItem(List<PageItemDO> pageItemList) throws MainWebException {

        try {
            pageItemDAO.addPageItem(pageItemList);
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_INSERT,  e);
        }
    }

    @Override
    public List<PageItemDTO> queryPageItem(Long categoryId  ) throws MainWebException {
        try {
            List<PageItemDTO> pageItemList = new ArrayList<>();
            List<PageItemDO> pageItemDOList = pageItemDAO.queryPageItem(categoryId);
            for(PageItemDO item : pageItemDOList){
               PageItemDTO target = new PageItemDTO();
                BeanUtils.copyProperties(item,target);
                pageItemList.add(target);
            }
            return pageItemList;
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_QUERY,  e);
        }
    }

    @Override
    public Integer deleteByPageId(Long id) throws MainWebException {
        try {
            return  pageItemDAO.deleteByPageId(id);
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_DELETE,  e);
        }
    }


    @Override
    public void deleteByItemId(Long itemId) throws MainWebException {
        try {
             pageItemDAO.deleteByItemId(itemId);
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_DELETE,  e);
        }
    }

    @Override
    public List<PageItemDTO> queryPageByItemId(Long itemId) throws MainWebException {
        try {
            List<PageItemDTO> pageItemList = new ArrayList<>();
            List<PageItemDO> pageItemDOList = pageItemDAO.queryPageByItemId(itemId);
            for(PageItemDO item : pageItemDOList){
                PageItemDTO target = new PageItemDTO();
                BeanUtils.copyProperties(item,target);
                pageItemList.add(target);
            }
            return pageItemList;
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_QUERY,  e);
        }
    }
}
