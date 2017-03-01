package com.mockuai.suppliercenter.core.dao.impl;

import com.mockuai.suppliercenter.core.dao.MessageRecordDAO;
import com.mockuai.suppliercenter.core.domain.MessageRecordDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by duke on 15/11/19.
 */
@Service
public class MessageRecordDAOImpl extends SqlMapClientDaoSupport implements MessageRecordDAO {
    private static final Logger log = LoggerFactory.getLogger(MessageRecordDAOImpl.class);

    @Override
    public Long addRecord(MessageRecordDO messageRecordDO) {
        try {
            Long id = (Long) getSqlMapClientTemplate().insert("message_record.addRecord", messageRecordDO);
            return id;
        } catch (DataAccessException e) {
            log.error("add message record error, errMsg: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public MessageRecordDO getRecordByIdentifyAndBizType(String identify, Integer bizType) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("identify", identify);
        map.put("bizType", bizType);
        MessageRecordDO messageRecordDO =
                (MessageRecordDO) getSqlMapClientTemplate().queryForObject("message_record.getByIdentify", map);
        return messageRecordDO;
    }
}
