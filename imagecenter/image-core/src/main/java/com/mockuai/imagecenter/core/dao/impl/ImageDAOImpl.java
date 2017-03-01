package com.mockuai.imagecenter.core.dao.impl;

import com.mockuai.imagecenter.common.domain.dto.ImageDTO;
import com.mockuai.imagecenter.core.dao.BaseDAO;
import com.mockuai.imagecenter.core.dao.ImageDAO;
import com.mockuai.imagecenter.core.domain.ImageDO;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;


/**
 * Created by 冠生 on 2016/5/24.
 */
@Repository
public class ImageDAOImpl  extends BaseDAO implements ImageDAO {
    @Override
    public void add(ImageDO imageDO) {
      getSqlMapClientTemplate().insert("image.add",imageDO);
    }

    public void deleteByKey(String key) {
        getSqlMapClientTemplate().update("image.deleteByKey",key);
    }

    @Override
    public void updateByKey(ImageDO imageDO) throws SQLException {
      getSqlMapClient().update("image.updateByKey",imageDO);
    }

    @Override
    public void deleteItemCodeByUserId(long userId) throws SQLException {
        getSqlMapClient().update("image.deleteItemCodeByUserId",userId);

    }

    @Override
    public void deleteItemCodeByItemId(long itemId) throws SQLException {
        getSqlMapClient().update("image.deleteItemCodeByItemId",itemId);

    }


    @Override
    public ImageDTO queryByKey(String key) throws SQLException {
        return (ImageDTO) getSqlMapClient().queryForObject("image.queryByKey",key);
    }


}
