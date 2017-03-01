package com.mockuai.itemcenter.core.service.action.migrate;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.ItemSearchManager;
import com.mockuai.itemcenter.core.manager.ShopManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.JsonUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yindingyu on 16/2/25.
 */
@Service
public class Single2MultiShopMigrateAction extends TransAction {

    @Resource
    private ShopManager shopManager;

    @Resource
    private ItemManager itemManager;

    @Resource
    private ItemSearchManager itemSearchManager;

    private static final Logger log = LoggerFactory.getLogger(Single2MultiShopMigrateAction.class);

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {

        ItemRequest request = context.getRequest();

        Long sellerId = request.getLong("sellerId");

        String appKey = request.getString("appKey");

        if (sellerId == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "sellerId不能为空");
        }

        final String bizCode = (String) context.get("bizCode");

        //查询是否已创建店铺
        ShopDTO shopDTO = shopManager.getShop(sellerId, appKey);

        if (shopDTO == null) {
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "店铺不存在");
        }

        final Long shopId = shopDTO.getId();

        Long result = 0L;


        final List<ItemDTO> itemDTOList = itemManager.selectItemLoseShopId(bizCode);

        if (!CollectionUtils.isEmpty(itemDTOList)) {

            List<Long> itemIdList = Lists.newArrayListWithCapacity(itemDTOList.size());

            for (ItemDTO itemDTO : itemDTOList) {

                itemIdList.add(itemDTO.getId());

                itemDTO.setShopId(shopId);

//                itemSearchManager.setItemIndex(itemDTO);
            }

            if (!CollectionUtils.isEmpty(itemIdList)) {

                ItemQTO query = new ItemQTO();

                query.setIdList(itemIdList);

                query.setShopId(shopId);

                query.setBizCode(bizCode);

                result = itemManager.updateItemLoseShopId(query);
            }


            //启动新线程,处理索引任务
            ExecutorService executor = Executors.newSingleThreadExecutor();

            executor.execute(new Runnable() {

                @Override
                public void run() {

                    long count = 0L;
                    long errCount = 0;

                    for (ItemDTO itemDTO : itemDTOList) {

                        itemDTO.setShopId(shopId);

                        try {
                            itemSearchManager.setItemIndex(itemDTO);
                            count++;
                        } catch (ItemException e) {
                            errCount++;
                            log.error("code :{} msg:{} itemDTO: {}", e.getCode(), e.getMessage(), JsonUtil.toJson(itemDTO));
                        } catch (Exception e) {
                            errCount++;
                            log.error("code :{} msg:{} itemDTO: {}", e.getMessage(), JsonUtil.toJson(itemDTO));
                        }
                    }

                    log.info("单店铺升级多店铺商品数据迁移完成,biz_code:{},共迁移成功{}条,失败{}条", bizCode, count, errCount);
                }

            });


        }


        return ResponseUtil.getSuccessResponse(result);
    }

    @Override
    public String getName() {
        return ActionEnum.SINGLE_2_MULTI_SHOP_MIGRATE.getActionName();
    }
}
