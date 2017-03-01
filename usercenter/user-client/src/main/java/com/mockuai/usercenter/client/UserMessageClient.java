package com.mockuai.usercenter.client;

import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.UserMessageDTO;
import com.mockuai.usercenter.common.qto.UserMessageQTO;
import java.util.List;

public interface UserMessageClient {
    public Response<Boolean> addUserMessage(List<UserMessageDTO> userMessageDTOs, String appKey);

    public Response<List<UserMessageDTO>> queryUserMessage(UserMessageQTO userMessageQTO, String appKey);
}
