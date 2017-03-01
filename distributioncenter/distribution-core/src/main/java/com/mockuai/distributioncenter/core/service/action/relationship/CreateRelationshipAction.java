package com.mockuai.distributioncenter.core.service.action.relationship;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.Operator;
import com.mockuai.distributioncenter.common.constant.RelationshipType;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.OperationDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerRelationshipDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerRelationshipManager;
import com.mockuai.distributioncenter.core.message.MessageProducer;
import com.mockuai.distributioncenter.core.message.Topicer;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.TransAction;

/**
 * Created by duke on 16/6/18.
 */
@Service
public class CreateRelationshipAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(CreateRelationshipAction.class);

    @Autowired
    private SellerRelationshipManager sellerRelationshipManager;
    @Autowired
    private MessageProducer messageProducer;
    @Autowired
    private Topicer topicer;
    
    @Override
    public DistributionResponse doTransaction(RequestContext context) throws DistributionException {
    	 Request request = context.getRequest();
         Long userId = (Long) request.getParam("userId");
         Long parentId = (Long) request.getParam("parentId");
         String appKey = (String) context.get("appKey");
         
         if (userId == null) {
             log.error("userId is null");
             throw new DistributionException(ResponseCode.PARAMETER_NULL);
         }
         
         if (parentId == null) {
             log.error("parentId is null");
             throw new DistributionException(ResponseCode.PARAMETER_NULL);
         }
         
         SellerRelationshipDTO relationshipDTO = sellerRelationshipManager.getByUserId(userId);
         if (relationshipDTO != null) {
             throw new DistributionException(ResponseCode.SERVICE_EXCEPTION, "已存在关系");
         }
         
         
         
         
         SellerRelationshipDTO relationshipDTO1 = new SellerRelationshipDTO();
         relationshipDTO1.setUserId(userId);
         relationshipDTO1.setParentId(parentId);
         relationshipDTO1.setType(RelationshipType.SELLER_TO_SELLER.getValue());
         // 预注册，关系状态为0
         relationshipDTO1.setStatus(1);
         Long id = sellerRelationshipManager.add(relationshipDTO1);
         
       // 异步处理直接下级的数量和团队的数量
       OperationDTO operationDTO = new OperationDTO();
       operationDTO.setAppKey(appKey);
       operationDTO.setOperator(Operator.ADD.getOp());
       operationDTO.setUserId(parentId);
       operationDTO.setDirectCount(1L);
       operationDTO.setGroupCount(1L);
       // 使用关系ID作为消息的唯一标识
       operationDTO.setMsgIdentify(String.valueOf(id));
       messageProducer.send(topicer.getValue(), "update", operationDTO.getMsgIdentify(), operationDTO);
       return new DistributionResponse(true);
    }

    @Override
    public String getName() {
        return ActionEnum.CREATE_RELATION_SHIP.getActionName();
    }
}
