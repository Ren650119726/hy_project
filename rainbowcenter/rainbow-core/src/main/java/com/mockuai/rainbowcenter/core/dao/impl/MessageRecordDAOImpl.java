package com.mockuai.rainbowcenter.core.dao.impl;

import com.mockuai.appcenter.common.util.JsonUtil;
import com.mockuai.rainbowcenter.core.dao.MessageRecordDAO;
import com.mockuai.rainbowcenter.core.domain.MessageRecordDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by duke on 15/12/23.
 */
public class MessageRecordDAOImpl extends SqlMapClientDaoSupport implements MessageRecordDAO {
    private static final Logger log = LoggerFactory.getLogger(MessageRecordDAOImpl.class);

    @Override
    public Long addRecord(MessageRecordDO messageRecordDO) {
        try {
            Long id = (Long) getSqlMapClientTemplate().insert("message_record.addRecord", messageRecordDO);
            return id;
        } catch (DataAccessException e) {
            log.error("add message record error, errMsg: {}, messageRecordDO: {}",
                    e.getMessage(), JsonUtil.toJson(messageRecordDO));
            return null;
        }
    }

    @Override
    public MessageRecordDO getRecordByIdentifyAndBizType(String identify, Integer bizType) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("identify", identify);
        map.put("bizType", bizType);
        try {
            MessageRecordDO messageRecordDO =
                    (MessageRecordDO) getSqlMapClientTemplate().queryForObject("message_record.getByIdentify", map);
            return messageRecordDO;
        } catch (DataAccessException e) {
            log.error("get record by identify and bizCode error, errMsg: {}, identify: {}. bizType: {}",
                    e.getMessage(), identify, bizType);
            return null;
        }
    }

    @Override
    public Integer updateRecord(Long id, int bizType) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("bizType", bizType);

        try {
            return this.getSqlMapClientTemplate().update("message_record.updateMessageBizType", map);
        } catch (Exception e) {
            log.error("update message biztype error, errMsg: {}, id: {}, bizType: {}", e.getMessage(), id, bizType);
            return null;
        }

    }
}
