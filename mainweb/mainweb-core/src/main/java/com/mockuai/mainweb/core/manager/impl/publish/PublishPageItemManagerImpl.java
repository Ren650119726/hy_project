package com.mockuai.mainweb.core.manager.impl.publish;

import com.mockuai.mainweb.common.constant.ResponseCode;
import com.mockuai.mainweb.common.domain.dto.PageItemDTO;
import com.mockuai.mainweb.core.dao.publish.PublishPageItemDAO;
import com.mockuai.mainweb.core.domain.PageItemDO;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.publish.PublishPageItemManager;
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
public class PublishPageItemManagerImpl implements PublishPageItemManager {

    @Resource
    private PublishPageItemDAO pageItemDAO;


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





}
