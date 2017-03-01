package com.mockuai.itemcenter.core.manager; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.itemcenter.common.domain.mop.MopBaseItemDTO;
import com.mockuai.itemcenter.core.exception.ItemException;

/**
 * Created by hy on 2016/12/16.
 */
public interface PublishItemManager {



    MopBaseItemDTO publish(Long itemId,String appKey,String bizCode,Boolean needWritePage) throws ItemException;

}
