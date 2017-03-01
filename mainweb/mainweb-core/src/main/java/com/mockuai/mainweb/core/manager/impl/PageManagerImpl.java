package com.mockuai.mainweb.core.manager.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.mainweb.common.constant.PageTypeEnum;
import com.mockuai.mainweb.common.constant.ResponseCode;
import com.mockuai.mainweb.common.constant.ValueTypeEnum;
import com.mockuai.mainweb.common.domain.dto.*;
import com.mockuai.mainweb.common.domain.dto.publish.*;
import com.mockuai.mainweb.common.domain.qto.PageGridQTO;
import com.mockuai.mainweb.common.domain.qto.PageQTO;
import com.mockuai.mainweb.core.dao.PageDAO;
import com.mockuai.mainweb.core.domain.*;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.*;
import com.mockuai.mainweb.core.util.HttpUtil_q;
import com.mockuai.mainweb.core.util.JsonUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20.
 */
@Service
public class PageManagerImpl implements PageManager {

    @Resource
    private PageDAO pageDAO;


    @Resource
    private PageGalleryManager pageGalleryManager;

    @Resource
    private PageBlockManager pageBlockManager;

    @Autowired
    private PageItemCategoryManager pageItemCategoryManager;

    @Resource
    private PageItemManager pageItemManager;
    
    @Resource
    private PageGridManager pageGridManager;

    @Resource
    private ItemManager itemManager;

    @Resource
    private PagePictureManager pagePictureManager;

    private Logger logger = LoggerFactory.getLogger(PageManagerImpl.class);


    public final static String baseTargetUrl = "http://m.haiyn.com/detail.html?";
    public final static String baseTmsUrl = "http://act.haiyn.com/data/";


    @Override
    public void addPage(IndexPageDTO indexPageDTO) throws MainWebException {
        IndexPageDO indexPageDO = new IndexPageDO();
        BeanUtils.copyProperties(indexPageDTO, indexPageDO);
        try {
            this.pageDAO.addPage(indexPageDO);
            indexPageDTO.setId(indexPageDO.getId());
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_INSERT,  e);
        }
    }

    @Override
    public void addBizPage(IndexPageDTO indexPageDTO) throws MainWebException {

            List<PageBlockDTO<ImageElementDTO>> imgBlockList =   indexPageDTO.getImgPageBlockList();
            List<PageBlockDTO<ItemListElementDTO>>   itemBlockDTOList = indexPageDTO.getItemPageBlockList();
            List<PageBlockDTO> tmsBlockList = indexPageDTO.getTmsPageBlockList();
            List<PageBlockDTO<PageGridElementDTO>> gridBlockList = indexPageDTO.getGridPageBlockList();
            List<PageBlockDTO<PagePictureDTO>> pictureBlockList = indexPageDTO.getPicturePageBlockList();


            //遍历图片块
//            if (CollectionUtils.isNotEmpty(pictureBlockList)) {
        logger.info(" @@@@@@@@@ gridBlockList : "+JSONObject.toJSONString(pictureBlockList));
                for (PageBlockDTO<PagePictureDTO> pictureBlockDTO : pictureBlockList) {
                    pictureBlockDTO.setPageId(indexPageDTO.getId());
                    pageBlockManager.addPageBlock(pictureBlockDTO);
                    List<PagePictureDTO> pagePictureDTOS = pictureBlockDTO.getElementList();

                    for (PagePictureDTO pagePictureDTO : pagePictureDTOS) {
                        pagePictureDTO.setBlockId(pictureBlockDTO.getId());
                        pagePictureDTO.setPageId(indexPageDTO.getId());
                        pagePictureManager.addPagePictureBlock(pagePictureDTO);
                    }
                }
//            }

            //遍历装有轮播图片 的 PageBlockDTO
            for (PageBlockDTO<ImageElementDTO> imagePageBlockDTO : imgBlockList ){
                imagePageBlockDTO.setPageId(indexPageDTO.getId());
                pageBlockManager.addPageBlock(imagePageBlockDTO);
                List<ImageElementDTO> imageElementDTOs = imagePageBlockDTO.getElementList();

                for (ImageElementDTO imageElementDTO : imageElementDTOs){
                    imageElementDTO.setBlockId(imagePageBlockDTO.getId());
                    imageElementDTO.setPageId(indexPageDTO.getId());
                    PageGalleryDO pageGalleryDO = new PageGalleryDO();
                    BeanUtils.copyProperties(imageElementDTO,pageGalleryDO);
                    pageGalleryDO.setTarget(imageElementDTO.getTargetUrl());
                    pageGalleryDO.setImageUri(imageElementDTO.getImageUrl());
                    pageGalleryManager.addPageGallery(pageGalleryDO);
                }

            }

            //遍历装有商品块的 PageBlockDTO
            for (PageBlockDTO<ItemListElementDTO> itemPageBlockDTO : itemBlockDTOList){
                itemPageBlockDTO.setPageId(indexPageDTO.getId());
                pageBlockManager.addPageBlock(itemPageBlockDTO);
                List<ItemListElementDTO> pageItemDTOs = itemPageBlockDTO.getElementList();

                for (ItemListElementDTO itemListElementDTO : pageItemDTOs){
                    itemListElementDTO.setBlockId(itemPageBlockDTO.getId());
                    itemListElementDTO.setPageId(indexPageDTO.getId());
                    //保存商品列表
                    PageItemCategoryDO pageItemCategory = new PageItemCategoryDO();
                    BeanUtils.copyProperties(itemListElementDTO,pageItemCategory);
                    pageItemCategoryManager.addPageItemCategory(pageItemCategory);
                    //保存商品
                    List<PageItemDTO> itemDTOs = itemListElementDTO.getProductListList();
                    List<PageItemDO> pageItemDOList = new ArrayList<>(itemDTOs.size());
                    for (PageItemDTO pageItemDTO:itemDTOs){

                        pageItemDTO.setPageItemCategoryId(pageItemCategory.getId());
                        pageItemDTO.setPageId(indexPageDTO.getId());
                        PageItemDO pageItemDO = new PageItemDO();
                        BeanUtils.copyProperties(pageItemDTO,pageItemDO);
                        pageItemDOList.add(pageItemDO);
                        //  pageItemManager.addPageItem(pageItemDO);
                    }
                    pageItemManager.addPageItem(pageItemDOList);
                }
            }
        Iterator<PageBlockDTO> iterator = tmsBlockList.iterator();
        while (iterator.hasNext()){
            PageBlockDTO blockDTO = iterator.next();
                String tmsName = blockDTO.getTmsName();
                String tmsUrl = baseTmsUrl+tmsName+".json";
                String tmsResponse = HttpUtil_q.sendGet(tmsUrl,"utf-8");
                if ("".equals(tmsResponse)){
//                    iterator.remove();
                    throw new MainWebException("TMS 名字配置有误");
                }
                blockDTO.setPageId(indexPageDTO.getId());
            }

            //保存tms 列表
           if(CollectionUtils.isNotEmpty(tmsBlockList)){
               pageBlockManager.addPageBlockList(tmsBlockList);
           }
           
           
           // 格子块
           if(CollectionUtils.isNotEmpty(gridBlockList)){
        	   for(PageBlockDTO<PageGridElementDTO> blockGridElementDTO:gridBlockList){
            	   blockGridElementDTO.setPageId(indexPageDTO.getId());
                   pageBlockManager.addPageBlock(blockGridElementDTO);
                   if(CollectionUtils.isNotEmpty(blockGridElementDTO.getElementList())){    
                	   logger.info(" @@@@@@@@@  blockGridElementDTO.getElementList() : "+JSONObject.toJSONString(blockGridElementDTO.getElementList()));
                	   for(PageGridElementDTO pageGridElementDTO:blockGridElementDTO.getElementList()){
                		   pageGridElementDTO.setPageId(indexPageDTO.getId());
                		   pageGridElementDTO.setBlockId(blockGridElementDTO.getId());
                		   //主表
                		   pageGridManager.addGrid(pageGridElementDTO);
                		   if(CollectionUtils.isNotEmpty(pageGridElementDTO.getGridList())){
                			   for(PageGridContentDTO pageGridContentDTO:pageGridElementDTO.getGridList()){
                				   logger.info(" ############# pageGridContentDTO:{} ",JsonUtil.toJson(pageGridContentDTO));
                				   pageGridContentDTO.setPageId(indexPageDTO.getId());
                				   pageGridContentDTO.setBlockId(pageGridElementDTO.getBlockId());
                				   pageGridContentDTO.setGridId(pageGridElementDTO.getId());
                				   //明细表
                				   pageGridManager.addGridContent(pageGridContentDTO);                				   
                    			   if(CollectionUtils.isNotEmpty(pageGridContentDTO.getIds())){
                    				   for(Integer id:pageGridContentDTO.getIds()){
                    					   PageGridMergeCellsDTO pageGridMergeCellsDTO = new PageGridMergeCellsDTO();
                    					   pageGridMergeCellsDTO.setCellNo(id);
                    					   pageGridMergeCellsDTO.setPageId(indexPageDTO.getId());
                    					   pageGridMergeCellsDTO.setBlockId(pageGridContentDTO.getBlockId());
                    					   pageGridMergeCellsDTO.setGridId(pageGridContentDTO.getGridId());
                    					   pageGridMergeCellsDTO.setGridContentId(pageGridContentDTO.getId());
                    					   //单元格
                    					   pageGridManager.addGridMergeCells(pageGridMergeCellsDTO);
                        			   }
                    			   }
                				   
                    		   }
                		   }
                	   }
                   }
            	   
               }
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

            
            PageGridQTO pageGridQTO = new PageGridQTO(); 
            pageGridQTO.setPageId(id);
            pageGridManager.deleteGrid(pageGridQTO);
            pageGridManager.deleteGridContent(pageGridQTO);
            pageGridManager.deleteGridMergeCells(pageGridQTO);
        }catch (MainWebException e){
            throw  new MainWebException(e.getCode(),  e.getMessage());
        }
    }

    @Override
    public List<IndexPageDTO> queryPageNameList() throws MainWebException {

        List<IndexPageDO> indexPageDOs = null;
        try {
            indexPageDOs = this.pageDAO.queryPageNameList();
            List<IndexPageDTO> indexPageDTOs = new ArrayList<>();

            for (IndexPageDO indexPageDO : indexPageDOs){
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
    public void updatePage(IndexPageDTO indexPageDTO) throws MainWebException {
        IndexPageDO indexPageDO = new IndexPageDO();
        BeanUtils.copyProperties(indexPageDTO, indexPageDO);
        try {
            this.pageDAO.updatePage(indexPageDO);
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_UPDATE,  e);

        }
    }



    @Override
    public IndexPageDTO getPage(Long pageId) throws MainWebException {
        IndexPageDTO indexPageDTO = new IndexPageDTO();
        try {
           IndexPageDO pageDO =   pageDAO.getPage(pageId);
            if (pageDO!=null){
                BeanUtils.copyProperties(pageDO,indexPageDTO);
            }
           return indexPageDTO;
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_QUERY,  e);
        }

    }

    @Override
    public IndexPageDTO getPage(IndexPageDTO indexPageDTO, String appKey) throws MainWebException {

        indexPageDTO = getPage(indexPageDTO.getId());
        List<PageBlockDO> pageBlockList = pageBlockManager.queryPageBlock(indexPageDTO.getId());
        List<PageBlockDTO> allBlockList = new ArrayList<>();
        List<PageBlockDTO> tmsBlockList = new ArrayList<>();
        List<PageBlockDTO<ItemListElementDTO>> itemBlockList = new ArrayList<>();
        List<PageBlockDTO<ImageElementDTO>> imgBlockList = new ArrayList<>();
        List<PageBlockDTO<PageGridElementDTO>> gridBlockList = Lists.newArrayList(); // new ArrayList<PageBlockDTO<PageGridElementDTO>>();
        List<PageBlockDTO<PagePictureDTO>> pictureBlockList = new ArrayList<>();

        for (PageBlockDO pageBlock : pageBlockList) {
            PageBlockDTO pageBlockDTO = new PageBlockDTO();
            BeanUtils.copyProperties(pageBlock,pageBlockDTO);
            switch (pageBlock.getBlockType()) {
                case 1:
                    List<PageGalleryDO> pageGalleryList = pageGalleryManager.queryPageGallery(pageBlock.getId());
                    List<ImageElementDTO> imageElementList = new ArrayList<>(pageGalleryList.size());
                    //PageGalleryDO 和 ImageElementDTO 一回事
                    for (PageGalleryDO pageGallery : pageGalleryList) {
                        ImageElementDTO imageElement = new ImageElementDTO();
                        BeanUtils.copyProperties(pageGallery, imageElement);
                        imageElement.setImageUrl(pageGallery.getImageUri());
                        imageElement.setTargetUrl(pageGallery.getTarget());
                        imageElementList.add(imageElement);
                    }
                    pageBlockDTO.setElementList(imageElementList);
                    imgBlockList.add(pageBlockDTO);
                    allBlockList.add(pageBlockDTO);
                    break;

                case 2:
                    ItemListElementDTO itemListElement = new ItemListElementDTO();
                    PageItemCategoryDO pageItemCategory = pageItemCategoryManager.getPageItemCategory(pageBlock.getId());
                    BeanUtils.copyProperties(pageItemCategory, itemListElement);
                    List<PageItemDTO> pageItemDTOList = pageItemManager.queryPageItem(pageItemCategory.getId());
                    List<PageItemDTO> copyPageList = new ArrayList<>();
                    copyPageList.addAll(pageItemDTOList);
                    List<Long> itemIdList = Lists.transform(pageItemDTOList, new Function<PageItemDTO, Long>() {
                        @Override
                        public Long apply(PageItemDTO pageItemDTO) {
                            return pageItemDTO.getItemId();
                        }
                    });
                    //批量查询出所有商品
                    ItemQTO itemQTO = new ItemQTO();
                    itemQTO.setIdList(itemIdList);
                    itemQTO.setSellerId(0L);
                    itemQTO.setNeedStockNum(false);
                    List<ItemDTO> itemList = itemManager.queryItem(itemQTO,appKey);

                    Map<Long,ItemDTO> itemMap = Maps.uniqueIndex(itemList, new Function<ItemDTO,Long>() {
                        public Long apply(ItemDTO from) {
                            return from.getId();
                    }});

                    //判断哪些itemId未被查询出。
                    itemIdList.removeAll(itemMap.keySet());

                    List<PageItemDTO> retainPageItemList = Lists.newArrayList();
                    //有一些商品未查询出
                    if(CollectionUtils.isNotEmpty(itemIdList)){
                        for(PageItemDTO pageItem : copyPageList){
                            //已经被删除的商品 则不显示
                            if(!itemIdList.contains(pageItem.getItemId())){
                                retainPageItemList.add(pageItem);
                            }
                        }
                    }else{
                        retainPageItemList = copyPageList;
                    }

                    for(PageItemDTO pageItem : retainPageItemList){
                        ItemDTO itemDTO = itemMap.get(pageItem.getItemId());
                        if(itemDTO == null){
                            logger.error("item id:{}读取失败",pageItem.getItemId());
                            continue;
                        }
                        pageItem.setIconUrl(itemDTO.getIconUrl());
                        pageItem.setItemName(itemDTO.getItemName());
                        pageItem.setMarketPrice(itemDTO.getMarketPrice());
                    }
					itemListElement.setProductListList(retainPageItemList);
                    pageBlockDTO.setElementList(Lists.newArrayList(itemListElement));
                    allBlockList.add(pageBlockDTO);
                    itemBlockList.add(pageBlockDTO);
                    break;
                case 3:
                    tmsBlockList.add(pageBlockDTO);
                    allBlockList.add(pageBlockDTO);
                    break;
                case 5:

                	List<PageGridElementDTO> pageGridElementDTOs = new ArrayList<PageGridElementDTO>();
                	
                	PageGridQTO pageGridQTO = new PageGridQTO();
                	pageGridQTO.setBlockId(pageBlock.getId());
                	pageGridQTO.setDeleteMark(0);
                	PageGridDTO pageGridDTO = pageGridManager.getGrid(pageGridQTO);
                	
                	PageGridElementDTO pageGridElementDTO = new PageGridElementDTO();
                	BeanUtils.copyProperties(pageGridDTO, pageGridElementDTO);
                	

                	pageGridQTO.setGridId(pageGridDTO.getId());
                	List<PageGridContentDTO> pageGridContentDTOs = pageGridManager.queryGridContent(pageGridQTO);
                	logger.info(" $$$$$$$$ pageGridContentDTOs : "+JsonUtil.toJson(pageGridContentDTOs));
                	for(PageGridContentDTO pageGridContentDTO:pageGridContentDTOs){

                		pageGridQTO.setGridContentId(pageGridContentDTO.getId());
                		logger.info(" $$$$$$$$$$ pageGridQTO : "+JsonUtil.toJson(pageGridQTO));
                		List<PageGridMergeCellsDTO> pageGridMergeCellsDTOs = pageGridManager.queryGridMergeCells(pageGridQTO);
                		logger.info(" $$$$$$$$$$ pageGridMergeCellsDTOs : "+JsonUtil.toJson(pageGridMergeCellsDTOs));
                		List<Integer> celssNos = new ArrayList<Integer>();
                		for(PageGridMergeCellsDTO pageGridMergeCellsDTO:pageGridMergeCellsDTOs){
                			celssNos.add(pageGridMergeCellsDTO.getCellNo());
                		}
                		
                		pageGridContentDTO.setIds(celssNos);
                	}
                	
                	pageGridElementDTO.setGridList(pageGridContentDTOs);
                	pageGridElementDTOs.add(pageGridElementDTO);
                	pageBlockDTO.setElementList(pageGridElementDTOs);
                	
                	gridBlockList.add(pageBlockDTO);
                    allBlockList.add(pageBlockDTO);
                	break;

                case 6:
                    // TODO: 2016/12/19
                    List<PagePictureDTO> pagePictureDTOList = pagePictureManager.queryPagePicture(pageBlock.getId());
                    pageBlockDTO.setElementList(pagePictureDTOList);
                    pictureBlockList.add(pageBlockDTO);
                    allBlockList.add(pageBlockDTO);
                    break;
            }

        }
        indexPageDTO.setTmsPageBlockList(tmsBlockList);
        indexPageDTO.setItemPageBlockList(itemBlockList);
        indexPageDTO.setImgPageBlockList(imgBlockList);
        indexPageDTO.setAllPageBlockList(allBlockList);
        indexPageDTO.setGridPageBlockList(gridBlockList);
        indexPageDTO.setPicturePageBlockList(pictureBlockList);
        return indexPageDTO;
    }

    @Override
    public List<IndexPageDTO> queryPublishPageNames() throws MainWebException {
        List<IndexPageDTO> indexPageDTOs = new ArrayList<>();
        try {
            List<IndexPageDO> indexPageDOs = pageDAO.queryPublishPageNames();
            logger.info("indexPadgeDOs:{}",JsonUtil.toJson(indexPageDOs));
            for (IndexPageDO indexPageDO : indexPageDOs){
                if(new Integer(PageTypeEnum.HOME_PAGE.getCode()).equals(indexPageDO.getType())) {
                    IndexPageDTO indexPageDTO = new IndexPageDTO();
                    BeanUtils.copyProperties(indexPageDO, indexPageDTO);
                    indexPageDTOs.add(indexPageDTO);
                }
            }
            logger.info("indexPageDTO:{}",JsonUtil.toJson(indexPageDTOs));
            return indexPageDTOs;
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_QUERY,  e);
        }

    }

    @Override
    public PublishPageDTO generatePageJson(Long pageId, String appKey) throws MainWebException {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<PageBlockDO> pageBlockList = pageBlockManager.queryPageBlock(pageId);
        logger.info("pageBlockList:{}",JsonUtil.toJson(pageBlockList));
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
            //如果是格子块
            if (pageBlock.getBlockType() == 5){

                PageGridQTO pageGridQTO = new PageGridQTO();
                pageGridQTO.setBlockId(pageBlock.getId());
                pageGridQTO.setDeleteMark(0);
                PageGridDTO pageGridDTO = pageGridManager.getGrid(pageGridQTO);

                PageGridElementDTO pageGridElementDTO = new PageGridElementDTO();
                BeanUtils.copyProperties(pageGridDTO, pageGridElementDTO);


                pageGridQTO.setGridId(pageGridDTO.getId());
                List<PageGridContentDTO> pageGridContentDTOs = pageGridManager.queryGridContent(pageGridQTO);
                for(PageGridContentDTO pageGridContentDTO:pageGridContentDTOs){

                    pageGridQTO.setGridContentId(pageGridContentDTO.getId());
                    List<PageGridMergeCellsDTO> pageGridMergeCellsDTOs = pageGridManager.queryGridMergeCells(pageGridQTO);
                    List<Integer> celssNos = new ArrayList<Integer>();
                    for(PageGridMergeCellsDTO pageGridMergeCellsDTO:pageGridMergeCellsDTOs){
                        celssNos.add(pageGridMergeCellsDTO.getCellNo());
                    }

                    pageGridContentDTO.setIds(celssNos);
                }

                pageGridElementDTO.setGridList(pageGridContentDTOs);
                ContentDTO gridContent = new ContentDTO();
                gridContent.setValueType(ValueTypeEnum.GRID.getValueType());
                gridContent.setValue(pageGridElementDTO);
                logger.info("generateGrid:{}",JsonUtil.toJson(gridContent));
                componentList.add(JsonUtil.toJson(gridContent));


            }

            //图片块
            if (pageBlock.getBlockType() == 6){
                ContentDTO contentDTO = new ContentDTO();
                contentDTO.setValueType(ValueTypeEnum.PICTURE.getValueType());

                List<PagePictureDTO> pagePictureDTOList = pagePictureManager.queryPagePicture(pageBlock.getId());
                contentDTO.setValue(pagePictureDTOList);
                componentList.add(JsonUtil.toJson(contentDTO));
            }



        }

        PublishPageDTO publishPageDTO = new PublishPageDTO();
        try {
            IndexPageDO pageDO = pageDAO.getPage(pageId);
            publishPageDTO.setName(pageDO.getName());
        }catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_QUERY,  e);
        }
        publishPageDTO.setPageId(pageId);
        publishPageDTO.setContent("["+ StringUtils.join( componentList,",")+"]");

        return publishPageDTO;
    }

    @Override
    public List<IndexPageDTO> showPageList(PageQTO pageQTO) throws MainWebException {
        List<IndexPageDTO> indexPageDTOs = new ArrayList<>();
        try {
            List<IndexPageDO> indexPageDOs = pageDAO.showPageList(pageQTO);
            for (IndexPageDO indexPageDO : indexPageDOs){
                IndexPageDTO indexPageDTO = new IndexPageDTO();
                BeanUtils.copyProperties(indexPageDO,indexPageDTO);
                indexPageDTOs.add(indexPageDTO);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return indexPageDTOs;
    }

    @Override
    public void cancelPage(Long id) throws MainWebException {
        try {
            this.pageDAO.cancelPage(id);
        } catch (SQLException e) {
            throw  new MainWebException(ResponseCode.SYS_E_DB_UPDATE,  e);

        }
    }

   /* @Override
    public List<ContentDTO> previewPages(Long pageId ,String appKey) throws MainWebException {
        List<PageBlockDO> pageBlockList = pageBlockManager.queryPageBlock(pageId);
        logger.info("pageBlockList:{}", JsonUtil.toJson(pageBlockList));
        List<ContentDTO> component = new ArrayList<>();

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

                component.add(contentDTO);
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
                        component.add(headerContentDTO);
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

                component.add(productContentDTO);
            }

            if (pageBlock.getBlockType() == 3) {
                String tmsName = pageBlock.getTmsName();

                //http://act.haiyn.com/data/tuijian.json

                String tmsUrl = baseTmsUrl+tmsName+".json";
                String tmsResponse = HttpUtil_q.sendGet(tmsUrl,"utf-8");

                PublishDTO publishDTO = null;
                try {
                    publishDTO = JsonUtil.parseJson(tmsResponse, PublishDTO.class);

                }catch (Exception e){
                    throw  new MainWebException (  e.getMessage());
                }


                List<ContentDTO> contentDTOs = publishDTO.getData().getComponent();
//                component.add(contentDTOs);
            }

            //如果是秒杀块
         *//*   if (pageBlock.getBlockType() == 4){
                ContentDTO contentDTO = new ContentDTO();
                List<PageSeckillDTO> pageSeckillDTOs  = pageSeckillManager.queryPageSeckill(pageBlock.getPageId());
                if (pageSeckillDTOs!=null) {
                    contentDTO.setValue(pageSeckillDTOs);
                }
                contentDTO.setValueType(ValueTypeEnum.SECKILL.getValueType());
                componentList.add(JsonUtil.toJson(pageSeckillDTOs));
            }*//*
            //如果是格子块
            if (pageBlock.getBlockType() == 5){
                List<PageGridElementDTO> pageGridElementDTOs = new ArrayList<PageGridElementDTO>();

                PageGridQTO pageGridQTO = new PageGridQTO();
                pageGridQTO.setBlockId(pageBlock.getId());
                pageGridQTO.setDeleteMark(0);
                PageGridDTO pageGridDTO = pageGridManager.getGrid(pageGridQTO);

                PageGridElementDTO pageGridElementDTO = new PageGridElementDTO();
                BeanUtils.copyProperties(pageGridDTO, pageGridElementDTO);


                pageGridQTO.setGridId(pageGridDTO.getId());
                List<PageGridContentDTO> pageGridContentDTOs = pageGridManager.queryGridContent(pageGridQTO);
                for(PageGridContentDTO pageGridContentDTO:pageGridContentDTOs){

                    pageGridQTO.setGridContentId(pageGridContentDTO.getId());
                    List<PageGridMergeCellsDTO> pageGridMergeCellsDTOs = pageGridManager.queryGridMergeCells(pageGridQTO);
                    List<Integer> celssNos = new ArrayList<Integer>();
                    for(PageGridMergeCellsDTO pageGridMergeCellsDTO:pageGridMergeCellsDTOs){
                        celssNos.add(pageGridMergeCellsDTO.getCellNo());
                    }

                    pageGridContentDTO.setIds(celssNos);
                }

                pageGridElementDTO.setGridList(pageGridContentDTOs);
                pageGridElementDTOs.add(pageGridElementDTO);
                ContentDTO GridContent = new ContentDTO();
                GridContent.setValueType(ValueTypeEnum.GRID.getValueType());
                GridContent.setValue(GridContent);
                logger.info("generateGrid:{}",JsonUtil.toJson(GridContent));
                component.add(GridContent);


            }

        }
        return  component;
    }*/

}
