package com.mockuai.tradecenter.core.service.action.order.add.step;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengzhangqiang on 5/23/16.
 */
public class StepPipeImpl implements StepPipe {
    private static Logger log = LoggerFactory.getLogger(StepPipeImpl.class);

    private List<Step> stepList = new ArrayList<Step>();

    @Override
    public StepPipe addStep(Step step) {
        stepList.add(step);
        return this;
    }

    @Override
    public TradeResponse execute(RequestContext requestContext) {
        TradeResponse tradeResponse = null;
        for (Step step : stepList) {
            try{
                tradeResponse = step.execute(requestContext);

                //执行失败，则中断step链的执行，直接返回错误提示
                if(tradeResponse.isSuccess() == false){
                    log.error("error to execute step, stepName:{}, code:{}, msg:{}",
                            step.getName(), tradeResponse.getCode(), tradeResponse.getMessage());
                    return tradeResponse;
                }
            }catch(Exception e) {
                log.error("error to execute step", e);
                return ResponseUtils.getFailResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
            }
        }

        //返回最后一步step的执行结果
        return tradeResponse;
    }
}
