package item;

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
import com.mockuai.itemcenter.common.domain.dto.CompositeItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemImageDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemPropertyDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyDTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UpdateCompositeItemTest {
	@Resource
	private ItemService itemService;
	
	
	@Test
	public void test(){
		
		Request request = new BaseRequest();
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setId(82L);
		itemDTO.setItemName("婴儿毛巾444修改");
		itemDTO.setSellerId(1L);
		//itemDTO.setItemBriefName("商品简称修改");
		itemDTO.setItemBrandId(1L);
		itemDTO.setItemDesc("商品简介修改");
		itemDTO.setItemType(1);
		itemDTO.setIconUrl("http://www.163.com/e.jpeg");
		itemDTO.setCategoryId(1L);
		
		ItemPropertyDTO itemPropertyDTO2 = new ItemPropertyDTO();
		itemPropertyDTO2.setBizMark(1L);
		itemPropertyDTO2.setName("发货国家修改");
		itemPropertyDTO2.setValue("中国修改");
		itemPropertyDTO2.setPropertyValueId(100002L);
		itemPropertyDTO2.setItemPropertyTmplId(21L);
		itemPropertyDTO2.setSort(1);
		itemPropertyDTO2.setPropertyValueId(102L);
		
		List<ItemPropertyDTO> itemPropertys = new ArrayList<ItemPropertyDTO>();
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
		
		
		CompositeItemDTO c = new CompositeItemDTO();
		c.setSubSkuId(1L);
		c.setNum(2);
		c.setBizCode("abc");
		List<CompositeItemDTO> list =new ArrayList<CompositeItemDTO>();
		list.add(c);

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

//		ItemSkuDTO itemSkuDTO2 = new ItemSkuDTO();
//		itemSkuDTO2.setMaterialCode("bb");
//		itemSkuDTO2.setBarCode("b");
//		itemSkuDTO2.setMarketPrice(100L);
//		itemSkuDTO2.setPromotionPrice(90L);
//		itemSkuDTO2.setWirelessPrice(80L);
//		itemSkuDTO2.setStockNum(1000L);
//		itemSkuDTO2.setSoldNum(1L);
//		
//		
//
//		SkuPropertyDTO skuPropertyDTO3 = new SkuPropertyDTO();
//		skuPropertyDTO3.setName("尺寸修改");
//		skuPropertyDTO3.setSort(2);
//		skuPropertyDTO3.setValue("17码修改");
//		skuPropertyDTO3.setValueType(1);
//
//		SkuPropertyDTO skuPropertyDTO4 = new SkuPropertyDTO();
//		skuPropertyDTO4.setName("颜色修改");
//		skuPropertyDTO4.setSort(1);
//		skuPropertyDTO4.setPropertyValueId(200002L);
//		skuPropertyDTO4.setValue("蓝色修改");
//		skuPropertyDTO4.setValueType(1);
//
//		List<SkuPropertyDTO> skuPropertyDTOList2 = new ArrayList<SkuPropertyDTO>();
//		skuPropertyDTOList2.add(skuPropertyDTO3);
//		skuPropertyDTOList2.add(skuPropertyDTO4);
//		itemSkuDTO2.setSkuPropertyDTOList(skuPropertyDTOList2);

		retItemSkuDTOList.add(itemSkuDTO);
//		retItemSkuDTOList.add(itemSkuDTO2);

		itemDTO.setItemSkuDTOList(retItemSkuDTOList);

		ItemImageDTO itemImageDTO = new ItemImageDTO();
		itemImageDTO.setImageName("测试狗11");
		itemImageDTO.setImageType(2);
		//itemImageDTO.setImgUrl("http://www.163.com/a.jpeg");

		ItemImageDTO itemImageDTO2 = new ItemImageDTO();
		itemImageDTO2.setImageName("测试狗22");
		itemImageDTO2.setImageType(2);
		///itemImageDTO2.setImgUrl("http://www.163.com/a.jpeg");
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
	
}
