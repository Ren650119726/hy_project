package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.qto.FreightTemplateAreaInfoQTO;
import com.mockuai.itemcenter.core.dao.FreightTemplateAreaInfoDAO;
import com.mockuai.itemcenter.core.domain.FreightTemplateAreaInfoDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yindingyu on 15/10/20.
 */
@Repository
public class FreightTemplateAreaInfoDAOImpl extends SqlMapClientDaoSupport implements FreightTemplateAreaInfoDAO {

    @Override
    public Long addAreaInfo(FreightTemplateAreaInfoDO areaInfoDO) {
        return (Long) getSqlMapClientTemplate().insert("FreightTemplateAreaInfoDAO.add",areaInfoDO);
    }

    @Override
    public Integer deleteAreaInfoByTemplateId(FreightTemplateAreaInfoQTO areaInfoQTO) {
        return getSqlMapClientTemplate().delete("FreightTemplateAreaInfoDAO.delete",areaInfoQTO);
    }

    @Override
    public List<FreightTemplateAreaInfoDO> queryAreaTemplate(FreightTemplateAreaInfoQTO areaInfoQTO) {
        return getSqlMapClientTemplate().queryForList("FreightTemplateAreaInfoDAO.query",areaInfoQTO);
    }
}
