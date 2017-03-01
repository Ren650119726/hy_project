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
public interface QrCodeImageGenerator {

     ImageDO genQRCode(String id,  String appKey) throws ImageException;


     ImageDO genQRCode(String id, String content, String appKey) throws ImageException;

}
