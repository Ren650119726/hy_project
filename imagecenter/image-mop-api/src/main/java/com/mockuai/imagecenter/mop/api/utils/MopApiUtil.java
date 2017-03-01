package com.mockuai.imagecenter.mop.api.utils;

import com.mockuai.imagecenter.common.domain.dto.ImageDTO;
import com.mockuai.imagecenter.mop.api.domain.MopImageDTO;

/**
 * Created by lizg on 15/10/28.
 */
public class MopApiUtil {

    public static MopImageDTO getMopSellerDTO(ImageDTO imageDTO) {
        MopImageDTO mopImageDTO = new MopImageDTO();
        mopImageDTO.setImageUrl(imageDTO.getImageUrl());
        mopImageDTO.setContent(imageDTO.getContent());
        mopImageDTO.setUserId(imageDTO.getUserId());
        return mopImageDTO;
    }

}
