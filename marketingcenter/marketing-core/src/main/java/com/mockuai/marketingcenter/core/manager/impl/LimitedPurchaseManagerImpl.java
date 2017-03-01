package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.LimitTimeActivityStatus;
import com.mockuai.marketingcenter.common.constant.LimitTimeIssueStatus;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.qto.TimePurchaseQTO;
import com.mockuai.marketingcenter.core.dao.LimitedGoodsCorrelationDAO;
import com.mockuai.marketingcenter.core.dao.LimitedPurchaseDAO;
import com.mockuai.marketingcenter.core.dao.LimitedPurchaseGoodsDAO;
import com.mockuai.marketingcenter.core.domain.LimitedGoodsCorrelationDO;
import com.mockuai.marketingcenter.core.domain.LimitedPurchaseDO;
import com.mockuai.marketingcenter.core.domain.LimitedPurchaseGoodsDO;
import com.mockuai.marketingcenter.core.domain.LimitedUserCorrelationDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.LimitedPurchaseManager;
import com.mockuai.marketingcenter.core.manager.LimitedUserCorrelationManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.*;

/**限时购活动相关操作
 * Created by huangsiqian on 2016/10/7.
 */
public class LimitedPurchaseManagerImpl implements LimitedPurchaseManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(LimitedPurchaseManagerImpl.class);
    @Resource
    private LimitedPurchaseDAO activityDAO;
    @Resource
    private LimitedPurchaseGoodsDAO limitedPurchaseGoodsDAO;
    @Resource
    private LimitedPurchaseManager limitedPurchaseManager;
    @Resource
    private LimitedUserCorrelationManager limitedUserCorrelationManager;
    @Resource
    private LimitedGoodsCorrelationDAO limitedGoodsCorrelationDAO;

    //添加限时购活动信息
    @Override
    public Long addActivities(TimePurchaseQTO activityQTO) throws MarketingException {
        Long num=null;
        try {
            LimitedPurchaseDO activityDO = new LimitedPurchaseDO();
            activityDO.setStartTime(activityQTO.getStartTime());
            activityDO.setEndTime(activityQTO.getEndTime());
            activityDO.setActivityTag(activityQTO.getActivityTag());
            activityDO.setActivityName(activityQTO.getActivityName());
            activityDO.setVoucherType(activityQTO.getVoucherType());
            activityDO.setIssueStatus(activityQTO.getIssueStatus());
            num=(Long) activityDAO.addActivities(activityDO);
        }catch (Exception e){
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,"添加活动出错");
        }
        if(num<1){
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,"活动未添加成功");
        }
        return num;
    }
    //限时购活动展示
    @Override
    public List<LimitedPurchaseDTO> activityList(TimePurchaseQTO timePurchaseQTO) throws MarketingException {
        List<LimitedPurchaseDTO>  list = new ArrayList<LimitedPurchaseDTO>();
        List<LimitedPurchaseDO> activityDOs = activityDAO.activityList(timePurchaseQTO);
        for(LimitedPurchaseDO activityDO : activityDOs){
            LimitedPurchaseDTO activityDTO = new LimitedPurchaseDTO();
            BeanUtils.copyProperties(activityDO == null ? new LimitedPurchaseDO() : activityDO, activityDTO);
            list.add(activityDTO);
        }
        return list;

    }
    //修改限时购活动状态
    @Override
    public Boolean updateActivity(LimitedPurchaseDO activityDO) throws MarketingException {
        Integer num=null;
        try {
            num = (Integer) activityDAO.updateActivity(activityDO);
        }catch (Exception e){
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,"活动失效出错");
        }
        if(num<1){
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,"活动失效失败");
        }
        return true;

    }
    //修改限时购活动内容信息
    @Override
    public Boolean modifyActivity(LimitedPurchaseDO activityDO) throws MarketingException {
        Integer num=null;

        try {
            num = (Integer) activityDAO.modifyActivity(activityDO);
        }catch (Exception e){
            e.printStackTrace();
//            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,"活动修改出错");
        }
        if(num<0){
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,"活动修改失败");
        }
        return true;
    }

    //查询单个限时购活动
    @Override
    public LimitedPurchaseDTO activityById(Long id) throws MarketingException {
        LimitedPurchaseDO activityDO = (LimitedPurchaseDO)activityDAO.activityById(id);
        LimitedPurchaseDTO activityDTO = new LimitedPurchaseDTO();
        BeanUtils.copyProperties(activityDTO == null ? new LimitedPurchaseDO() : activityDO, activityDTO);
        return activityDTO;
    }

    //限时购活动总数统计
    @Override
    public Integer activityCount(TimePurchaseQTO timePurchaseQTO) throws MarketingException {
        try {
            return (Integer) activityDAO.activityCount(timePurchaseQTO).intValue();
        }catch (Exception e){
            e.printStackTrace();
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,"查询活动总数错误");
        }
    }
    //发布活动
    @Override
    public Boolean startLimtedPurchase(LimitedPurchaseDO activityDO) throws MarketingException {
        Integer num=null;
        try {
            num = activityDAO.startLimtedPurchase(activityDO);
        }catch (Exception e){
            e.printStackTrace();
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,"活动发布出错");
        }
        if(num<1){
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,"活动发布失败");
        }
        return true;
    }
    // 活动没有结束传出商品信息(手机端限时购活动商品详情)
    public Map<LimitedPurchaseDTO, List<MarketItemDTO>> getTimeLimitOfItemSkuMsg(List<MarketItemDTO> list) throws MarketingException {
        Map<LimitedPurchaseDTO, List<MarketItemDTO>> map = new HashMap<LimitedPurchaseDTO, List<MarketItemDTO>>();
        try {
            if (list == null) {
                throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "没有传入商品");
            }

            //获取当前时间
            Date now = Calendar.getInstance().getTime();

            List<Long> activityIdList = new ArrayList<>();
            //获取距离活动开始时间最近的活动信息
            for(MarketItemDTO marketItemDTO :list) {
                LimitedGoodsCorrelationDO goodsCorrelationDO = this.limitedGoodsCorrelationDAO.selectCurrentActivityId(marketItemDTO.getItemId());

                if (goodsCorrelationDO != null)
                {
                    activityIdList.add(goodsCorrelationDO.getActivityId());
                }
            }

            //去除重复的activityID   //TODO 可用set来优化
            List<Long> resultList = new ArrayList<>();
            for(Long i:activityIdList){
                if(!resultList.contains(i)){
                    resultList.add(i);
                }
            }

            LOGGER.info("resultList:{}",JsonUtil.toJson(resultList));

            for(Long activityId : resultList){
                //新加一个集合存过滤出来的商品

                List<MarketItemDTO> lastMarketList = new ArrayList<MarketItemDTO>();
                List<Long> itemIdList = limitedPurchaseGoodsDAO.selectGoodsItemId(activityId);

                for(Long itemId : itemIdList){
                    //取出所有的item_id与传入商品item_id对比，对上就取出
                    for(MarketItemDTO marketItemDTO :list) {
                        if (itemId.equals(marketItemDTO.getItemId())) {
                            //活动没有结束，取出商品信息
                            LimitedPurchaseDO limtedPurchaseDO = activityDAO.activityById(activityId);
                            if (LimitTimeIssueStatus.ISSUE.getValue().equals(limtedPurchaseDO.getIssueStatus())) {

                                if (now.before(limtedPurchaseDO.getStartTime())) {
                                    marketItemDTO.setItemType(21);

                                    lastMarketList.add(marketItemDTO);
                                }

                                //活动正在进行传入商品价格，sku级取价格传出
                                if (now.after(limtedPurchaseDO.getStartTime()) && now.before(limtedPurchaseDO.getEndTime())) {
                                    marketItemDTO.setItemType(21);

                                    LimitedPurchaseGoodsDO putIn = new LimitedPurchaseGoodsDO();
                                    putIn.setItemId(itemId);
                                    putIn.setActivityId(activityId);
                                    List<LimitedPurchaseGoodsDO> goodsDOlist = limitedPurchaseGoodsDAO.activityGoodsList(putIn);
                                    for (LimitedPurchaseGoodsDO goodsDO : goodsDOlist) {
                                        if (itemId.equals(goodsDO.getItemId())) {
                                            MarketItemDTO newMarItemDTO = new MarketItemDTO();
                                            BeanUtils.copyProperties(marketItemDTO, newMarItemDTO);

                                            newMarItemDTO.setTotalPrice(goodsDO.getGoodsPrice());
                                            newMarItemDTO.setUnitPrice(goodsDO.getGoodsPrice());
                                            newMarItemDTO.setOriginTotalPrice(goodsDO.getGoodsPrice());
                                            //返回剩余可售商品数量
                                            Long goodsNum = goodsDO.getGoodsQuantity();
                                            if (goodsNum < 1) {
                                                goodsNum = 99999L;

                                            }
                                            newMarItemDTO.setLimitNumber(goodsNum);
                                            newMarItemDTO.setItemSkuId(goodsDO.getSkuId());
                                            LOGGER.info("skuInfo:{}", JsonUtil.toJson(marketItemDTO));
                                            lastMarketList.add(newMarItemDTO);
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
                //一个商品传出
                if (!lastMarketList.isEmpty()) {
                    LimitedPurchaseDTO limitedPurchaseDTO = limitedPurchaseManager.activityById(activityId);
                    map.put(limitedPurchaseDTO, lastMarketList);
                }
            }
            LOGGER.info("LIMITEDISNOTNULL goodsInfo:{}", JsonUtil.toJson(map));
            return map;


        } catch (Exception e) {
            e.printStackTrace();
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,"提取商品出错");
        }
    }




    // 活动没有结束传出商品信息（手机端限时购活动商品列表）
    @Override
    public Map<LimitedPurchaseDTO, List<MarketItemDTO>> getTimeLimitOfItem(List<MarketItemDTO> list) throws MarketingException {
        LOGGER.info("get into GOODS LIST:{}", JsonUtil.toJson(list));
        try {
            if (list == null) {
                throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "商品输入为空");
            }

            Date now = Calendar.getInstance().getTime();

            Map map = new HashMap();
            List<Long> activityIdList = new ArrayList();

            for (MarketItemDTO marketItemDTO : list)
            {
                LimitedGoodsCorrelationDO goodsCorrelationDO = this.limitedGoodsCorrelationDAO.selectCurrentActivityId(marketItemDTO.getItemId());

                if (goodsCorrelationDO != null)
                {
                    activityIdList.add(goodsCorrelationDO.getActivityId());
                }
            }
            //去除重复的activityID
            List<Long> resultList = new ArrayList<>();
            for(Long i:activityIdList){
                if(!resultList.contains(i)){
                    resultList.add(i);
                }
            }
            LOGGER.info("activityId:{}",JsonUtil.toJson(resultList));
            for(Long activityId:resultList){
                //新加一个集合存过滤出来的商品

                List<MarketItemDTO>  lastMarketList = new ArrayList<MarketItemDTO>();

                List<Long> itemIdList = limitedPurchaseGoodsDAO.selectGoodsItemId(activityId);
                LOGGER.info("itemidList:{}",JsonUtil.toJson(itemIdList));
//                活动信息
                LimitedPurchaseDO limtedPurchaseDO = activityDAO.activityById(activityId);
                LOGGER.info("limitedpurchase:{}",JsonUtil.toJson(limtedPurchaseDO));
                for(Long itemId: itemIdList){
                    //取出所有的item_id与传入商品item_id对比，对上就取出
                   for(MarketItemDTO marketItemDTO:list){
                        if(itemId.equals(marketItemDTO.getItemId())){
                            LOGGER.info("itemid:{}",itemId);
                            //活动没有结束，取出商品信息

                            //如果活动没有发布，不返回值进入下一次循环//TODO 后期加活动进行状态的判断
                            if(LimitTimeIssueStatus.ISSUE.getValue().equals(limtedPurchaseDO.getIssueStatus())) {
                                LOGGER.info("activity is  issue");
                                //活动没有开始只标记为活动产品
                                if (now.before(limtedPurchaseDO.getStartTime())) {
                                    marketItemDTO.setItemType(21);
                                    lastMarketList.add(marketItemDTO);
                                }
                                //活动正在进行传入商品价格，sku级取价格较低的那个价格传出
                                if (now.after(limtedPurchaseDO.getStartTime()) && now.before(limtedPurchaseDO.getEndTime())) {
                                    marketItemDTO.setItemType(21);
                                    LimitedPurchaseGoodsDO putIn = new LimitedPurchaseGoodsDO();
                                    putIn.setItemId(itemId);
                                    putIn.setActivityId(activityId);
                                    LimitedPurchaseGoodsDO goodsDO = limitedPurchaseGoodsDAO.selectMinGoodsPrice(putIn);
                                    if (goodsDO == null) {
                                        throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "限时购商品数据异常");
                                    }
                                    LOGGER.info("get out goods info:{}",JsonUtil.toJson(goodsDO));
                                    marketItemDTO.setTotalPrice(goodsDO.getGoodsPrice());
                                    marketItemDTO.setUnitPrice(goodsDO.getGoodsPrice());
                                    marketItemDTO.setOriginTotalPrice(goodsDO.getGoodsPrice());
                                    //返回剩余可售商品数量
                                    Long goodsNum = goodsDO.getGoodsQuantity();
                                    if (goodsNum < 1) {
                                        goodsNum = 99999L;

                                    }
                                    marketItemDTO.setLimitNumber(goodsNum);
                                    lastMarketList.add(marketItemDTO);
                                }
                            }
                        }
                    }

                }
                //一个商品传出
                if(!lastMarketList.isEmpty()){
                    LimitedPurchaseDTO limitedPurchaseDTO = limitedPurchaseManager.activityById(activityId);
                    map.put(limitedPurchaseDTO,lastMarketList);
                }
            }
            LOGGER.info("search goods list:{}",JsonUtil.toJson(map));
            return map;

        }catch (Exception e){
            e.printStackTrace();

            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,"提取商品出错");
        }
    }

    //活动正在进行中把限时购商品价格和剩余购买量传出(限时购结算时调用)
    @Override
    public Map<LimitedPurchaseDTO, List<MarketItemDTO>> getIteminTimeLimit(List<MarketItemDTO> list,Long userId) throws MarketingException {
        try{
            if(list==null){
                throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,"商品输入为空");
            }
            //获取当前时间
            Date now = Calendar.getInstance().getTime();

            List<Long> activityIdList = new ArrayList<>();

            for (MarketItemDTO marketItemDTO : list){
                LimitedGoodsCorrelationDO goodsCorrelationDO = this.limitedGoodsCorrelationDAO.selectCurrentActivityId(marketItemDTO.getItemId());

                if (goodsCorrelationDO != null){
                    activityIdList.add(goodsCorrelationDO.getActivityId());
                }
            }
            //去除重复的activityID
            List<Long> resultList = new ArrayList<>();
            for(Long i:activityIdList){
                if(!resultList.contains(i)){
                    resultList.add(i);
                }
            }

            Iterator It = resultList.iterator();
            //新加一个集合存过滤出来的商品
            Map<LimitedPurchaseDTO, List<MarketItemDTO>>  map = new HashMap<LimitedPurchaseDTO, List<MarketItemDTO>>();
            //根据活动id取出所有参加活动商品item_id
            for(Long activityId:resultList){

                List<MarketItemDTO>  lastMarketList = new ArrayList<MarketItemDTO>();

                List<Long> itemIdList = limitedPurchaseGoodsDAO.selectGoodsItemId(activityId);
               for(Long itemId: itemIdList){
                    //取出所有的item_id与传入商品item_id对比，对上就取出
                    for(MarketItemDTO marketItemDTO:list){
                        if(itemId.equals(marketItemDTO.getItemId())) {
                            //活动正在进行，取出商品信息
                            LimitedPurchaseDO limtedPurchaseDO = activityDAO.activityById(activityId);
                            if (limtedPurchaseDO == null) {
                                throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "对应的商品没有参加活动");
                            }
//                            //如果活动没有发布，不返回值进入下一次循环
                            if(LimitTimeIssueStatus.ISSUE.getValue().equals(limtedPurchaseDO.getIssueStatus())) {

//                            如果活动为非结束状态，就添加
                                if (limtedPurchaseDO.getRunStatus().equals(LimitTimeActivityStatus.PROCESS.getValue())) {
                                    //活动没开始商品详情页显示活动状态
                                   /* if (now.before(limtedPurchaseDO.getStartTime())) {
                                        marketItemDTO.setItemType(21);
                                        marketItemDTO.setLimitNumber(99999L);
                                        lastMarketList.add(marketItemDTO);
                                    }*/


                                    //活动正在进行时判断
                                    if (now.after(limtedPurchaseDO.getStartTime()) && now.before(limtedPurchaseDO.getEndTime())) {
                                        //活动标识改成21
                                        marketItemDTO.setItemType(21);
                                        //取出中间表中已经销售限购商品数量
                                        LimitedUserCorrelationDO setIN = new LimitedUserCorrelationDO();
                                        setIN.setActivityId(activityId);
                                        setIN.setItemId(itemId);
                                        setIN.setUserId(userId);//传入用户id
                                        setIN.setSkuId(marketItemDTO.getItemSkuId());
                                        LOGGER.info("userCorrlelation MSG:{},userId:{}", JsonUtil.toJson(setIN), userId);
                                        LimitedUserCorrelationDO userCorrelationDO = limitedUserCorrelationManager.selectUserMsg(setIN);
                                        //取出活动商品信息
                                        LimitedPurchaseGoodsDO goodsDO = new LimitedPurchaseGoodsDO();
                                        goodsDO.setActivityId(activityId);
                                        goodsDO.setItemId(itemId);
                                        goodsDO.setSkuId(marketItemDTO.getItemSkuId());
                                        //取出对应的商品（入参activity_id ,item_id , sku_id）
                                        LimitedPurchaseGoodsDO goods = limitedPurchaseGoodsDAO.selectActivityGoods(goodsDO);
                                        //返回剩余可售商品数量
                                        Long limitNum = null;
                                        //中间表中用户已买的量
                                        Long lastNum = null;
                                        if (userCorrelationDO == null) {
                                            lastNum = 0L;
                                        } else {
                                            lastNum = userCorrelationDO.getPurchaseQuantity();
                                        }
                                        if (goods.getGoodsQuantity().equals(new Long(0L))) {

                                            //如果不限购，可买量为9999
                                            limitNum = 99999L;

                                        } else {
                                            limitNum = goods.getGoodsQuantity() - lastNum;
                                        }
                                        if (limitNum < 0) {
                                            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "商品id:" + itemId + "对应的商品销售数据有误");

                                        }
                                        marketItemDTO.setLimitNumber(limitNum);
                                        marketItemDTO.setOriginTotalPrice(goods.getGoodsPrice());
                                        marketItemDTO.setUnitPrice(goods.getGoodsPrice());
                                        marketItemDTO.setTotalPrice(goods.getGoodsPrice());

                                        lastMarketList.add(marketItemDTO);
                                    }
                                }
                            }
                        }
                    }

                }
                //组装数据传出(如果没有商品则不返回商品集合)
                if(!lastMarketList.isEmpty()) {
                    LimitedPurchaseDTO limitedPurchaseDTO = limitedPurchaseManager.activityById(activityId);
                    map.put(limitedPurchaseDTO, lastMarketList);
                }
            }
            LOGGER.info("show goods MSG:{}",JsonUtil.toJson(map));
            return map;

        }catch (Exception e){
            e.printStackTrace();
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,"提取商品出错");
        }
    }
    //更改活动前物理删除活动信息
    @Override
    public Boolean deleteLimitPurchase(Long activityId) throws MarketingException {
        Integer num=null;
        try {
            num = (Integer) activityDAO.deleteLimitPurchase(activityId);
        }catch (Exception e){
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,"删除活动出错");
        }
        if(num<1){
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,"删除活动失败");
        }
        return true;
    }
    //定时任务修活动状态
    @Override
    public Boolean updateLimitedPurchaseStatus(String status, Long activityId) throws MarketingException {
        Integer num=null;
        try{
            num = (Integer)activityDAO.updateLimitedPurchaseStatus(status,activityId);
        }catch (Exception e){
            e.printStackTrace();
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,"更新活动状态出错");
        }
        if(num<1){
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,"更新活动状态失败");
        }
        return true;
    }
}
