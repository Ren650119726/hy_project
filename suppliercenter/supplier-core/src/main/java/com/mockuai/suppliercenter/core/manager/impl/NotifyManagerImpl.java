package com.mockuai.suppliercenter.core.manager.impl;

import com.mockuai.appcenter.common.constant.BizPropertyKey;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.suppliercenter.core.manager.AppManager;
import com.mockuai.suppliercenter.core.manager.NotifyManager;
import com.mockuai.suppliercenter.core.manager.ThreadPoolManager;
import com.mockuai.suppliercenter.core.util.HttpUtil;
import com.mockuai.suppliercenter.core.util.JsonUtil;
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
 * Created by zengzhangqiang on 7/2/15.
 */
@Service
public class NotifyManagerImpl implements NotifyManager {
    private static final Logger log = LoggerFactory.getLogger(NotifyManagerImpl.class);
    private static final Logger notifyLogger = LoggerFactory.getLogger("notifyLogger");

    @Resource
    private AppManager appManager;

    @Resource
    private ThreadPoolManager threadPoolManager;

    public static void main(String[] args) {
        long userId = 38607L;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("topic", "user-bind-mobile-msg"));
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("mobile", "18101861100");
        data.put("open_type", 1);
        data.put("open_uid", "");
        params.add(new BasicNameValuePair("data", JsonUtil.toJson(data)));
        try {
            String response = HttpUtil.post("http://121.40.98.204:8082/YDX-ERP/message/notify", params);
            System.out.println("response:" + response);
            notifyLogger.info("userId:{}, response:{}", userId, response);
        } catch (Exception e) {
            notifyLogger.info("userId:{}", userId, e);

        }
    }

    public void notifyBindUserMsg(final String mobile, final int openType, final String openUid, final String bizCode) {

        //获取企业回调url
        final String notifyUrl = getBizNotifyUrl(bizCode);

        if (StringUtils.isBlank(notifyUrl)) {
            log.warn("notifyUrl does not config, bizCode:{}", bizCode);
            return;
        }

        //TODO 待重构成使用异步任务框架
        threadPoolManager.execute(new Runnable() {
            public void run() {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("topic", "user-bind-mobile-msg"));
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("mobile", mobile);
                data.put("open_type", openType);
                data.put("open_uid", openUid);
                params.add(new BasicNameValuePair("data", JsonUtil.toJson(data)));
                try {
                    String response = HttpUtil.post(notifyUrl, params);
                    notifyLogger.info("mobile:{}, openType:{}, openUid:{}, response:{}",
                            mobile, openType, openUid, response);
                } catch (Exception e) {
                    notifyLogger.error("mobile:{}, openType:{}, openUid:{}", mobile, openType, openUid, e);
                }
            }
        });

    }

    public void notifyAddUserMsg(final long userId, final String bizCode) {

        //获取企业回调url
        final String notifyUrl = getBizNotifyUrl(bizCode);

        if (StringUtils.isBlank(notifyUrl)) {
            log.warn("notifyUrl does not config, bizCode:{}", bizCode);
            return;
        }

        //TODO 待重构成使用异步任务框架
        threadPoolManager.execute(new Runnable() {
            public void run() {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("topic", "user-add-msg"));
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("user_id", userId);
                params.add(new BasicNameValuePair("data", JsonUtil.toJson(data)));
                try {
                    String response = HttpUtil.post(notifyUrl, params);
                    notifyLogger.info("userId:{}, response:{}", userId, response);
                } catch (Exception e) {
                    notifyLogger.info("userId:{}", userId, e);

                }
            }
        });
    }

    /**
     * 获取企业回调url
     *
     * @param bizCode
     * @return
     */
    private String getBizNotifyUrl(String bizCode) {

        if (StringUtils.isBlank(bizCode)) {
            return null;
        }

        //获取企业配置信息
        try {
            BizInfoDTO bizInfoDTO = appManager.getBizInfo(bizCode);
            BizPropertyDTO notifyUrlProperty = bizInfoDTO.getBizPropertyMap().get(BizPropertyKey.BIZ_NOTIFY_URL);
            if (notifyUrlProperty != null) {
                String notifyUrl = notifyUrlProperty.getValue();
                log.info("biz notifyUrl:{}", notifyUrl);
                return notifyUrl;
            }
        } catch (Exception e) {
            log.error("error to get bizInfo, bizCode:{}", bizCode, e);
        }

        return null;
    }
}
