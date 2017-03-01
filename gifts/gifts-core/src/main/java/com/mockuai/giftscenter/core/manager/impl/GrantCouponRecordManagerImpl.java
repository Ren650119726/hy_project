package com.mockuai.giftscenter.core.manager.impl; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.giftscenter.common.domain.dto.GrantCouponRecordDTO;
import com.mockuai.giftscenter.common.domain.qto.GrantCouponRecordQTO;
import com.mockuai.giftscenter.core.dao.GrantCouponRecordDAO;
import com.mockuai.giftscenter.core.domain.GrantCouponRecordDO;
import com.mockuai.giftscenter.core.manager.GrantCouponRecordManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guansheng on 2016/7/18.
 */
@Service
public class GrantCouponRecordManagerImpl implements GrantCouponRecordManager {

    @Autowired
    private GrantCouponRecordDAO grantCouponRecordDAO;

    @Override
    public List<GrantCouponRecordDTO> queryAll(GrantCouponRecordQTO qto) {
        List<GrantCouponRecordDO> grantCouponRecordDOList = grantCouponRecordDAO.queryAll(qto);
        List<GrantCouponRecordDTO> grantCouponRecordDTOList = new ArrayList<>(grantCouponRecordDOList.size());
        for (GrantCouponRecordDO grantCouponRecordDO : grantCouponRecordDOList) {
            GrantCouponRecordDTO item = new GrantCouponRecordDTO();
            BeanUtils.copyProperties(grantCouponRecordDO, item);
            grantCouponRecordDTOList.add(item);
        }
        return grantCouponRecordDTOList;
    }

    @Override
    public void save(List<GrantCouponRecordDO> grantCouponRecordDO) {
        grantCouponRecordDAO.insert(grantCouponRecordDO);
    }

    @Override
    public int queryTotalCount(GrantCouponRecordQTO qto) {
        return grantCouponRecordDAO.queryTotalCount(qto);

    }
}
