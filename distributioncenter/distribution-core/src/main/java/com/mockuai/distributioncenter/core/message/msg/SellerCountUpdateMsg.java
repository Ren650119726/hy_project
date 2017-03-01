package com.mockuai.distributioncenter.core.message.msg;

import com.aliyun.openservices.ons.api.Message;
import com.mockuai.distributioncenter.common.domain.dto.OperationDTO;

/**
 * Created by duke on 16/5/20.
 */
public class SellerCountUpdateMsg {
    private OperationDTO operationDTO;
    /**
     * 原始消息体
     * */
    private Message msg;

    public OperationDTO getOperationDTO() {
        return operationDTO;
    }

    public void setOperationDTO(OperationDTO operationDTO) {
        this.operationDTO = operationDTO;
    }

    public Message getMsg() {
        return msg;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }
}
