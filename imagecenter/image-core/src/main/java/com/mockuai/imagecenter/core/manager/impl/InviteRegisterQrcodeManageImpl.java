package com.mockuai.imagecenter.core.manager.impl;

import com.alibaba.fastjson.JSON;
import com.mockuai.imagecenter.common.constant.ResponseCode;
import com.mockuai.imagecenter.core.domain.BizType;
import com.mockuai.imagecenter.core.domain.ImageDO;
import com.mockuai.imagecenter.core.domain.QrcodeConfigDO;
import com.mockuai.imagecenter.core.domain.QrcodeConfigDetailDO;
import com.mockuai.imagecenter.core.exception.ImageException;
import com.mockuai.imagecenter.core.manager.QRCodeManager;
import com.mockuai.imagecenter.core.manager.QrCodeImageGenerator;
import com.mockuai.usercenter.client.UserClient;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by lizg on 2016/11/30.
 */
public class InviteRegisterQrcodeManageImpl extends QRCodeManager implements QrCodeImageGenerator {

    @Autowired
    private UserClient userClient;

    private String defaultInviteRegisterUrl ;

    public void setDefaultInviteRegisterUrl(String defaultInviteRegisterUrl) {
        this.defaultInviteRegisterUrl = defaultInviteRegisterUrl;
    }

    @Override
    public ImageDO genQRCode(String id, String appKey) throws ImageException {
        return genQRCode(id,null,appKey);
    }

    @Override
    public ImageDO genQRCode(String id, String content, String appKey) throws ImageException {
        Long userId = Long.parseLong(id);

        UserDTO userDTO;
        Response<UserDTO> userDTOResponse =  userClient.getUserById(userId,appKey);

        if (userDTOResponse.isSuccess()) {
             userDTO = userDTOResponse.getModule();
        } else {
            log.error("get user error,user id: {}", userId);
            throw new ImageException(ResponseCode.SYS_E_SERVICE_EXCEPTION, userDTOResponse.getMessage());
        }
        String nickName = userDTO.getNickName();

        String url = defaultInviteRegisterUrl.replace("%shareUserId%",userDTO.getId()+"");

        String tpl = "您的好友%s,邀请您加入嗨云";

        String target = nickName.length() > 5  ? nickName.substring(0,5):nickName;

        nickName =String.format(tpl,target);

        try {
            QrcodeConfigDO qrcodeConfigDO = qrcodeConfigDAO.queryByBizType(BizType.INVITEREGISTER.name());

            List<QrcodeConfigDetailDO> qrcodeConfigDetailDOList =
                    JSON.parseArray(qrcodeConfigDO.getConfigInfo(), QrcodeConfigDetailDO.class);

            InviteRegisterRender inviteRegisterRender = new InviteRegisterRender(id,qrcodeConfigDetailDOList,url,qrcodeConfigDO.getBgImage(),true);
            inviteRegisterRender.renderInviteRegister(nickName);
            return generateImageDO(inviteRegisterRender,userId,INVITEREGISTER,url);

        } catch (SQLException e) {
            log.error(ResponseCode.SYS_E_DB_INSERT.getComment(),e);
            throw  new ImageException(ResponseCode.SYS_E_DB_INSERT,e);
        }catch (IOException e) {
            log.error(ResponseCode.GEN_IMAGE_ERROR.getComment(),e);
            throw  new ImageException(ResponseCode.GEN_IMAGE_ERROR,e);
        }

    }
}
