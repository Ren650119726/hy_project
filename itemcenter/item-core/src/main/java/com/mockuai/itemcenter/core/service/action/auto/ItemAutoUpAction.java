package com.mockuai.itemcenter.core.service.action.auto;

import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.itemcenter.common.constant.ItemStatus;
import com.mockuai.itemcenter.common.constant.MessageTagEnum;
import com.mockuai.itemcenter.common.constant.MessageTopicEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.AppManager;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.ItemSearchManager;
import com.mockuai.itemcenter.core.manager.PublishItemManager;
import com.mockuai.itemcenter.core.message.producer.Producer;
import com.mockuai.itemcenter.core.util.JsonUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghailong on 15-7-23.
 */
public class ItemAutoUpAction {
    private static final Logger log = LoggerFactory.getLogger(ItemAutoUpAction.class);

    @Resource
    private ItemManager itemManager;

    @Resource
    private ItemSearchManager itemSearchManager;

    @Resource
    private AppManager appManager;


    @Resource
    TransactionTemplate transactionTemplate;

    @Resource
    private PublishItemManager publishItemManager;

    @Resource
    private Producer producer;

    private volatile boolean started = false;
    private final static String bizcode = "hanshu";

    public void execute() throws JobExecutionException {
        if (!started) {
            started = true;
            transactionTemplate.execute(new TransactionCallback() {
                @Override
                public Object doInTransaction(TransactionStatus status) {
                    try {
                        ItemQTO itemQTOUp = new ItemQTO();
                        itemQTOUp.setNeedPaging(true);

                        //FIXME 一轮调度只能修改200个商品的状态？
                        itemQTOUp.setOffset(0);
                        itemQTOUp.setPageSize(200);

                        List<ItemDTO> upList = itemManager.selectItemSaleUp(itemQTOUp);
                        List<Long> saleUpList = new ArrayList<Long>();
                        for (ItemDTO itemDTO : upList) {
                            saleUpList.add(itemDTO.getId());
                            itemDTO.setItemStatus(ItemStatus.WITHDRAW.getStatus());
                        }

                        log.info("[{}] auto up item idList {}", saleUpList);
                        if (!saleUpList.isEmpty()) {
                            itemQTOUp.setIdList(saleUpList);
                            itemManager.updateItemSaleUp(itemQTOUp);
                        }
                        //更新上架商品索引
                        for (ItemDTO itemDTO : upList) {
                            itemSearchManager.setItemIndex(itemDTO);
                            publishItemManager.publish(itemDTO.getId(), appManager.getAppInfoByType(bizcode, AppTypeEnum.APP_WAP).getAppKey(), bizcode,
                                    true);
                        }

                        if (upList != null && upList.size() > 0) {
                            producer.send(
                                    MessageTopicEnum.ITEM_STATUS_CHANGE.getTopic(),
                                    MessageTagEnum.BATCH.getTag(),

                                    upList);
                        }

                    } catch (ItemException e) {
                        status.setRollbackOnly();
                        log.error(e.toString());
                        return ResponseUtil.getErrorResponse(e.getResponseCode(), e.getMessage());
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        log.error("", e);
                        return ResponseUtil.getErrorResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
                    } finally {
                        started = false;
                    }
                    return null;
                }
            });
        }
    }
}