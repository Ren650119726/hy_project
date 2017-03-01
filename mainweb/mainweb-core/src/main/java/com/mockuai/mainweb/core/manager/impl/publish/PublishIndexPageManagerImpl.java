package com.mockuai.mainweb.core.manager.impl.publish;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.mainweb.common.constant.ResponseCode;
import com.mockuai.mainweb.common.constant.ValueTypeEnum;
import com.mockuai.mainweb.common.domain.dto.*;
import com.mockuai.mainweb.common.domain.dto.publish.*;
import com.mockuai.mainweb.common.domain.qto.PageGridQTO;
import com.mockuai.mainweb.core.dao.publish.PublishIndexPageDAO;
import com.mockuai.mainweb.core.domain.*;
import com.mockuai.mainweb.core.domain.publish.PublishIndexPageDO;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.ItemManager;
import com.mockuai.mainweb.core.manager.PageGridManager;
import com.mockuai.mainweb.core.manager.PagePictureManager;
import com.mockuai.mainweb.core.manager.publish.*;
import com.mockuai.mainweb.core.util.HttpUtil_q;
import com.mockuai.mainweb.core.util.JsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20.
 */
@Service
public class PublishIndexPageManagerImpl implements PublishIndexPageManager {

    @Resource
    private PublishIndexPageDAO pageDAO;


    @Resource
    private PublishPageGalleryManager pageGalleryManager;

    @Resource
    private PublishPageBlockManager pageBlockManager;

    @Resource
    private PublishPageItemCategoryManager pageItemCategoryManager;

    @Resource
    private PublishPageItemManager pageItemManager;

    @Resource
    private PageGridManager pageGridManager;

    @Resource
    private ItemManager itemManager;

    @Resource
    private PagePictureManager pagePictureManager;



    public final static String baseTargetUrl = "http://m.haiyn.com/detail.html?";
    public final static String baseTmsUrl = "http://act.haiyn.com/data/";


    private Logger logger = LoggerFactory.getLogger(PublishIndexPageManagerImpl.class);

    @Override
    public void addPage(PublishIndexPageDTO indexPageDTO) throws MainWebException {
        PublishIndexPageDO indexPageDO = new PublishIndexPageDO();
        BeanUtils.copyProperties(indexPageDTO, indexPageDO);
        try {
            this.pageDAO.addPage(indexPageDO);
            indexPageDTO.setId(indexPageDO.getId());


        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_INSERT,  e);
        }
    }


    @Override
    public void addBizPage(PublishIndexPageDTO indexPageDTO) throws MainWebException {

        List<PageBlockDTO<ImageElementDTO>> imgBlockList =   indexPageDTO.getImgPageBlockList();
        List<PageBlockDTO<ItemListElementDTO>>   itemBlockDTOList = indexPageDTO.getItemPageBlockList();
        List<PageBlockDTO> tmsBlockList = indexPageDTO.getTmsPageBlockList();

        //遍历装有轮播图片 的 PageBlockDTO
        for (PageBlockDTO<ImageElementDTO> imagePageBlockDTO : imgBlockList ){
            imagePageBlockDTO.setPageId(indexPageDTO.getIndexPageId());
            imagePageBlockDTO.setId(null);
            pageBlockManager.addPageBlock(imagePageBlockDTO);
            List<ImageElementDTO> imageElementDTOs = imagePageBlockDTO.getElementList();

            for (ImageElementDTO imageElementDTO : imageElementDTOs){

                imageElementDTO.setBlockId(imagePageBlockDTO.getId());
                imageElementDTO.setPageId(indexPageDTO.getIndexPageId());
                PageGalleryDO pageGalleryDO = new PageGalleryDO();
                BeanUtils.copyProperties(imageElementDTO,pageGalleryDO);
                pageGalleryDO.setId(null);
                pageGalleryDO.setTarget(imageElementDTO.getTargetUrl());
                pageGalleryDO.setImageUri(imageElementDTO.getImageUrl());
                pageGalleryManager.addPageGallery(pageGalleryDO);
            }

        }

        //遍历装有商品块的 PageBlockDTO
        for (PageBlockDTO<ItemListElementDTO> itemPageBlockDTO : itemBlockDTOList){
            itemPageBlockDTO.setPageId(indexPageDTO.getIndexPageId());
            itemPageBlockDTO.setId(null);
            pageBlockManager.addPageBlock(itemPageBlockDTO);
            List<ItemListElementDTO> pageItemDTOs = itemPageBlockDTO.getElementList();

            for (ItemListElementDTO itemListElementDTO : pageItemDTOs){
                itemListElementDTO.setBlockId(itemPageBlockDTO.getId());
                itemListElementDTO.setPageId(indexPageDTO.getIndexPageId());
                //保存商品列表
                PageItemCategoryDO pageItemCategory = new PageItemCategoryDO();
                BeanUtils.copyProperties(itemListElementDTO,pageItemCategory);
                pageItemCategory.setId(null);
                pageItemCategoryManager.addPageItemCategory(pageItemCategory);
                //保存商品
                List<PageItemDTO> itemDTOs = itemListElementDTO.getProductListList();
                List<PageItemDO> pageItemDOList = new ArrayList<>(itemDTOs.size());
                for (PageItemDTO pageItemDTO:itemDTOs){

                    pageItemDTO.setPageItemCategoryId(pageItemCategory.getId());
                    pageItemDTO.setPageId(indexPageDTO.getIndexPageId());
                    PageItemDO pageItemDO = new PageItemDO();
                    BeanUtils.copyProperties(pageItemDTO,pageItemDO);
                    pageItemDO.setId(null);
                    pageItemDOList.add(pageItemDO);
                    //  pageItemManager.addPageItem(pageItemDO);
                }
                pageItemManager.addPageItem(pageItemDOList);
            }
        }
        for(PageBlockDTO blockDTO : tmsBlockList){
            blockDTO.setId(null);
            blockDTO.setPageId(indexPageDTO.getIndexPageId());
        }
        //保存tms 列表
        if(CollectionUtils.isNotEmpty(tmsBlockList)){
            pageBlockManager.addPageBlockList(tmsBlockList);
        }

    }

    @Override
    public void deletePage(Long id) throws MainWebException {
        try {
            this.pageDAO.deletePage(id);
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_DELETE,  e);
        }
    }

    @Override
    public void deleteBizPage(Long id)throws MainWebException {
        //block删除
        try {
            pageBlockManager.deletePageBlock(id);
            pageGalleryManager.deletePageGallery(id);
            pageItemCategoryManager.deleteByPageId(id);
            pageItemManager.deleteByPageId(id);
            pagePictureManager.deletePagePicture(id);

            //删除格子块
            PageGridQTO pageGridQTO = new PageGridQTO();
            pageGridQTO.setPageId(id);
            pageGridManager.deleteGrid(pageGridQTO);
            pageGridManager.deleteGridContent(pageGridQTO);
            pageGridManager.deleteGridMergeCells(pageGridQTO);

        }catch (Exception e){
            throw  new MainWebException(ResponseCode.SYS_E_DB_DELETE,  e);
        }
    }

    @Override
    public IndexPageDTO getPage(Long pageId) throws MainWebException {
        IndexPageDTO indexPageDTO = new IndexPageDTO();
        try {
           PublishIndexPageDO pageDO =   pageDAO.getPage(pageId);
            if (pageDO!=null){
                BeanUtils.copyProperties(pageDO,indexPageDTO);
            }
           return indexPageDTO;
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_QUERY,  e);
        }

    }

    @Override
    public List<IndexPageDTO> queryPublishPageNames() throws MainWebException {
        List<IndexPageDTO> indexPageDTOs = new ArrayList<>();
        try {
            List<PublishIndexPageDO> indexPageDOs = pageDAO.queryPublishPageNames();

            for (PublishIndexPageDO indexPageDO : indexPageDOs){
                IndexPageDTO indexPageDTO = new IndexPageDTO();
                BeanUtils.copyProperties(indexPageDO,indexPageDTO);
                indexPageDTOs.add(indexPageDTO);
            }
            return indexPageDTOs;
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_QUERY,  e);
        }

    }

    @Override
    public List<IndexPageDTO> queryPageNameList() throws MainWebException {
        List<PublishIndexPageDO> indexPageDOs = null;
        try {
            indexPageDOs = this.pageDAO.queryPageNameList();
            List<IndexPageDTO> indexPageDTOs = new ArrayList<>();

            for (PublishIndexPageDO indexPageDO : indexPageDOs){
                IndexPageDTO indexPageDTO = new IndexPageDTO();
                BeanUtils.copyProperties(indexPageDO,indexPageDTO);
                indexPageDTOs.add(indexPageDTO);
            }
            return indexPageDTOs;
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_UPDATE,  e);

        }
    }
    @Override
    public PublishPageDTO generatePageJson(Long pageId, String appKey) throws MainWebException {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<PageBlockDO> pageBlockList = pageBlockManager.queryPageBlock(pageId);
        logger.info("查询 pageBlockList:{}",stopWatch.getTime()/1000.0);
        List<String> componentList = new ArrayList<>();

        for (PageBlockDO pageBlock : pageBlockList) {
            //轮播
            if (pageBlock.getBlockType() == 1) {
                ContentDTO contentDTO = new ContentDTO();
                contentDTO.setValueType(ValueTypeEnum.IMAGE_BANNER.getValueType());

                List<PageGalleryDO> pageGalleryList = pageGalleryManager.queryPageGallery(pageBlock.getId());

                List<ImageElementDTO> imageList = new ArrayList<>();
                for (PageGalleryDO pageGallery : pageGalleryList) {
                    ImageElementDTO imageElementDTO = new ImageElementDTO();
                    imageElementDTO.setImageUrl(pageGallery.getImageUri());
                    imageElementDTO.setTargetUrl(pageGallery.getTarget());
                    imageList.add(imageElementDTO);
                }
                contentDTO.setValue(imageList);

                componentList.add(JsonUtil.toJson(contentDTO));
            }

            //itemBlocks
            if (pageBlock.getBlockType() == 2) {

                PageItemCategoryDO pageItemCategoryDO = pageItemCategoryManager.getPageItemCategory(pageBlock.getId());

                if (pageItemCategoryDO!=null){
                    ContentDTO headerContentDTO = new ContentDTO();
                    headerContentDTO.setValueType(ValueTypeEnum.PRODUCT_LIST_HEADER.getValueType());

                    ProductListHeaderDTO headerDTO = new ProductListHeaderDTO();
                    if (pageItemCategoryDO.getCategoryTitle()!=null) {
                        headerDTO.setCategoryTitle(pageItemCategoryDO.getCategoryTitle());
                    }
                    if (pageItemCategoryDO.getSubCategoryTitle()!=null){
                        headerDTO.setSubCategoryTitle(pageItemCategoryDO.getSubCategoryTitle());
                    }

                    if ((pageItemCategoryDO.getCategoryTitle()!=null && !pageItemCategoryDO.getCategoryTitle().equals(""))
                            || (pageItemCategoryDO.getSubCategoryTitle()!=null && !pageItemCategoryDO.getSubCategoryTitle().equals(""))){
                        headerContentDTO.setValue(headerDTO);
                        //header 加入完毕
                        componentList.add(JsonUtil.toJson(headerContentDTO));
                    }

                }



                List<ProductJsonDTO> productList = new ArrayList<>();
                List<PageItemDTO> pageItemDTOs = pageItemManager.queryPageItem(pageItemCategoryDO.getId());
                List<Long> itemIdList = Lists.transform(pageItemDTOs, new Function<PageItemDTO, Long>() {
                    @Override
                    public Long apply(PageItemDTO pageItemDTO) {
                        return pageItemDTO.getItemId();
                    }
                });

                //批量查询出所有商品
                ItemQTO itemQTO = new ItemQTO();
                itemQTO.setIdList(itemIdList);
                itemQTO.setSellerId(0L);
                itemQTO.setNeedStockNum(false);//不查询商品库存
                List<ItemDTO> itemList = itemManager.queryItem(itemQTO,appKey);
                logger.info("查询 商品信息:{}",stopWatch.getTime()/1000.0);

                Map<Long,ItemDTO> itemMap = Maps.uniqueIndex(itemList, new Function<ItemDTO,Long>() {
                    public Long apply(ItemDTO from) {
                        return from.getId();
                    }});
                for (PageItemDTO pageItemDTO : pageItemDTOs) {
                    Long itemId = pageItemDTO.getItemId();
                    Long sellerId = pageItemDTO.getSellerId();
                    ItemDTO itemDTO = itemMap.get(itemId);

                    //tms格式的item对象
                    ProductJsonDTO productJsonDTO = new ProductJsonDTO();
                    if (itemDTO!=null){
                        productJsonDTO.setImageUrl(itemDTO.getIconUrl());
                        productJsonDTO.setMarketPrice(itemDTO.getMarketPrice());
                        productJsonDTO.setWirelessPrice(itemDTO.getWirelessPrice());
                        productJsonDTO.setSupplyPlace("");
                        productJsonDTO.setText(itemDTO.getItemName());
                    }
                    //拼接targetUrl ----base:http://m.haiyn.com/detail.html?
                    // targetUrl: "http://m.haiyn.com/detail.html?item_uid=1841254_2430"
                    String targetUrl = baseTargetUrl + "item_uid=" + sellerId + "_" + itemId;
                    productJsonDTO.setTargetUrl(targetUrl);
                    //单个item 加入集合
                    productList.add(productJsonDTO);

                }
                //tms格式的product块
                PublishProductDTO publishProductDTO = new PublishProductDTO();
                publishProductDTO.setProductType(2);
                publishProductDTO.setProductList(productList);

                ContentDTO productContentDTO = new ContentDTO();
                productContentDTO.setValueType(ValueTypeEnum.PRODUCT.getValueType());
                productContentDTO.setValue(publishProductDTO);

                componentList.add(JsonUtil.toJson(productContentDTO));
            }

            if (pageBlock.getBlockType() == 3) {
                String tmsName = pageBlock.getTmsName();

                //http://act.haiyn.com/data/tuijian.json

                String tmsUrl = baseTmsUrl+tmsName+".json";
                String tmsResponse = HttpUtil_q.sendGet(tmsUrl,"utf-8");
                logger.info("查询 tms 数据:{}:{}",tmsName,stopWatch.getTime()/1000.0);

                PublishDTO publishDTO = null;
                try {
                     publishDTO = JsonUtil.parseJson(tmsResponse, PublishDTO.class);
                    logger.info("解析 tms 数据:{}:{}",tmsName,stopWatch.getTime()/1000.0);

                }catch (Exception e){
                    logger.error("解析tms {} json异常 json:{}",tmsName,tmsResponse);
                    throw  new MainWebException (  e.getMessage());
                }


                List<ContentDTO> contentDTOs = publishDTO.getData().getComponent();
                String tmsData = JsonUtil.toJson(contentDTOs);
                componentList.add(tmsData.substring(1,tmsData.length()-1));
            }

            //如果是秒杀块
         /*   if (pageBlock.getBlockType() == 4){
                ContentDTO contentDTO = new ContentDTO();
                List<PageSeckillDTO> pageSeckillDTOs  = pageSeckillManager.queryPageSeckill(pageBlock.getPageId());
                if (pageSeckillDTOs!=null) {
                    contentDTO.setValue(pageSeckillDTOs);
                }
                contentDTO.setValueType(ValueTypeEnum.SECKILL.getValueType());
                componentList.add(JsonUtil.toJson(pageSeckillDTOs));
            }*/


        }

        PublishPageDTO publishPageDTO = new PublishPageDTO();
        publishPageDTO.setPageId(pageId);
        publishPageDTO.setContent("["+ StringUtils.join( componentList,",")+"]");

        return publishPageDTO;
    }



}
