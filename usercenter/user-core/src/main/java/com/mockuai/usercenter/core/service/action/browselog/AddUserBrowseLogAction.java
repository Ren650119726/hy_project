package com.mockuai.usercenter.core.service.action.browselog;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.dto.UserBrowseLogDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserBrowseLogManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;

@Service
public class AddUserBrowseLogAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(AddUserBrowseLogAction.class);

	@Resource
	private UserBrowseLogManager userBrowseLogManager;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public UserResponse execute(RequestContext context) throws UserException {
		UserRequest request = context.getRequest();
		
		Long userId = (Long)request.getParam("user_id");//用户id
        String nickName = (String)request.getParam("nick_name");//用户昵称
        Long storeId = (Long)request.getParam("store_id");//店铺id
        Long goodsId = (Long)request.getParam("goods_id");//商品id

		if (userId == null) {
			log.error("userId is null when add user access history");
		}
		
		if (nickName == null) {
			log.error("nickName is null when add user access history");
		}
		
		if (storeId == null) {
			log.error("storeId is null when add user access history");
		}
		
		if (goodsId == null) {
			log.error("goodsId is null when add user access history");
		}
	

		UserBrowseLogDTO userBrowseLog= userBrowseLogManager.addUserBrowseLog(userId, nickName, storeId, goodsId);

		return new UserResponse(userBrowseLog);
	}

	public String getName() {
		return ActionEnum.ADDUSERBROWSELOG.getActionName();
	}

}
