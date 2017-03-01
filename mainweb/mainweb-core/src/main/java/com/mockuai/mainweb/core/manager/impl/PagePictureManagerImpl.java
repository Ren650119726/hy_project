package com.mockuai.mainweb.core.manager.impl;

import com.mockuai.mainweb.common.constant.ResponseCode;
import com.mockuai.mainweb.common.domain.dto.PagePictureDTO;
import com.mockuai.mainweb.core.dao.PagePictureDAO;
import com.mockuai.mainweb.core.domain.PagePictureDO;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.PagePictureManager;
import com.mockuai.mainweb.core.util.ImageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangsiqian  on 2016/12/19.
 */
@Service
public class PagePictureManagerImpl implements PagePictureManager {

    @Resource
    private PagePictureDAO pagePictureDAO;

    @Override
    public void addPagePictureBlock(PagePictureDTO pagePictureDTO) throws MainWebException {
        try {
            PagePictureDO pagePictureDO = new PagePictureDO();
// TODO: 2016/12/19
            BufferedImage bufferedImage = ImageUtil.getBufferedImage(pagePictureDTO.getImageUrl());
            pagePictureDTO.setWidth(bufferedImage.getWidth());
            pagePictureDTO.setHeight(bufferedImage.getHeight());
            BeanUtils.copyProperties(pagePictureDTO,pagePictureDO);
            pagePictureDAO.addPagePicture(pagePictureDO);
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_INSERT,  e);
        }
    }

    @Override
    public void deletePagePicture(Long pageId) throws MainWebException {
        try {
            pagePictureDAO.deletePagePicture(pageId);
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_INSERT,  e);
        }
    }

    @Override
    public List<PagePictureDTO> queryPagePicture(Long blockId) throws MainWebException {
        List<PagePictureDTO> pagePictureDTOList = null;
        try {
            pagePictureDTOList = new ArrayList<>();
            List<PagePictureDO> pagePictureDOList = pagePictureDAO.queryPagePicture(blockId);
            for (PagePictureDO pagePictureDO : pagePictureDOList){
                PagePictureDTO pagePictureDTO = new PagePictureDTO();
                BeanUtils.copyProperties(pagePictureDO,pagePictureDTO);
                pagePictureDTOList.add(pagePictureDTO);
            }
            return pagePictureDTOList;
        } catch (SQLException e) {
            throw new MainWebException(ResponseCode.SYS_E_DB_QUERY,e);
        }
    }
}
