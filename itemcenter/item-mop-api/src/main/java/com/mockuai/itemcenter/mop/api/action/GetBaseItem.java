package com.mockuai.itemcenter.mop.api.action;

import com.mockuai.common.uils.staticpage.SerialPageEnum;
import com.mockuai.common.uils.staticpage.StaticPage;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.mop.api.domain.ItemUidDTO;
import com.mockuai.itemcenter.mop.api.util.JsonUtil;
import com.mockuai.itemcenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lizg on 2016/11/1.
 */
public class GetBaseItem extends BaseAction {

    private static final Logger log = LoggerFactory.getLogger(GetBaseItem.class);

    private StaticPage staticPage;
    private final static String environment = "online";

    private final static String OUTPUT_PREFIX = "{\"code\":10000,\"msg\":\"success\",\"data\":{\"time\":xx,\"item\":xxx}}";

    private final static String TPL = "xxx";

    private final static String TPL_TIME = "xx";

    public GetBaseItem() {
        //上线时候要改成online
        staticPage = new StaticPage();
        staticPage.setEnvironment(environment);
    }


    @Override
    public MopResponse execute(Request request) {

        String itemUidStr = (String) request.getParam("item_uid");
        ItemUidDTO itemUidDTO = MopApiUtil.parseItemUid(itemUidStr);
        if (itemUidDTO == null) {
            return new MopResponse(ResponseCode.PARAM_E_INVALID.getCode(), "item_uid is invalid");
        }
        log.info("[itemUidDTO] :{}", JsonUtil.toJson(itemUidDTO));
        try {

            String items = "";
            try {
                items = staticPage.getFileContent(SerialPageEnum.ITEM, itemUidDTO.getItemId());
            } catch (Exception e) {
                log.error("从ssd磁盘读取商品信息失败 item_id {}", itemUidDTO.getItemId());
            }
            log.info("[{}] items:{}", items);

            Long times = System.currentTimeMillis();

            String output = OUTPUT_PREFIX.replace(TPL, items);

            String responseStr = output.replace(TPL_TIME, String.valueOf(times));
            return new MopResponse<String>(responseStr);

        } catch (Exception e) {
            log.error("itemUid=" + itemUidStr, e);
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR);
        }

    }

    /**
     * 用户自定义返回格式
     *
     * @return
     */
    public ResponseFormat getResponseFormat() {
        return ResponseFormat.CUSTOMIZE;
    }

    @Override
    public String getName() {
        return "/item/base/get";
    }

    @Override
    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    @Override
    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
