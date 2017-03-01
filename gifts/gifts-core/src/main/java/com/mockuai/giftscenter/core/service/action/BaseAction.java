package com.mockuai.giftscenter.core.service.action;

import com.mockuai.giftscenter.common.api.GiftsResponse;
import com.mockuai.giftscenter.common.constant.ResponseCode;
import com.mockuai.giftscenter.core.exception.GiftsException;
import com.mockuai.giftscenter.core.service.RequestContext;
import com.mockuai.giftscenter.core.util.JsonUtil;
import com.mockuai.giftscenter.core.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by edgar.zr on 12/29/15.
 */
public abstract class BaseAction<T> implements Action {

    public static final Logger LOGGER = LoggerFactory.getLogger(BaseAction.class);

    @Override
    public GiftsResponse run(RequestContext context) throws GiftsException {
        try {
            GiftsResponse seckillBuyResponse = execute(context);
            if (seckillBuyResponse.isSuccess() == false) {
                LOGGER.error("Action [{}] is unsuccess, {}",
                        context.getRequest().getCommand(), seckillBuyResponse.getMessage());
            }
            LOGGER.debug("------------------------------------------------------------------");
            LOGGER.debug("{}, {}", context.getRequest().getCommand(), JsonUtil.toJson(seckillBuyResponse));
            LOGGER.debug("------------------------------------------------------------------");
            return seckillBuyResponse;
        } catch (GiftsException e) {
            LOGGER.error("Action [{}] is unsuccess, {}",
                    context.getRequest().getCommand(), e.getMessage());
            return ResponseUtil.getResponse(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Action [{}] is unsuccess, {}",
                    context.getRequest().getCommand(), e.getMessage());
            return ResponseUtil.getResponse(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    public abstract GiftsResponse execute(final RequestContext context) throws GiftsException;
}