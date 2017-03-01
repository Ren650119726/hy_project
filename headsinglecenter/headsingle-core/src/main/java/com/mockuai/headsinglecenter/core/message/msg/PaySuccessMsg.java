package com.mockuai.headsinglecenter.core.message.msg;

import com.aliyun.openservices.ons.api.Message;
import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleInfoDTO;
import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleUserDTO;

/**
 * Created by duke on 16/3/11.
 */
public class PaySuccessMsg {
    private Message msg;
    private HeadSingleUserDTO headSingleUserDTO;
    private HeadSingleInfoDTO headSingleInfoDTO;
    private String appKey;

    public Message getMsg() {
        return msg;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

	public HeadSingleUserDTO getHeadSingleUserDTO() {
		return headSingleUserDTO;
	}

	public void setHeadSingleUserDTO(HeadSingleUserDTO headSingleUserDTO) {
		this.headSingleUserDTO = headSingleUserDTO;
	}

	public HeadSingleInfoDTO getHeadSingleInfoDTO() {
		return headSingleInfoDTO;
	}

	public void setHeadSingleInfoDTO(HeadSingleInfoDTO headSingleInfoDTO) {
		this.headSingleInfoDTO = headSingleInfoDTO;
	}
}
