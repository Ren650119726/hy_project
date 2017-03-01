package com.mockuai.imagecenter.core.dao;

import com.mockuai.imagecenter.core.domain.QrcodeConfigDO;

import java.sql.SQLException;

/**
 * Created by 冠生 on 2016/5/24.
 */
public interface QrcodeConfigDAO {

    void add(QrcodeConfigDO qrcodeConfigDO) throws SQLException;

    QrcodeConfigDO queryByBizType(String bizType) throws SQLException;

}
