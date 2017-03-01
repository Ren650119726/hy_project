package com.mockuai.distributioncenter.core.dao.impl;

import com.mockuai.distributioncenter.core.dao.BaseDAO;
import com.mockuai.distributioncenter.core.dao.MessageRecordDAO;
import com.mockuai.distributioncenter.core.domain.MessageRecordDO;
import com.mockuai.distributioncenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by duke on 16/2/19.
 */
@Repository
public class MessageRecordDAOImpl extends BaseDAO implements MessageRecordDAO {
    private static final Logger log = LoggerFactory.getLogger(MessageRecordDAOImpl.class);

    @Override
    public Long addRecord(MessageRecordDO messageRecordDO) {
        log.info("add record: {}", JsonUtil.toJson(messageRecordDO));
        return (Long) getSqlMapClientTemplate().insert("message_record.addRecord", messageRecordDO);
    }

    @Override
    public MessageRecordDO getRecordByIdentifyAndBizType(String identify, Integer bizType) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("identify", identify);
        map.put("bizType", bizType);
        log.info("get record by condition: {}", JsonUtil.toJson(map));
        return (MessageRecordDO) getSqlMapClientTemplate().queryForObject("message_record.getByIdentify", map);
    }
}
