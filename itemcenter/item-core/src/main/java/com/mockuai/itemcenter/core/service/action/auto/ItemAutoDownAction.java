package com.mockuai.itemcenter.core.service.action.auto;

import com.mockuai.itemcenter.common.constant.MessageTagEnum;
import com.mockuai.itemcenter.common.constant.MessageTopicEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.constant.ItemStatus;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.ItemSearchManager;
import com.mockuai.itemcenter.core.message.producer.Producer;
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
public class ItemAutoDownAction {
    private static final Logger log = LoggerFactory.getLogger(ItemAutoDownAction.class);

    @Resource
    private ItemManager itemManager;

    @Resource
    private ItemSearchManager itemSearchManager;

    @Resource
    TransactionTemplate transactionTemplate;

    @Resource
    private Producer producer;

    private volatile boolean started = false;

    public void execute() throws JobExecutionException {
        if (!started) {
            started = true;
            transactionTemplate.execute(new TransactionCallback() {
                @Override
                public Object doInTransaction(TransactionStatus status) {
                    try {

                        ItemQTO itemQTODown = new ItemQTO();
                        itemQTODown.setNeedPaging(true);
                        itemQTODown.setOffset(0);
                        itemQTODown.setPageSize(200);

                        List<ItemDTO> downList = itemManager.selectItemSaleDown(itemQTODown); //下架状态返回
                        List<Long> saleDownList = new ArrayList<Long>();
                        for (ItemDTO itemDTO : downList) {
                            saleDownList.add(itemDTO.getId());
                            itemDTO.setItemStatus(ItemStatus.WITHDRAW.getStatus());
                        }

                 //       log.info("auto up item idList {}",saleDownList);
                        if (!saleDownList.isEmpty()) {
                            itemQTODown.setIdList(saleDownList);
                            itemManager.updateItemSaleDown(itemQTODown);
                        }
                        //删除下架商品索引
                        for (ItemDTO itemDTO : downList) {
                            itemSearchManager.deleteItemIndex(itemDTO.getId(), itemDTO.getSellerId());
                        }

                        String msgKey = MessageTopicEnum.ITEM_STATUS_CHANGE.getTopic();

                        if (downList != null && downList.size() > 0) {
                            producer.send(
                                    MessageTopicEnum.ITEM_STATUS_CHANGE.getTopic(),
                                    MessageTagEnum.BATCH.getTag(),

                                    downList);
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