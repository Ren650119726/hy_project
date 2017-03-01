package com.mockuai.imagecenter.core.manager; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.imagecenter.core.domain.ImageDO;
import com.mockuai.imagecenter.core.exception.ImageException;

/**
 * Created by hy on 2016/6/7.
 */
public interface QrCodeItemGenerator {

        ImageDO genQRCode(String id,String sellerId,Long shareUserId,String bizcode,  String appKey) throws ImageException;
        void genQRCode(Long itemId, String urlTpl, String appKey) throws ImageException;


}
