package com.mockuai.distributioncenter.core.service.action.message;

import com.aliyun.openservices.ons.api.Message;
import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.BizType;
import com.mockuai.distributioncenter.common.constant.Operator;
import com.mockuai.distributioncenter.common.domain.dto.MessageRecordDTO;
import com.mockuai.distributioncenter.common.domain.dto.OperationDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerRelationshipDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.MessageRecordManager;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.manager.SellerRelationshipManager;
import com.mockuai.distributioncenter.core.message.msg.SellerCountUpdateMsg;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.TransAction;
import com.mockuai.distributioncenter.core.service.action.distribution.finder.Finder;
import com.mockuai.distributioncenter.core.service.action.distribution.finder.SimpleFinder;
import com.mockuai.distributioncenter.core.service.action.distribution.finder.checker.Checker;
import com.mockuai.distributioncenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by duke on 16/5/20.
 */
@Service
public class UpdateSellerCountListenerAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(UpdateSellerCountListenerAction.class);

    @Resource
    private SellerRelationshipManager sellerRelationshipManager;

    @Resource
    private SellerManager sellerManager;

    @Resource
    private MessageRecordManager messageRecordManager;

    @Override
    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        SellerCountUpdateMsg sellerCountUpdateMsg = (SellerCountUpdateMsg) request.getParam("sellerCountUpdateMsg");

        Message msg = sellerCountUpdateMsg.getMsg();
        OperationDTO operationDTO = sellerCountUpdateMsg.getOperationDTO();

        // 检查消息是否已经被消费过了，保证消息处理幂等
        MessageRecordDTO messageRecordDTO = messageRecordManager.getRecordByIdentifyAndBizType(
                String.valueOf(operationDTO.getMsgIdentify()) /* 使用order ID作为唯一标识，保证消息唯一性 */,
                BizType.UPDATE_SELLER_RELATIONSHIP.getValue()
        );

        if (messageRecordDTO != null) {
            // 消息已经被消费过
            log.warn("reduplicate message msgId: {}", msg.getMsgID());
            return new DistributionResponse(true);
        }

        Finder<SellerDTO> finder = new SimpleFinder(sellerRelationshipManager, sellerManager);
        SellerDTO sellerDTO = sellerManager.getByUserId(operationDTO.getUserId());
        SellerRelationshipDTO relationshipDTO = sellerRelationshipManager.getByUserId(operationDTO.getUserId());
        if (operationDTO.getOperator().equals(Operator.ADD.getOp())) {
            sellerDTO.setDirectCount(sellerDTO.getDirectCount() + operationDTO.getDirectCount());
            log.info("add direct count: {} on seller: {}", operationDTO.getDirectCount(), JsonUtil.toJson(sellerDTO));
            sellerDTO.setGroupCount(sellerDTO.getGroupCount() + operationDTO.getGroupCount());
            log.info("add group count: {} on seller: {}", operationDTO.getDirectCount(), JsonUtil.toJson(sellerDTO));
        } else {
            sellerDTO.setDirectCount(sellerDTO.getDirectCount() - operationDTO.getDirectCount());
            log.info("sub direct count: {} on seller: {}", operationDTO.getGroupCount(), JsonUtil.toJson(sellerDTO));
            sellerDTO.setGroupCount(sellerDTO.getGroupCount() - operationDTO.getGroupCount());
            log.info("sub group count: {} on seller: {}", operationDTO.getGroupCount(), JsonUtil.toJson(sellerDTO));
        }
        sellerManager.update(sellerDTO);
        if (relationshipDTO.getParentId() == 0) {
            // 如果父级为空，则不再去查找父级
            sellerDTO.setMaster(true);
        }
        while (!sellerDTO.isMaster()) {
            sellerDTO = finder.find(sellerDTO, new Checker<SellerDTO>() {
                @Override
                public boolean check(SellerDTO o1, SellerDTO o2) {
                    // 不对上级进行筛选
                    return true;
                }
            });
            if (operationDTO.getOperator().equals(Operator.ADD.getOp())) {
                sellerDTO.setGroupCount(sellerDTO.getGroupCount() + operationDTO.getGroupCount());
                log.info("add group count: {} on seller: {}", operationDTO.getGroupCount(), JsonUtil.toJson(sellerDTO));
            } else {
                sellerDTO.setGroupCount(sellerDTO.getGroupCount() - operationDTO.getGroupCount());
                log.info("sub group count: {} on seller: {}", operationDTO.getGroupCount(), JsonUtil.toJson(sellerDTO));
            }
            sellerManager.update(sellerDTO);
        }

        // 记录已经成功消费的消息
        messageRecordDTO = new MessageRecordDTO();
        messageRecordDTO.setMsgId(msg.getMsgID());
        messageRecordDTO.setIdentify(String.valueOf(operationDTO.getMsgIdentify()));
        messageRecordDTO.setBizType(BizType.UPDATE_SELLER_RELATIONSHIP.getValue());
        messageRecordManager.addRecord(messageRecordDTO);
        return new DistributionResponse(true);
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_SELLER_COUNT.getActionName();
    }
}
