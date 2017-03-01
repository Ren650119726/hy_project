package skuproperty;

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
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyDTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddSkuPropertyTest {
	@Resource
	private ItemService itemService;

	@Test
	/**
	 * 正常插入
	 */
	public void test001() {
		Request request = new BaseRequest();
		SkuPropertyDTO skuPropertyDTO = new SkuPropertyDTO();
		skuPropertyDTO.setName("name");
		skuPropertyDTO.setCode("code");
		skuPropertyDTO.setValue("value");
		skuPropertyDTO.setBizCode("bizCode");
		skuPropertyDTO.setBizMark(11L);
		skuPropertyDTO.setSkuId(11L);
		skuPropertyDTO.setItemId(14L);
		skuPropertyDTO.setSkuPropertyTmplId(44L);
		skuPropertyDTO.setPropertyValueId(55L);
		skuPropertyDTO.setSellerId(33L);
		skuPropertyDTO.setImgUrl("imgUrl");
		skuPropertyDTO.setValueType(1);
		skuPropertyDTO.setSort(4);

//		SkuPropertyDTO skuPropertyDTO2 = new SkuPropertyDTO();
//		skuPropertyDTO2.setName("name2");
//		skuPropertyDTO2.setValue("value2");
//		skuPropertyDTO2.setBizCode("bizCode2");
//		skuPropertyDTO2.setValueType(2);
//		skuPropertyDTO2.setSort(5);

		List<SkuPropertyDTO> skuPropertyDTOList = new ArrayList<SkuPropertyDTO>();
		skuPropertyDTOList.add(skuPropertyDTO);
//		skuPropertyDTOList.add(skuPropertyDTO2);
		request.setParam("skuPropertyDTOList", skuPropertyDTOList);
		request.setParam("skuId", 3L);
		request.setParam("sellerId", 1L);
		request.setParam("itemId", 11L);
		request.setCommand(ActionEnum.ADD_SKU_PROPERTY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	@Test
	/**
	 * skuPropertyDTOList 为空
	 */
	public void test002() {
		Request request = new BaseRequest();
		request.setParam("skuId", 3L);
		request.setParam("sellerId", 1L);
		request.setCommand(ActionEnum.ADD_SKU_PROPERTY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	@Test
	/**
	 * skuId为空
	 */
	public void test003() {
		Request request = new BaseRequest();
		List<SkuPropertyDTO> skuPropertyDTOList = new ArrayList<SkuPropertyDTO>();
		request.setParam("skuPropertyDTOList", skuPropertyDTOList);
		request.setCommand(ActionEnum.ADD_SKU_PROPERTY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	@Test
	/**
	 * sellerId为空
	 */
	public void test004() {
		Request request = new BaseRequest();
		List<SkuPropertyDTO> skuPropertyDTOList = new ArrayList<SkuPropertyDTO>();
		request.setParam("skuPropertyDTOList", skuPropertyDTOList);
		request.setParam("skuId", 3L);
		request.setCommand(ActionEnum.ADD_SKU_PROPERTY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	@Test
	/**
	 * skuPropertyDTOList size为0
	 */
	public void test005() {
		Request request = new BaseRequest();

		List<SkuPropertyDTO> skuPropertyDTOList = new ArrayList<SkuPropertyDTO>();
		request.setParam("skuPropertyDTOList", skuPropertyDTOList);
		request.setParam("skuId", 3L);
		request.setParam("sellerId", 1L);
		request.setCommand(ActionEnum.ADD_SKU_PROPERTY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	@Test
	/**
	 * 找不到ItemSku
	 */
	public void test006() {
		Request request = new BaseRequest();
		SkuPropertyDTO skuPropertyDTO = new SkuPropertyDTO();
		skuPropertyDTO.setName("颜色");
		skuPropertyDTO.setValue("蓝蓝色3");
		skuPropertyDTO.setValueType(1);
		skuPropertyDTO.setSort(4);

		List<SkuPropertyDTO> skuPropertyDTOList = new ArrayList<SkuPropertyDTO>();
		skuPropertyDTOList.add(skuPropertyDTO);
		request.setParam("skuPropertyDTOList", skuPropertyDTOList);
		request.setParam("skuId", 99999L);
		request.setParam("sellerId", 1L);
		request.setCommand(ActionEnum.ADD_SKU_PROPERTY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	@Test
	/**
	 * keyname为空
	 */
	public void test007() {
		Request request = new BaseRequest();
		SkuPropertyDTO skuPropertyDTO = new SkuPropertyDTO();
		skuPropertyDTO.setValue("蓝蓝色3");
		skuPropertyDTO.setValueType(1);
		skuPropertyDTO.setSort(4);

		SkuPropertyDTO skuPropertyDTO2 = new SkuPropertyDTO();
		skuPropertyDTO2.setValue("红红色3");
		skuPropertyDTO2.setValueType(1);
		skuPropertyDTO2.setSort(5);

		List<SkuPropertyDTO> skuPropertyDTOList = new ArrayList<SkuPropertyDTO>();
		skuPropertyDTOList.add(skuPropertyDTO);
		skuPropertyDTOList.add(skuPropertyDTO2);
		request.setParam("skuPropertyDTOList", skuPropertyDTOList);
		request.setParam("skuId", 3L);
		request.setParam("sellerId", 1L);
		request.setCommand(ActionEnum.ADD_SKU_PROPERTY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	@Test
	/**
	 * value为空
	 */
	public void test008() {
		Request request = new BaseRequest();
		SkuPropertyDTO skuPropertyDTO = new SkuPropertyDTO();
		skuPropertyDTO.setName("颜色");
		skuPropertyDTO.setValueType(1);
		skuPropertyDTO.setSort(4);

		SkuPropertyDTO skuPropertyDTO2 = new SkuPropertyDTO();
		skuPropertyDTO2.setName("颜色");
		skuPropertyDTO2.setValueType(1);
		skuPropertyDTO2.setSort(5);

		List<SkuPropertyDTO> skuPropertyDTOList = new ArrayList<SkuPropertyDTO>();
		skuPropertyDTOList.add(skuPropertyDTO);
		skuPropertyDTOList.add(skuPropertyDTO2);
		request.setParam("skuPropertyDTOList", skuPropertyDTOList);
		request.setParam("skuId", 3L);
		request.setParam("sellerId", 1L);
		request.setCommand(ActionEnum.ADD_SKU_PROPERTY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	@Test
	/**
	 * sort字段为空
	 */
	public void test009() {
		Request request = new BaseRequest();
		SkuPropertyDTO skuPropertyDTO = new SkuPropertyDTO();
		skuPropertyDTO.setName("颜色");
		skuPropertyDTO.setValue("蓝蓝色3");
		skuPropertyDTO.setValueType(1);

		SkuPropertyDTO skuPropertyDTO2 = new SkuPropertyDTO();
		skuPropertyDTO2.setName("颜色");
		skuPropertyDTO2.setValue("红红色3");
		skuPropertyDTO2.setValueType(1);

		List<SkuPropertyDTO> skuPropertyDTOList = new ArrayList<SkuPropertyDTO>();
		skuPropertyDTOList.add(skuPropertyDTO);
		skuPropertyDTOList.add(skuPropertyDTO2);
		request.setParam("skuPropertyDTOList", skuPropertyDTOList);
		request.setParam("skuId", 3L);
		request.setParam("sellerId", 1L);
		request.setCommand(ActionEnum.ADD_SKU_PROPERTY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
}
