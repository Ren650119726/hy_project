package com.mockuai.mainweb.core.message.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.mainweb.common.api.MainWebService;
import com.mockuai.mainweb.common.api.action.BaseRequest;
import com.mockuai.mainweb.common.api.action.MainWebResponse;
import com.mockuai.mainweb.common.api.action.Request;
import com.mockuai.mainweb.common.constant.ActionEnum;
import com.mockuai.mainweb.common.domain.dto.PageItemDTO;
import com.mockuai.mainweb.common.domain.dto.PublishPageDTO;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.AppManager;
import com.mockuai.mainweb.core.manager.PageItemManager;
import com.mockuai.mainweb.core.manager.PublishPageManager;
import com.mockuai.mainweb.core.util.AppKeyEnum;
import com.mockuai.mainweb.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import java.util.List;

/**
 * Created by 冠生 on 15/11/18.
 */
public class ItemDeleteListener implements MessageListener {
    private static final Logger log = LoggerFactory.getLogger(ItemDeleteListener.class);
    private static final String BIZ_CODE = "hanshu";

//    @Autowired
//    private MainWebResponse mainWebResponse;

    @Autowired
    private MainWebService mainWebService;

    @Autowired
    private PageItemManager pageItemManager;

//    @Autowired
//    private PublishPageManager publishPageManager;

    @Autowired
    private AppManager appManager;


    @Override
    public Action consume(Message msg, ConsumeContext consumeContext) {



        log.info("Enter [{}]: ", getClass().getName());

        log.info("收到了消息 msg : {}", new String(msg.getBody()));

        if (msg.getTag() != null && msg.getTopic().equals("item-delete")) {

            log.info("msg topic = {}, tag = {}, body = {}", msg.getTopic(), msg.getTag(), new String(msg.getBody()).toString());

//            ItemDTO itemDTO = JSONObject.parseObject(new String(msg.getBody()),ItemDTO.class);
            Long itemId = JSONObject.parseObject(new String(msg.getBody()),Long.class);

            if (itemId!=null){
                try {
                    AppInfoDTO appInfoDTO = appManager.getAppInfoByType(BIZ_CODE,AppTypeEnum.APP_WAP);
                    String appKey = appInfoDTO.getAppKey();

                    pageItemManager.deleteByItemId(itemId);
                    List<PageItemDTO> pageItemDTOs = pageItemManager.queryPageByItemId(itemId);
                    for (PageItemDTO pageItemDTO : pageItemDTOs){
                        Request request = new BaseRequest();
                        request.setCommand(ActionEnum.ADD_PUBLISH_PAGE.getActionName());
                        request.setParam("pageId",pageItemDTO.getPageId());
    //                            request.setParam("appKey", AppKeyEnum.MAINWEB.getAppKey());
                        request.setParam("appKey", appKey);
                        mainWebService.execute(request);
                    }
                } catch (MainWebException e) {
                    log.error("更新商品删除状态失败 error_code :{} msg : {}", e.getCode(), e.getMessage());
    //                    return ResponseUtil.getErrorResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
                }
            }
        }
        return Action.CommitMessage;
    }
}
