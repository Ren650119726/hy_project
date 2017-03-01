package com.mockuai.itemcenter.core.manager.impl;

import com.mockuai.appcenter.common.constant.BizPropertyKey;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.itemcenter.core.manager.AppManager;
import com.mockuai.itemcenter.core.manager.NotifyManager;
import com.mockuai.itemcenter.core.manager.ThreadPoolManager;
import com.mockuai.itemcenter.core.util.HttpUtil;
import com.mockuai.itemcenter.core.util.ItemUtil;
import com.mockuai.itemcenter.core.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 4/21/16.
 */
@Service("notifyManager")
public class NotifyManagerImpl implements NotifyManager{
    private static final Logger log = LoggerFactory.getLogger(NotifyManagerImpl.class);
    private static final Logger notifyLogger = LoggerFactory.getLogger("notifyLogger");

    @Resource
    private AppManager appManager;

    @Resource
    private ThreadPoolManager threadPoolManager;

    @Override
    public void notifyAddItemMsg(final long itemId, final long sellerId, String bizCode) {
        String topic = "item-add-msg";
        String itemUid = ItemUtil.genUid(itemId, sellerId);
        Map<String,Object> data = new HashMap<String, Object>();
        data.put("item_uid", itemUid);

        //推送消息给商城客户
        pushMsg(topic, data, bizCode);
    }

    @Override
    public void notifyUpdateItemMsg(long itemId, long sellerId, String bizCode) {
        String topic = "item-update-msg";
        String itemUid = ItemUtil.genUid(itemId, sellerId);
        Map<String,Object> data = new HashMap<String, Object>();
        data.put("item_uid", itemUid);

        //推送消息给商城客户
        pushMsg(topic, data, bizCode);
    }

    @Override
    public void notifyUpItemMsg(long itemId, long sellerId, String bizCode) {
        String topic = "item-up-msg";
        String itemUid = ItemUtil.genUid(itemId, sellerId);
        Map<String,Object> data = new HashMap<String, Object>();
        data.put("item_uid", itemUid);

        //推送消息给商城客户
        pushMsg(topic, data, bizCode);
    }

    @Override
    public void notifyDownItemMsg(long itemId, long sellerId, String bizCode) {
        String topic = "item-down-msg";
        String itemUid = ItemUtil.genUid(itemId, sellerId);
        Map<String,Object> data = new HashMap<String, Object>();
        data.put("item_uid", itemUid);

        //推送消息给商城客户
        pushMsg(topic, data, bizCode);
    }

    private void pushMsg(final String topic, final Map<String,Object> data, String bizCode){
        //获取企业回调url
        final String notifyUrl = getBizNotifyUrl(bizCode);

        if(StringUtils.isBlank(notifyUrl)){
            log.warn("notifyUrl does not config, bizCode:{}", bizCode);
            return;
        }

        //TODO 待重构成使用异步任务框架
        threadPoolManager.execute(new Runnable() {
            public void run() {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("topic", topic));

                params.add(new BasicNameValuePair("data", JsonUtil.toJson(data)));
                try{
                    String response = HttpUtil.post(notifyUrl, params);
                    notifyLogger.info("data:{}, response:{}", JsonUtil.toJson(data), response);
                }catch(Exception e){
                    notifyLogger.error("data:{}", JsonUtil.toJson(data), e);
                }
            }
        });
    }

    /**
     * 获取企业回调url
     * @param bizCode
     * @return
     */
    private String getBizNotifyUrl(String bizCode){

        if(StringUtils.isBlank(bizCode)){
            return null;
        }

        //获取企业配置信息
        try{
            BizInfoDTO bizInfoDTO = appManager.getBizInfo(bizCode);
            BizPropertyDTO notifyUrlProperty = bizInfoDTO.getBizPropertyMap().get(BizPropertyKey.BIZ_NOTIFY_URL);
            if(notifyUrlProperty != null){
                String notifyUrl = notifyUrlProperty.getValue();
                log.info("biz notifyUrl:{}", notifyUrl);
                return notifyUrl;
            }
        }catch(Exception e){
            log.error("error to get bizInfo, bizCode:{}", bizCode, e);
        }

        return null;
    }


}
