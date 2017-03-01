package com.mockuai.imagecenter.core.dao.impl; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.imagecenter.core.dao.BaseDAO;
import com.mockuai.imagecenter.core.dao.QrcodeConfigDAO;
import com.mockuai.imagecenter.core.domain.QrcodeConfigDO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * Created by 冠生 on 2016/5/24.
 */
@Service
public class QrcodeConfigDAOImpl extends BaseDAO implements QrcodeConfigDAO {
    @Override
    public void add(QrcodeConfigDO qrcodeConfigDO) throws SQLException {
        getSqlMapClient().insert("qrcode_config.add",qrcodeConfigDO);
    }

    @Override
    public QrcodeConfigDO queryByBizType(String bizType) throws SQLException {
        return (QrcodeConfigDO) getSqlMapClient().queryForObject("qrcode_config.queryByBizType",bizType);
    }
}
