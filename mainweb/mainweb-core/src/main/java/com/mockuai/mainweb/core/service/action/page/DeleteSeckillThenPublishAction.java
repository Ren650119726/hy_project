package com.mockuai.mainweb.core.service.action.page;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.mainweb.common.api.action.MainWebResponse;
import com.mockuai.mainweb.common.api.action.Request;
import com.mockuai.mainweb.common.constant.ActionEnum;
import com.mockuai.mainweb.common.constant.ResponseCode;
import com.mockuai.mainweb.common.constant.ValueTypeEnum;
import com.mockuai.mainweb.common.domain.dto.*;
import com.mockuai.mainweb.common.domain.dto.publish.*;
import com.mockuai.mainweb.core.domain.PageBlockDO;
import com.mockuai.mainweb.core.domain.PageGalleryDO;
import com.mockuai.mainweb.core.domain.PageItemCategoryDO;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.*;
import com.mockuai.mainweb.core.service.action.RequestContext;
import com.mockuai.mainweb.core.service.action.TransAction;
import com.mockuai.mainweb.core.util.HttpUtil_q;
import com.mockuai.mainweb.core.util.JsonUtil;
import com.mockuai.mainweb.core.util.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsq on 2016/10/11.
 */
@Service
public class DeleteSeckillThenPublishAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(DeleteSeckillThenPublishAction.class);
    @Resource
    private ItemManager itemManager;

    @Resource
    private PageBlockManager pageBlockManager;

    @Resource
    private PageGalleryManager pageGalleryManager;

    @Resource
    private PageItemCategoryManager pageItemCategoryManager;

    @Resource
    private PageItemManager pageItemManager;

    @Resource
    private PublishPageManager publishPageManager;

    @Resource
    private PageManager pageManager;

    @Resource
    private PageSeckillManager pageSeckillManager;

    public final static String baseTargetUrl = "http://m.haiyn.com/detail.html?";
    public final static String baseTmsUrl = "http://act.haiyn.com/data/";

    @Override
    protected MainWebResponse doTransaction(RequestContext context) throws MainWebException {

        Request request = context.getRequest();

        PageSeckillDTO pageSeckillDTO = (PageSeckillDTO) request.getParam("pageSeckillDTO");//pageSeckillDTO 暂时冗余

        Boolean isMainPage = (Boolean) request.getParam("isMainPage");//isMainPage 暂时冗余

        String appKey = (String) request.getParam("appKey");
        IndexPageDTO indexPageDTO = null;
        Long pageId = null;
        try {

            List<IndexPageDTO> publishedPageDTOs = pageManager.queryPublishPageNames();
            for (IndexPageDTO publishedPageDTO : publishedPageDTOs){
                if (publishedPageDTO.getName().equals("推荐")){//暂时只会删除 推荐页面
                    pageId = publishedPageDTO.getId();
                    //删除 名字叫推荐的首页 重新走发布
                    publishPageManager.deletePublishPage(pageId);
                }
            }
            //删除秒杀块
//            pageSeckillManager.deletePageSeckill(pageId);

//            List<IndexPageDTO> publishedPageDTOs = pageManager.queryPublishPageNames();
//            for (IndexPageDTO publishedPageDTO : publishedPageDTOs){
//                if (publishedPageDTO.getId().equals(pageId)){
//                    publishPageManager.deletePublishPage(pageId);
//                }
//            }

            indexPageDTO = pageManager.getPage(pageId);
            List<PageBlockDO> pageBlockList = pageBlockManager.queryPageBlock(pageId);

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

                    for (PageItemDTO pageItemDTO : pageItemDTOs) {
                        Long itemId = pageItemDTO.getItemId();
                        Long sellerId = pageItemDTO.getSellerId();
                        ItemDTO itemDTO = itemManager.getItemById(itemId, sellerId, true,appKey);

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

                    PublishDTO publishDTO = JsonUtil.parseJson(tmsResponse, PublishDTO.class);

                    List<ContentDTO> contentDTOs = publishDTO.getData().getComponent();
                    String tmsData = JsonUtil.toJson(contentDTOs);
                    componentList.add(tmsData.substring(1,tmsData.length()-1));
                }

            }

            PublishPageDTO publishPageDTO = new PublishPageDTO();
            publishPageDTO.setPageId(pageId);


            publishPageDTO.setContent("["+ StringUtils.join( componentList,",")+"]");
            publishPageManager.addPublishPage(publishPageDTO);
            //跟新page 表的publish_status状态
            indexPageDTO.setPublishStatus(1);
            pageManager.updatePage(indexPageDTO);

//            //对tab表进行操作
//            List<IndexPageDTO> pageDTOs = pageManager.queryPublishPageNames();
//            List<PageIdNamePairDTO> pageNames = new ArrayList<>();
//            for (IndexPageDTO pageDTO : pageDTOs){
//                PageIdNamePairDTO pageIdNamePair = new PageIdNamePairDTO();
//                pageIdNamePair.setId(pageDTO.getId());
//                pageIdNamePair.setName(pageDTO.getName());
//                pageNames.add(pageIdNamePair);
//            }
//            String content = JsonUtil.toJson(pageNames);
//
//            PublishTabDTO pubTab = publishTabManager.getPublishTab();
//            //每次对publish_tab都是先删除再添加
//
//            //Long id = publishTabManager.confirmRecordExist();
//            if(pubTab == null){
//                pubTab = new PublishTabDTO();
//                pubTab.setContent(content);
//                publishTabManager.addPublishTab(pubTab);
//            }else{
//                pubTab.setContent(content);
//                publishTabManager.updatePublishTab(pubTab);
//            }

            return ResponseUtil.getSuccessResponse();
        } catch (MainWebException e) {
            log.error("删除秒杀块发布首页失败，DeleteSeckillThenPublish ", e);
            return ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.DELETE_SECKILL_THEN_PUBLISH.getActionName();
    }
}
