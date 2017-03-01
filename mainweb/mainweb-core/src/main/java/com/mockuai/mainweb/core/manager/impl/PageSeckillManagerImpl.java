package com.mockuai.mainweb.core.manager.impl;

import com.mockuai.mainweb.common.constant.ResponseCode;
import com.mockuai.mainweb.common.domain.dto.PageSeckillDTO;
import com.mockuai.mainweb.core.dao.PageSeckillDAO;
import com.mockuai.mainweb.core.domain.PageSeckillDO;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.PageSeckillManager;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */
@Service
public class PageSeckillManagerImpl implements PageSeckillManager {
    @Resource
    PageSeckillDAO pageSeckillDAO;

    @Override
    public void addPageSeckill(PageSeckillDTO pageSeckillDTO) throws MainWebException {
        PageSeckillDO pageSeckillDO = new PageSeckillDO();
        BeanUtils.copyProperties(pageSeckillDTO,pageSeckillDO);
        try {
            pageSeckillDAO.addPageSeckill(pageSeckillDO);
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_INSERT,  e);
        }
    }

    @Override
    public void deletePageSeckill(Long pageId) throws MainWebException {
        try {
            pageSeckillDAO.deletePageSeckill(pageId);
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_DELETE,  e);
        }

    }

    @Override
    public List<PageSeckillDTO> queryPageSeckill(Long pageId) throws MainWebException {
        List<PageSeckillDTO> pageSeckillDTOs = new ArrayList<>();
        try {
            List<PageSeckillDO> pageSeckillDOs = pageSeckillDAO.getPageSeckill(pageId);
            for (PageSeckillDO pageSeckillDO : pageSeckillDOs){
                PageSeckillDTO pageSeckillDTO = new PageSeckillDTO();
                BeanUtils.copyProperties(pageSeckillDO,pageSeckillDTO);
                pageSeckillDTOs.add(pageSeckillDTO);
            }

            return pageSeckillDTOs;
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_QUERY,  e);
        }
    }

    @Override
    public List<String> queryPageSeckillTitles(Long pageId) throws MainWebException {
        try {
            List<String> pageSeckillTitles = pageSeckillDAO.queryPageSeckillTitles(pageId);
            return pageSeckillTitles;
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_QUERY,  e);
        }
    }
}
