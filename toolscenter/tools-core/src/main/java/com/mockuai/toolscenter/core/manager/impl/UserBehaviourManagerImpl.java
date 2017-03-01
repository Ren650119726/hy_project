package com.mockuai.toolscenter.core.manager.impl; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.toolscenter.common.domain.UserBehaviourDTO;
import com.mockuai.toolscenter.common.util.JsonUtil;
import com.mockuai.toolscenter.core.dao.UserBehaviourDAO;
import com.mockuai.toolscenter.core.domain.UserBehaviourDO;
import com.mockuai.toolscenter.core.exception.ToolsException;
import com.mockuai.toolscenter.core.manager.UserBehaviourManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by guansheng on 2016/10/18.
 */
@Service
public class UserBehaviourManagerImpl implements UserBehaviourManager {


       private Logger logger = LoggerFactory.getLogger(UserBehaviourManagerImpl.class);
    @Autowired
    private UserBehaviourDAO userBehaviourDAO;

    @Override
    public void addUserBehaviour(UserBehaviourDTO userBehaviourDTO) throws ToolsException {
           UserBehaviourDO userBehaviourDO = new UserBehaviourDO();

        try {

            BeanUtils.copyProperties(userBehaviourDTO,userBehaviourDO);
            userBehaviourDAO.addBeHaviour(userBehaviourDO);

        } catch (Exception e) {
            logger.error("userBehaviourDTO:{}, addUserBehaviour cause exception:{}", JsonUtil.toJson(userBehaviourDTO),e);
            //throw  new ToolsException(ResponseCode.SYS_E_DATABASE_ERROR,e.getMessage());
        }
    }
}
