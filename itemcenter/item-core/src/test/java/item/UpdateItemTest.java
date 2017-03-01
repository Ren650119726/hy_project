package item;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.*;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.util.DateUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.AppKeyEnum;
import util.RequestFactory;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UpdateItemTest {
	@Resource
	private ItemService itemService;
	@Resource
	private ItemManager itemManager;
	
	
	public void test(){
		
		Request request = new BaseRequest();
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setId(119L);
		itemDTO.setItemName("abcdedjlfdljfl");
		itemDTO.setSellerId(100L);
		itemDTO.setItemBrandId(1L);
		itemDTO.setItemDesc("商品简介修改");
		itemDTO.setItemType(1);
		itemDTO.setIconUrl("http://www.163.com/e.jpeg");
		itemDTO.setCategoryId(11L);
		
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
		itemPropertyDTO2.setName("发货国家修改");
		itemPropertyDTO2.setValue("中国修改");
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
		//itemSkuDTO.setMaterialCode("aa");
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
		skuPropertyDTO1.setSkuPropertyTmplId(1111L);
		skuPropertyDTO1.setValueType(1);

		SkuPropertyDTO skuPropertyDTO2 = new SkuPropertyDTO();
		skuPropertyDTO2.setName("颜色");
		skuPropertyDTO2.setSort(2);
		skuPropertyDTO2.setValue("红色");
		skuPropertyDTO2.setSkuPropertyTmplId(1111L);
		skuPropertyDTO2.setPropertyValueId(200002L);
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
		skuPropertyDTO3.setName("尺寸修改");
		skuPropertyDTO3.setSort(2);
		skuPropertyDTO3.setValue("17码修改");
		skuPropertyDTO3.setSkuPropertyTmplId(1111L);
		skuPropertyDTO3.setPropertyValueId(111L);
		skuPropertyDTO3.setValueType(1);

		SkuPropertyDTO skuPropertyDTO4 = new SkuPropertyDTO();
		skuPropertyDTO4.setName("颜色修改");
		skuPropertyDTO4.setSort(1);
		skuPropertyDTO4.setPropertyValueId(200002L);
		skuPropertyDTO4.setSkuPropertyTmplId(1111L);
		skuPropertyDTO4.setValue("蓝色修改");
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
		//itemImageDTO.setImgUrl("http://www.163.com/a.jpeg");

		ItemImageDTO itemImageDTO2 = new ItemImageDTO();
		itemImageDTO2.setImageName("测试狗22");
		itemImageDTO2.setImageType(2);
		//itemImageDTO2.setImgUrl("http://www.163.com/a.jpeg");
		List<ItemImageDTO> itemImageDTOList = new ArrayList<ItemImageDTO>();
		itemImageDTOList.add(itemImageDTO);
		itemImageDTOList.add(itemImageDTO2);

		itemDTO.setItemImageDTOList(itemImageDTOList);

		request.setParam("itemDTO", itemDTO);
		request.setParam("updateDetail", true);

		request.setCommand(ActionEnum.UPDATE_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
		
	}

	/**
	 * 正常更新
	 */
	public void test001() {
		Request request = new BaseRequest();
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setId(85L);
		itemDTO.setSellerId(1L);
		itemDTO.setItemName("大头");
		request.setParam("itemDTO", itemDTO);
		request.setParam("updateDetail", true);
		request.setCommand(ActionEnum.UPDATE_ITEM.getActionName());
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
		request.setCommand(ActionEnum.UPDATE_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * ID为空
	 */
	public void test003() {
		Request request = new BaseRequest();
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setSellerId(2L);
		itemDTO.setItemName("大头");
		request.setParam("itemDTO", itemDTO);
		request.setCommand(ActionEnum.UPDATE_ITEM.getActionName());
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
	public void test004() {
		Request request = new BaseRequest();
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setId(13L);
		itemDTO.setItemName("大头");
		request.setParam("itemDTO", itemDTO);
		request.setCommand(ActionEnum.UPDATE_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * ID在数据库中不存在
	 */

	public void test005() {
		Request request = new BaseRequest();
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setId(99999L);
		itemDTO.setSellerId(2L);
		itemDTO.setItemName("大头");
		request.setParam("itemDTO", itemDTO);
		request.setCommand(ActionEnum.UPDATE_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

//	@Test
	public void test006() {
		Request request = new BaseRequest();
		request.setParam("sellerId", 91L);
		request.setParam("groupId", 1L);
		request.setParam("itemId", 1L);
		request.setCommand(ActionEnum.ADD_ITEM_GROUP_ACTION.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	@Test
	public void test007() {
		Request request = new BaseRequest();
		request.setParam("sellerId", 91L);
		request.setParam("itemId", 1L);
		request.setCommand(ActionEnum.REMOVE_ITEM_GROUP_ACTION.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

    /**
     *
     */
	@Test
	public void testBug433() throws ParseException {
        Request request = new BaseRequest();
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(91L);
        itemDTO.setSellerId(91L);
        itemDTO.setItemName("大头");
        request.setParam("itemDTO", itemDTO);
        request.setParam("updateDetail", true);
        request.setParam("appKey","af7433eda4883d7139e2934dd8d035f1");

        final LimitEntity limitEntity = new LimitEntity();
        limitEntity.setBeginTime(DateUtil.toDayDate("2015-09-11"));
        limitEntity.setEndTime(DateUtil.toDayDate("2015-09-13"));
        limitEntity.setLimitCount(20);

        itemDTO.setBuyLimit(new ArrayList<LimitEntity>() {{
			add(limitEntity);
		}});

        request.setCommand(ActionEnum.UPDATE_ITEM.getActionName());
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
	}


	@Test
	public void testxx() {

		Request request = RequestFactory.newRequest(AppKeyEnum.HAIYUN, ActionEnum.GET_ITEM);


		request.setParam("id", 28954L);
		request.setParam("supplierId", 1841254L);
		request.setParam("needDetail", true);

		Response<ItemDTO> response = itemService.execute(request);

		Request request1 = RequestFactory.newRequest(AppKeyEnum.HAIYUN, ActionEnum.UPDATE_ITEM);
		request1.setParam("itemDTO", response.getModule());
		request1.setParam("updateDetail", true);

		Response response1 = itemService.execute(request1);


		System.currentTimeMillis();
	}
	@Test
	public void testUpdateStatus() {
		/*Request request = RequestFactory.newRequest(AppKeyEnum.HAIYUN, ActionEnum.GET_ITEM);
		request.setParam("itemId",214L);
		Response response = itemService.execute(request);*/
		try {
			Long result = itemManager.updateIssueStatus(214L,1);
			System.out.println(result);
		} catch (ItemException e) {
			e.printStackTrace();
		}
	}
}
