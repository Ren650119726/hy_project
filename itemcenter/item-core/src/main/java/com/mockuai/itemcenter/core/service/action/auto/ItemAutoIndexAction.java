package com.mockuai.itemcenter.core.service.action.auto;

import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchResultDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCommentQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.AppManager;
import com.mockuai.itemcenter.core.manager.ItemCommentManager;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.ItemSearchManager;
import org.quartz.JobExecutionException;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * Created by yindingyu on 15/10/16.
 * 定时更新商品索引,修补数据不一致情况,现在限于单店升级多店的情况
 */
public class ItemAutoIndexAction {

//    @Resource
//    private ItemManager itemManager;
//
//    @Resource
//    private ItemSearchManager itemSearchManager;
//
//    @Resource
//    private AppManager appManager;
//
//    private volatile boolean started = false;
//
//    public void execute() throws JobExecutionException {
//        if(!started) {
//            started = true;
//
//
//
//
//                 try{
//
//                     BizInfoDTO bizInfoDTO = appManager.getBizInfo("xx");
//
//                }catch (ItemException e){
//
//                }
//
//            }
//
//        }
//    }

}
