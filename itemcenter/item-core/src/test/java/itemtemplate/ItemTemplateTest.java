package itemtemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemImageDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemTemplateDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemTemplatePropertyDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemTemplateQTO;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class ItemTemplateTest {
	@Resource
	private ItemService itemService;
	
	/**
	 * 正常插入
	 */
	@Test
	public void test001() {
		Request request = new BaseRequest();
		ItemTemplateDTO itemDTO = new ItemTemplateDTO();
		itemDTO.setTemplateName("abc模版");
		itemDTO.setItemName("abc测试商品");
		itemDTO.setSellerId(100L);
		itemDTO.setItemBrandId(2L);
		itemDTO.setItemDesc("商品简介");
		itemDTO.setItemType(99);
		itemDTO.setIconUrl("iconUrl");
		itemDTO.setCategoryId(99L);
		itemDTO.setMarketPrice(99L);
		itemDTO.setPromotionPrice(99L);
		itemDTO.setMarketPrice(99L);
		itemDTO.setSaleBegin(new Date());
		itemDTO.setSaleEnd(new Date());
		itemDTO.setSaleMaxNum(99);
		itemDTO.setSaleMaxNum(99);
		itemDTO.setBizCode("bizCode");
		itemDTO.setItemDesc("itemDesc");
		itemDTO.setCornerIconId(99L);
		itemDTO.setDeliveryType(99);
		itemDTO.setDeleteMark(2);
		
		ItemTemplatePropertyDTO itemPropertyDTO = new ItemTemplatePropertyDTO();
		itemPropertyDTO.setBizMark(1L);
		itemPropertyDTO.setName("计量单位");
		itemPropertyDTO.setValue("瓶");
		itemPropertyDTO.setPropertyValueId(100001L);
		itemPropertyDTO.setItemPropertyTmplId(20L);
		itemPropertyDTO.setSort(1);
		itemPropertyDTO.setPropertyValueId(101L);
		
		ItemTemplatePropertyDTO itemPropertyDTO2 = new ItemTemplatePropertyDTO();
		itemPropertyDTO2.setBizMark(1L);
		itemPropertyDTO2.setName("发货国家");
		itemPropertyDTO2.setValue("中国");
		itemPropertyDTO2.setPropertyValueId(100002L);
		itemPropertyDTO2.setItemPropertyTmplId(21L);
		itemPropertyDTO2.setSort(1);
		itemPropertyDTO2.setPropertyValueId(102L);
		
		List<ItemTemplatePropertyDTO> itemPropertys = new ArrayList<ItemTemplatePropertyDTO>();
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
		skuPropertyDTO1.setValueType(1);

		SkuPropertyDTO skuPropertyDTO2 = new SkuPropertyDTO();
		skuPropertyDTO2.setName("颜色");
		skuPropertyDTO2.setSort(2);
		skuPropertyDTO2.setValue("红色");
		skuPropertyDTO1.setPropertyValueId(200002L);
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
		skuPropertyDTO3.setValueType(1);

		SkuPropertyDTO skuPropertyDTO4 = new SkuPropertyDTO();
		skuPropertyDTO4.setName("颜色");
		skuPropertyDTO4.setSort(1);
		skuPropertyDTO4.setPropertyValueId(200002L);
		skuPropertyDTO4.setValue("蓝色");
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

		ItemImageDTO itemImageDTO2 = new ItemImageDTO();
		itemImageDTO2.setImageName("测试狗22");
		itemImageDTO2.setImageType(2);
		List<ItemImageDTO> itemImageDTOList = new ArrayList<ItemImageDTO>();
		itemImageDTOList.add(itemImageDTO);
		itemImageDTOList.add(itemImageDTO2);

		itemDTO.setItemImageDTOList(itemImageDTOList);

		request.setParam("itemTemplateDTO", itemDTO);
		request.setCommand(ActionEnum.ADD_ITEM_TEMPLATE.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ErrorCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	public void getTest(){
		Request request = new BaseRequest();
		request.setParam("itemTemplateId", 191L);
		request.setParam("supplierId", 100L);
		request.setCommand(ActionEnum.GET_ITEM_TEMPLATE.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ErrorCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		ItemTemplateDTO item = (ItemTemplateDTO)response.getModule();
		System.out.println(item.getItemPropertyList());
		System.out.println("**************************************");
	}
	
	public void deleteTest(){
		Request request = new BaseRequest();
		request.setParam("itemTemplateId", 192L);
		request.setParam("supplierId", 100L);
		request.setCommand(ActionEnum.DELETE_ITEM_TEMPLATE.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ErrorCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	public void queryTest(){
		Request request = new BaseRequest();
		ItemTemplateQTO itemTemplateQTO= new ItemTemplateQTO();
//		itemTemplateQTO.setSupplierId(2L);
		itemTemplateQTO.setTemplateName("测试");
//		itemTemplateQTO.setCategoryId(1L);
		itemTemplateQTO.setNeedPaging(true);
		itemTemplateQTO.setPageSize(1);
		itemTemplateQTO.setCurrentPage(1);
		request.setParam("itemTemplateQTO", itemTemplateQTO);
		request.setCommand(ActionEnum.QUERY_ITEM_TEMPLATE.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ErrorCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	public void updateTest(){
		Request request = new BaseRequest();
		ItemTemplateDTO itemDTO = new ItemTemplateDTO();
		itemDTO.setId(192L);
		itemDTO.setItemName("新的测试模版");
		itemDTO.setSellerId(100L);
		itemDTO.setItemBrandId(1L);
		itemDTO.setItemDesc("商品详情");
		itemDTO.setItemType(1);
		itemDTO.setIconUrl("iconUrl2");
		itemDTO.setCategoryId(99L);
		itemDTO.setItemType(111111);
		itemDTO.setCornerIconId(111111L);
		itemDTO.setBizCode("bizCode2");
		itemDTO.setItemStatus(11);
		
		ItemTemplatePropertyDTO itemPropertyDTO = new ItemTemplatePropertyDTO();
		itemPropertyDTO.setBizMark(122222L);
		itemPropertyDTO.setName("计量单位22222");
		itemPropertyDTO.setValue("瓶2222");
		itemPropertyDTO.setPropertyValueId(10000122222L);
		itemPropertyDTO.setItemPropertyTmplId(20222222L);
		itemPropertyDTO.setSort(1);
		itemPropertyDTO.setPropertyValueId(101L);
		
		ItemTemplatePropertyDTO itemPropertyDTO2 = new ItemTemplatePropertyDTO();
		itemPropertyDTO2.setBizMark(12222L);
		itemPropertyDTO2.setName("发货国家修改22222");
		itemPropertyDTO2.setValue("中国修改2222");
		itemPropertyDTO2.setPropertyValueId(100002L);
		itemPropertyDTO2.setItemPropertyTmplId(2122222L);
		itemPropertyDTO2.setSort(12222);
		itemPropertyDTO2.setPropertyValueId(10222222L);
		
		List<ItemTemplatePropertyDTO> itemPropertys = new ArrayList<ItemTemplatePropertyDTO>();
		itemPropertys.add(itemPropertyDTO);
		itemPropertys.add(itemPropertyDTO2);
		itemDTO.setItemPropertyList(itemPropertys);
		
		List<ItemSkuDTO> retItemSkuDTOList = new ArrayList<ItemSkuDTO>();
		ItemSkuDTO itemSkuDTO = new ItemSkuDTO();
		itemSkuDTO.setBarCode("a22222");
		itemSkuDTO.setMarketPrice(10022222L);
		itemSkuDTO.setPromotionPrice(902222L);
		itemSkuDTO.setWirelessPrice(802222L);
		itemSkuDTO.setStockNum(10002222L);
		itemSkuDTO.setSoldNum(1222L);

		SkuPropertyDTO skuPropertyDTO1 = new SkuPropertyDTO();
		skuPropertyDTO1.setName("尺寸2222");
		skuPropertyDTO1.setSort(12222);
		skuPropertyDTO1.setValue("16码222");
		skuPropertyDTO1.setPropertyValueId(200001L);
		skuPropertyDTO1.setValueType(1222);

		SkuPropertyDTO skuPropertyDTO2 = new SkuPropertyDTO();
		skuPropertyDTO2.setName("颜色222");
		skuPropertyDTO2.setSort(222);
		skuPropertyDTO2.setValue("红色");
		skuPropertyDTO1.setPropertyValueId(200002L);
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
		skuPropertyDTO3.setValueType(1);

		SkuPropertyDTO skuPropertyDTO4 = new SkuPropertyDTO();
		skuPropertyDTO4.setName("颜色修改");
		skuPropertyDTO4.setSort(1);
		skuPropertyDTO4.setPropertyValueId(200002L);
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

		ItemImageDTO itemImageDTO2 = new ItemImageDTO();
		itemImageDTO2.setImageName("测试狗22");
		itemImageDTO2.setImageType(2);
		List<ItemImageDTO> itemImageDTOList = new ArrayList<ItemImageDTO>();
		itemImageDTOList.add(itemImageDTO);
		itemImageDTOList.add(itemImageDTO2);

		itemDTO.setItemImageDTOList(itemImageDTOList);

		request.setParam("itemTemplateDTO", itemDTO);
		request.setParam("updateDetail", true);

		request.setCommand(ActionEnum.UPDATE_ITEM_TEMPLATE.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ErrorCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
}
