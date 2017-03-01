package com.mockuai.appcenter.core.service.action.impl;

import com.mockuai.appcenter.common.api.AppResponse;
import com.mockuai.appcenter.common.constant.ActionEnum;
import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.appcenter.common.constant.BizPropertyKey;
import com.mockuai.appcenter.common.constant.ResponseCode;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.appcenter.core.domain.AppInfoDO;
import com.mockuai.appcenter.core.domain.AppPropertyDO;
import com.mockuai.appcenter.core.domain.BizInfoDO;
import com.mockuai.appcenter.core.domain.BizPropertyDO;
import com.mockuai.appcenter.core.exception.AppException;
import com.mockuai.appcenter.core.manager.AppInfoManager;
import com.mockuai.appcenter.core.manager.BizInfoManager;
import com.mockuai.appcenter.core.manager.BizPropertyManager;
import com.mockuai.appcenter.core.manager.CacheManager;
import com.mockuai.appcenter.core.service.RequestContext;
import com.mockuai.appcenter.core.service.action.Action;
import com.mockuai.appcenter.core.util.CacheHelper;
import com.mockuai.appcenter.core.util.ModelUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 6/8/15.
 */
public class GetBizInfo implements Action{
    private static final Logger log = LoggerFactory.getLogger(GetBizInfo.class);

    @Resource
    private BizInfoManager bizInfoManager;
    @Resource
    private AppInfoManager appInfoManager;
    @Resource
    private BizPropertyManager bizPropertyManager;
    @Resource
    private CacheManager cacheManager;
    private String mopHost = "api.mockuai.com";

    @Override
    public AppResponse execute(RequestContext context) {
        String bizCode = (String)context.getRequest().getParam("bizCode");

        //TODO 入参检查

        if(StringUtils.isBlank(bizCode)){
            return new AppResponse(ResponseCode.PARAM_E_PARAM_MISSING, "bizCode is null");
        }

        //查询缓存，缓存命中则直接返回
        try{
            Object bizCache = cacheManager.getAndTouch(CacheHelper.genBizCacheKeyByBizCode(bizCode), 60*60*24);
            if(bizCache != null){
                return new AppResponse(bizCache);
            }
        }catch(Throwable t){
            log.error("error to get biz cache!", t);
        }

        try{
            BizInfoDO bizInfoDO = bizInfoManager.getBizInfo(bizCode);
            if(bizInfoDO == null){
                return new AppResponse(ResponseCode.BIZ_E_BIZ_INFO_NOT_EXIST);
            }

            List<BizPropertyDO> bizPropertyDOs = bizPropertyManager.getBizPropertyList(bizCode);


            BizInfoDTO bizInfoDTO = ModelUtil.convertToBizInfoDTO(bizInfoDO);
            List<BizPropertyDTO> bizPropertyDTOs = ModelUtil.convertToBizPropertyDTOList(bizPropertyDOs);
            Map<String,BizPropertyDTO> bizPropertyDTOMap = new HashMap<String, BizPropertyDTO>();
            if(bizPropertyDTOs != null){
                for(BizPropertyDTO bizPropertyDTO: bizPropertyDTOs){
                    bizPropertyDTOMap.put(bizPropertyDTO.getpKey(), bizPropertyDTO);
                }
            }

            //TODO 以下字符串常量需重构到常量类中
            boolean isPayByMockuai = (bizPropertyDTOMap.containsKey(BizPropertyKey.IS_PAY_BY_MOCKUAI)
                    && bizPropertyDTOMap.get(BizPropertyKey.IS_PAY_BY_MOCKUAI).getValue().equals("1"));
            boolean isWechatLoginByMockuai = (bizPropertyDTOMap.containsKey(BizPropertyKey.IS_WECHAT_LOGIN_BY_MOCKUAI)
                    && bizPropertyDTOMap.get(BizPropertyKey.IS_WECHAT_LOGIN_BY_MOCKUAI).getValue().equals("1"));
            if(isPayByMockuai || isWechatLoginByMockuai){
                //获取魔筷通用版的相关配置信息
                bizPropertyDOs = bizPropertyManager.getBizPropertyList("mockuai_demo");

                if(isPayByMockuai==true && bizPropertyDOs!=null){
                    //如果使用魔筷通用版支付账号的话，则填充魔筷通用支付信息
                    for(BizPropertyDO bizPropertyDO: bizPropertyDOs){
                        if(BizPropertyKey.PAY_PROPERTY_SET.contains(bizPropertyDO.getpKey())){
                            bizPropertyDTOMap.put(bizPropertyDO.getpKey(), ModelUtil.convertToBizPropertyDTO(bizPropertyDO));
                        }
                    }
                }


                if(isWechatLoginByMockuai==true && bizPropertyDOs!=null){
                    //如果使用魔筷通用版微信登录的话，则填充魔筷通用微信登录配置信息
                    for(BizPropertyDO bizPropertyDO: bizPropertyDOs){
                        if(BizPropertyKey.WECHAT_LOGIN_PROPERTY_SET.contains(bizPropertyDO.getpKey())){
                            bizPropertyDTOMap.put(bizPropertyDO.getpKey(), ModelUtil.convertToBizPropertyDTO(bizPropertyDO));
                        }
                    }
                }
            }

            //设置h5支付前台回调的地址
            AppInfoDO appInfoDO = appInfoManager.getAppInfoByType(bizCode, AppTypeEnum.APP_WAP.getValue());
            if(appInfoDO != null){
                //支付宝前台回调地址
                String alipayReturnUrl = "http://"+appInfoDO.getDomainName()+"/pay-success.html";
                bizPropertyDTOMap.put(BizPropertyKey.ALIPAY_RETURN_URL,
                        genBizProperty(bizCode, BizPropertyKey.ALIPAY_RETURN_URL, alipayReturnUrl));

                //银联前台回调地址
                String unionpayReturnUrl = "http://"+appInfoDO.getDomainName()+"/pay-success.html";
                bizPropertyDTOMap.put(BizPropertyKey.UNIONPAY_RETURN_URL,
                        genBizProperty(bizCode, BizPropertyKey.UNIONPAY_RETURN_URL, unionpayReturnUrl));

                //统统付前台回调地址
                String sumpayReturnUrl = "http://"+appInfoDO.getDomainName()+"/pay-success.html";
                bizPropertyDTOMap.put(BizPropertyKey.SUMPAY_RETURN_URL,
                        genBizProperty(bizCode, BizPropertyKey.SUMPAY_RETURN_URL, sumpayReturnUrl));
            }

            //设置后台回调地址
            bizPropertyDTOMap.put(BizPropertyKey.ALIPAY_NOTIFY_URL,
                    genBizProperty(bizCode, BizPropertyKey.ALIPAY_NOTIFY_URL,
                            "http://"+mopHost+"/trade/order/payment/callback/alipay_notify"));
            bizPropertyDTOMap.put(BizPropertyKey.WECHAT_NOTIFY_URL,
                    genBizProperty(bizCode, BizPropertyKey.WECHAT_NOTIFY_URL,
                            "http://"+mopHost+"/trade/order/payment/callback/wechat_notify"));
            bizPropertyDTOMap.put(BizPropertyKey.UNIONPAY_NOTIFY_URL,
                    genBizProperty(bizCode, BizPropertyKey.UNIONPAY_NOTIFY_URL,
                            "http://"+mopHost+"/trade/order/payment/callback/unionpay_notify"));

            //连连支付
            bizPropertyDTOMap.put(BizPropertyKey.LIANLIANPAY_NOTIFY_URL,
                    genBizProperty(bizCode, BizPropertyKey.LIANLIANPAY_NOTIFY_URL,
                            "http://"+mopHost+"/trade/order/payment/callback/lianlianpay_notify"));
            
            //连连代付
            bizPropertyDTOMap.put(BizPropertyKey.LIANLIAN_CARDPAY_NOTIFY_URL,
                    genBizProperty(bizCode, BizPropertyKey.LIANLIAN_CARDPAY_NOTIFY_URL,
                            "http://"+mopHost+"/trade/order/payment/callback/lianliancardpay_notify"));
            
            //统统付后台回调
            bizPropertyDTOMap.put(BizPropertyKey.SUMPAY_NOTIFY_URL,
                    genBizProperty(bizCode, BizPropertyKey.SUMPAY_NOTIFY_URL,
                            "http://"+mopHost+"/trade/order/payment/callback/sumpay_notify"));
            //退款回调地址
            bizPropertyDTOMap.put(BizPropertyKey.SUMPAY_REFUND_NOTIFY_URL,
                    genBizProperty(bizCode, BizPropertyKey.SUMPAY_REFUND_NOTIFY_URL,
                            "http://"+mopHost+"/trade/order/refund/callback/sumpay_notify"));
            //连连退款回调地址 TODO
            bizPropertyDTOMap.put(BizPropertyKey.LIANLIANPAY_REFUND_NOTIFY_URL,
                    genBizProperty(bizCode, BizPropertyKey.LIANLIANPAY_REFUND_NOTIFY_URL,
                            "http://"+mopHost+"/trade/order/refund/callback/lianlian_notify"));

            bizInfoDTO.setBizPropertyMap(bizPropertyDTOMap);

            //将bizInfoDTO进行缓存
            cacheManager.set(CacheHelper.genBizCacheKeyByBizCode(bizCode), 60*60*24, bizInfoDTO);

            return new AppResponse(bizInfoDTO);
        }catch(AppException e){
            //TODO error handle
            return new AppResponse(e.getResponseCode(), e.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.GET_BIZ_INFO.getActionName();
    }

    public String getMopHost() {
        return mopHost;
    }

    public void setMopHost(String mopHost) {
        this.mopHost = mopHost;
    }

    private BizPropertyDTO genBizProperty(String bizCode, String pKey, String pValue){
        BizPropertyDTO bizPropertyDTO = new BizPropertyDTO();
        bizPropertyDTO.setBizCode(bizCode);
        bizPropertyDTO.setpKey(pKey);
        bizPropertyDTO.setValue(pValue);
        bizPropertyDTO.setValueType(1);//TODO 重构成枚举常量

        return bizPropertyDTO;
    }
}
