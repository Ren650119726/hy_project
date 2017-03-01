package com.mockuai.distributioncenter.core.dao.impl;

import com.mockuai.distributioncenter.common.domain.qto.DistRecordQTO;
import com.mockuai.distributioncenter.core.dao.BaseDAO;
import com.mockuai.distributioncenter.core.dao.DistRecordDAO;
import com.mockuai.distributioncenter.core.domain.DistRecordDO;
import com.mockuai.distributioncenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by duke on 16/3/11.
 */
@Repository
public class DistRecordDAOImpl extends BaseDAO implements DistRecordDAO {
    private final static Logger log = LoggerFactory.getLogger(DistRecordDAOImpl.class);

    @Override
    public Long add(DistRecordDO distRecordDO) {
        Long id = (Long)getSqlMapClientTemplate().insert(
                "dist_record.add", distRecordDO);
        return id;
    }

    @Override
    public List<DistRecordDO> query(DistRecordQTO distRecordQTO) {
        List<DistRecordDO> result = getSqlMapClientTemplate().queryForList("dist_record.query", distRecordQTO);
        return result;
    }

    @Override
    public Integer update(DistRecordDO distRecordDO) {
        return getSqlMapClientTemplate().update(
                "dist_record.update", distRecordDO);
    }

    @Override
    public List<Long> queryValuableOrderIds(DistRecordQTO distRecordQTO) {
        List<Long> orderIds = getSqlMapClientTemplate().queryForList("dist_record.queryValuableOrderIds", distRecordQTO);
        return orderIds;
    }
    
    @Override
    public  List<DistRecordDO> queryStatistics(DistRecordQTO distRecordQTO){
    	List<DistRecordDO> result = getSqlMapClientTemplate().queryForList("dist_record.queryStatistics", distRecordQTO);
        return result;
    }

    @Override
    public Long getAmountBySellerId(DistRecordDO distRecordDO) {
        return (Long)getSqlMapClientTemplate().queryForObject("dist_record.getAmountBySellerId",distRecordDO);
    }
}
