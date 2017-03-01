package item;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.*;
import com.mockuai.itemcenter.core.dao.HotNameDAO;
import com.mockuai.itemcenter.core.dao.ItemDAO;
import com.mockuai.itemcenter.core.domain.HotNameDO;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddItemTest {
	@Resource
	private ItemService itemService;
	@Resource
	private ItemDAO itemDAO;

	@Resource
	private HotNameDAO hotNameDAO;

    @Test
    public void testQueryCompositeItem(){
        List<Long> idList = Lists.newArrayList(3822L,3814L,3795L,208L,301L,261L);
        idList =itemDAO.queryCompositeItem(idList);
        System.out.print("idListfffffffffffffffffff:"+idList);
    }



    @Test
	public void testQueryClimbIndexSort(){
		HotNameDO  hotNameDO = hotNameDAO.queryClimbObj("up",42L);
		System.out.print("name="+hotNameDO.getHotName()+" sort="+hotNameDO.getIndexSort());
	}





	@Test
	/**
	 * 正常插入
	 */
	public void test001() {
		Request request = new BaseRequest();
        request.setParam("appKey", "af7433eda4883d7139e2934dd8d035f1");
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setItemName("婴儿毛巾444");
		itemDTO.setSellerId(38721L);
		itemDTO.setItemBrandId(99L);
		itemDTO.setMarketPrice(33L);
		itemDTO.setPromotionPrice(22L);
		itemDTO.setWirelessPrice(11L);
		itemDTO.setItemDesc("详情");
		itemDTO.setItemType(99);
		itemDTO.setIconUrl("iconUrl");
		itemDTO.setCategoryId(99L);
		itemDTO.setItemBrief("itemBrief");
		itemDTO.setSaleMinNum(99);
		itemDTO.setSaleMinNum(99);
		itemDTO.setItemStatus(99);
		itemDTO.setCornerIconId(99L);
		itemDTO.setCostPrice(99L);
		itemDTO.setDeliveryType(99);
		itemDTO.setBizCode("mockuai_demo");
		
		ItemPropertyDTO itemPropertyDTO = new ItemPropertyDTO();
		itemPropertyDTO.setBizMark(1L);
		itemPropertyDTO.setName("计量单位");
		itemPropertyDTO.setValue("瓶");
		itemPropertyDTO.setPropertyValueId(100001L);
		itemPropertyDTO.setItemPropertyTmplId(20L);
		itemPropertyDTO.setSort(1);
		itemPropertyDTO.setPropertyValueId(101L);
		
		ItemPropertyDTO itemPropertyDTO2 = new ItemPropertyDTO();
		itemPropertyDTO2.setBizMark(1L);
		itemPropertyDTO2.setName("发货国家");
		itemPropertyDTO2.setValue("中国");
		itemPropertyDTO2.setPropertyValueId(100002L);
		itemPropertyDTO2.setItemPropertyTmplId(21L);
		itemPropertyDTO2.setSort(1);
		itemPropertyDTO2.setPropertyValueId(102L);
		
		List<ItemPropertyDTO> itemPropertys = new ArrayList<ItemPropertyDTO>();
		itemPropertys.add(itemPropertyDTO);
		itemPropertys.add(itemPropertyDTO2);
		itemDTO.setItemPropertyList(itemPropertys);
		
		List<ItemSkuDTO> retItemSkuDTOList = new ArrayList<ItemSkuDTO>();
		ItemSkuDTO itemSkuDTO = new ItemSkuDTO();
		itemSkuDTO.setBarCode("a");
		itemSkuDTO.setMarketPrice(100L);
		itemSkuDTO.setPromotionPrice(90L);
		itemSkuDTO.setWirelessPrice(80L);
		itemSkuDTO.setStockNum(1000L);
		itemSkuDTO.setSoldNum(1L);

		SkuPropertyDTO skuPropertyDTO1 = new SkuPropertyDTO();
		skuPropertyDTO1.setName("尺寸");
		skuPropertyDTO1.setSort(1);
		skuPropertyDTO1.setValue("16码");
		skuPropertyDTO1.setPropertyValueId(200001L);
		skuPropertyDTO1.setSkuPropertyTmplId(1L);
		skuPropertyDTO1.setValueType(1);

		SkuPropertyDTO skuPropertyDTO2 = new SkuPropertyDTO();
		skuPropertyDTO2.setName("颜色");
		skuPropertyDTO2.setSort(2);
		skuPropertyDTO2.setValue("红色");
		skuPropertyDTO2.setPropertyValueId(200002L);
		skuPropertyDTO2.setSkuPropertyTmplId(1L);
		skuPropertyDTO2.setValueType(1);

		List<SkuPropertyDTO> skuPropertyDTOList = new ArrayList<SkuPropertyDTO>();
		skuPropertyDTOList.add(skuPropertyDTO1);
		skuPropertyDTOList.add(skuPropertyDTO2);
		itemSkuDTO.setSkuPropertyDTOList(skuPropertyDTOList);

		ItemSkuDTO itemSkuDTO2 = new ItemSkuDTO();
		itemSkuDTO2.setBarCode("b");
		itemSkuDTO2.setMarketPrice(100L);
		itemSkuDTO2.setPromotionPrice(90L);
		itemSkuDTO2.setWirelessPrice(80L);
		itemSkuDTO2.setStockNum(1000L);
		itemSkuDTO2.setSoldNum(1L);

		SkuPropertyDTO skuPropertyDTO3 = new SkuPropertyDTO();
		skuPropertyDTO3.setName("尺寸");
		skuPropertyDTO3.setSort(2);
		skuPropertyDTO3.setValue("17码");
		skuPropertyDTO3.setSkuPropertyTmplId(1L);
		skuPropertyDTO3.setPropertyValueId(1L);

		skuPropertyDTO3.setValueType(1);

		SkuPropertyDTO skuPropertyDTO4 = new SkuPropertyDTO();
		skuPropertyDTO4.setName("颜色");
		skuPropertyDTO4.setSort(1);
		skuPropertyDTO4.setPropertyValueId(200002L);
		skuPropertyDTO4.setValue("蓝色");
		skuPropertyDTO4.setSkuPropertyTmplId(1L);
		skuPropertyDTO4.setValueType(1);

		List<SkuPropertyDTO> skuPropertyDTOList2 = new ArrayList<SkuPropertyDTO>();
		skuPropertyDTOList2.add(skuPropertyDTO3);
		skuPropertyDTOList2.add(skuPropertyDTO4);
		itemSkuDTO2.setSkuPropertyDTOList(skuPropertyDTOList2);

		retItemSkuDTOList.add(itemSkuDTO);
		retItemSkuDTOList.add(itemSkuDTO2);

		itemDTO.setItemSkuDTOList(retItemSkuDTOList);

		ItemImageDTO itemImageDTO = new ItemImageDTO();
		itemImageDTO.setImageName("测试狗11");
		itemImageDTO.setImageType(2);
		itemImageDTO.setImageUrl("http://www.163.com/a.jpeg");
		//itemImageDTO.setImgUrl("http://www.163.com/a.jpeg");

		ItemImageDTO itemImageDTO2 = new ItemImageDTO();
		itemImageDTO2.setImageName("测试狗22");
		itemImageDTO2.setImageType(2);
		itemImageDTO2.setImageUrl("http://www.163.com/a.jpeg");
//		itemImageDTO2.setImgUrl("http://www.163.com/a.jpeg");
		List<ItemImageDTO> itemImageDTOList = new ArrayList<ItemImageDTO>();
		itemImageDTOList.add(itemImageDTO);
		itemImageDTOList.add(itemImageDTO2);

		itemDTO.setItemImageDTOList(itemImageDTOList);

		// buylimit regular;
		LimitEntity limitEntity = new LimitEntity();
		limitEntity.setLimitCount(10);
		limitEntity.setBeginTime(new Date());
		limitEntity.setBeginTime(new Date());
		List<LimitEntity> limitEntities = new ArrayList<LimitEntity>();
		limitEntities.add(limitEntity);
		itemDTO.setBuyLimit(limitEntities);

		request.setParam("itemDTO", itemDTO);
		request.setCommand(ActionEnum.ADD_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * itemDTO为空
	 */
	public void test002() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * 商品名为空
	 */
	public void test003() {
		Request request = new BaseRequest();
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setSellerId(1L);
		//itemDTO.setItemBriefName("商品简称");
		itemDTO.setItemBrandId(1L);
		//itemDTO.setBriefIntroduction("商品简介");
		itemDTO.setItemType(1);
		itemDTO.setIconUrl("http://www.163.com/e.jpeg");
		itemDTO.setCategoryId(1L);
		request.setParam("itemDTO", itemDTO);
		request.setCommand(ActionEnum.ADD_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * 商品简称为空
	 */
	public void test004() {
		Request request = new BaseRequest();
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setItemName("婴儿毛巾444");
		itemDTO.setSellerId(1L);
		itemDTO.setItemBrandId(1L);
		//itemDTO.setBriefIntroduction("商品简介");
		itemDTO.setItemType(1);
		itemDTO.setIconUrl("http://www.163.com/e.jpeg");
		itemDTO.setCategoryId(1L);
		request.setParam("itemDTO", itemDTO);
		request.setCommand(ActionEnum.ADD_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * 商品类型为空
	 */
	public void test005() {
		Request request = new BaseRequest();
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setItemName("婴儿毛巾444");
		//itemDTO.setItemBriefName("商品简称");
		itemDTO.setSellerId(1L);
		itemDTO.setItemBrandId(1L);
		itemDTO.setIconUrl("http://www.163.com/e.jpeg");
		itemDTO.setCategoryId(1L);
		request.setParam("itemDTO", itemDTO);
		request.setCommand(ActionEnum.ADD_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * 主图图片地址为空
	 */
	public void test006() {
		Request request = new BaseRequest();
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setItemName("婴儿毛巾444");
		itemDTO.setSellerId(1L);
		//itemDTO.setItemBriefName("商品简称");
		itemDTO.setItemBrandId(1L);
		//itemDTO.setBriefIntroduction("商品简介");
		itemDTO.setItemType(1);
		itemDTO.setCategoryId(1L);
		request.setParam("itemDTO", itemDTO);
		request.setCommand(ActionEnum.ADD_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * 类目ID为空
	 */
	public void test007() {
		Request request = new BaseRequest();
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setItemName("婴儿毛巾444");
		itemDTO.setSellerId(1L);
		//itemDTO.setItemBriefName("商品简称");
		itemDTO.setItemBrandId(1L);
		//itemDTO.setBriefIntroduction("商品简介");
		itemDTO.setItemType(1);
		itemDTO.setIconUrl("http://www.163.com/e.jpeg");
		request.setParam("itemDTO", itemDTO);
		request.setCommand(ActionEnum.ADD_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * 供应商ID为空
	 */
	public void test008() {
		Request request = new BaseRequest();
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setItemName("婴儿毛巾444");
		//itemDTO.setItemBriefName("商品简称");
		itemDTO.setItemBrandId(1L);
		//itemDTO.setBriefIntroduction("商品简介");
		itemDTO.setItemType(1);
		itemDTO.setIconUrl("http://www.163.com/e.jpeg");
		itemDTO.setCategoryId(1L);
		request.setParam("itemDTO", itemDTO);
		request.setCommand(ActionEnum.ADD_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * 类目ID在数据库不存在
	 */
	public void test009() {
		Request request = new BaseRequest();
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setItemName("婴儿毛巾444");
		itemDTO.setSellerId(1L);
		//itemDTO.setItemBriefName("商品简称");
		itemDTO.setItemBrandId(1L);
		//itemDTO.setBriefIntroduction("商品简介");
		itemDTO.setItemType(1);
		itemDTO.setIconUrl("http://www.163.com/e.jpeg");
		itemDTO.setCategoryId(9999L);
		request.setParam("itemDTO", itemDTO);
		request.setCommand(ActionEnum.ADD_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}


	/**
	 * 品牌ID在数据库不存在
	 */
	public void test010() {
		Request request = new BaseRequest();
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setItemName("婴儿毛巾444");
		itemDTO.setSellerId(1L);
		//itemDTO.setItemBriefName("商品简称");
		itemDTO.setItemBrandId(9999L);
		//itemDTO.setBriefIntroduction("商品简介");
		itemDTO.setItemType(1);
		itemDTO.setIconUrl("http://www.163.com/e.jpeg");
		itemDTO.setCategoryId(1L);
		request.setParam("itemDTO", itemDTO);
		request.setCommand(ActionEnum.ADD_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}


}
