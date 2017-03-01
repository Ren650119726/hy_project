package com.mockuai.imagecenter.core.util; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.appcenter.client.AppClient;
import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.imagecenter.common.domain.dto.ImageItemDTO;

/**
 * Created by guansheng on 2016/8/2.
 */
public class ImageUtil {

    private static  AppClient appClient = (AppClient) ApplicationAware.getBean("appClient");

    private final static  String bizcode = "hanshu";

    public static String getItemUrlTpl(ImageItemDTO imageItemDTO){


        String domain = appClient.getAppInfoByType(bizcode, AppTypeEnum.APP_WAP).getModule().getDomainName();

        //例子  http://m.haiyun.com/detail.html?item_uid=1841254_29065&distributor_id=19

        String url = "http://" + domain + "/detail.html?item_uid=%d_%d";
        if(imageItemDTO.getSeckill()){
            url += "&item_type=13";
        }
        if(imageItemDTO.getShareUserId() != null){
            url+= "&share_user_id="+imageItemDTO.getShareUserId();
        }
        return String.format(url,imageItemDTO.getSellerId(),imageItemDTO.getItemId()) ;
    }



}
