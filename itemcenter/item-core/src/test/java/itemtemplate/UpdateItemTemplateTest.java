package itemtemplate;

import java.util.ArrayList;
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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UpdateItemTemplateTest {
	
	@Resource
	private ItemService itemService;
	
	@Test
	public void getTest(){
		Request request = new BaseRequest();
		ItemTemplateDTO itemDTO = new ItemTemplateDTO();
		itemDTO.setId(211L);
		itemDTO.setItemName("新的测试模版");
        itemDTO.setTemplateName("xx");
        itemDTO.setItemBrief("fffffx");
		itemDTO.setSellerId(38699L);
		itemDTO.setItemBrandId(1L);
		itemDTO.setItemDesc("商品详情");
		itemDTO.setItemType(1);
		itemDTO.setIconUrl("iconUrl2");
		itemDTO.setCategoryId(99L);
		itemDTO.setItemBrandId(111111L);
		itemDTO.setCornerIconId(111111L);
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
		request.setParam("appKey","af7433eda4883d7139e2934dd8d035f1");

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
