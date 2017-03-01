package skupropertytmpl;

import javax.annotation.Resource;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.api.*;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyValueDTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyTmplQTO;
import com.mockuai.itemcenter.core.domain.SkuPropertyValueDO;
import com.mockuai.itemcenter.core.service.ItemRequest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyTmplDTO;
import util.AppKeyEnum;
import util.RequestFactory;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UpdateSkuPropertyTmplTest {

	@Resource
	private ItemService itemService;

    private final  static String BIZ_CODE= "mockuai_demo";

    private final static String APP_KEY = "af7433eda4883d7139e2934dd8d035f1";

    private final static Long SELLER_ID = 38699L;


    /**
     * 完全不修改的测试
     */
    @Test
	public void test001(){

        Request request = RequestFactory.newRequest(AppKeyEnum.HAODADA,ActionEnum.QUERY_SKU_PROPERTY_TMPL);

        SkuPropertyTmplQTO skuPropertyTmplQTO = new SkuPropertyTmplQTO();
        //skuPropertyTmplQTO.setCategoryId(1150L);
        skuPropertyTmplQTO.setId(10420L);

        request.setParam("skuPropertyTmplQTO", skuPropertyTmplQTO);
        request.setParam("needPropertyValue",true);


        Response<List<SkuPropertyTmplDTO>> response = itemService.execute(request);

        List<SkuPropertyTmplDTO>  skuPropertyTmplDTOList = response.getModule();

        SkuPropertyTmplDTO skuPropertyTmplDTO = skuPropertyTmplDTOList.get(0);


        Request request1 = RequestFactory.newRequest(AppKeyEnum.HAODADA,ActionEnum.UPDATE_SKU_PROPERTY_TMPL);


        request1.setParam("skuPropertyTmplDTO",skuPropertyTmplDTO);


        Response<Long> response1 = itemService.execute(request1);


        Long result = response1.getModule();

    }

    /**
     * 删除所有属性的测试
     */
    @Test
    public void test002() {

        Request request = RequestFactory.newRequest(AppKeyEnum.HAODADA, ActionEnum.QUERY_SKU_PROPERTY_TMPL);

        SkuPropertyTmplQTO skuPropertyTmplQTO = new SkuPropertyTmplQTO();
        //skuPropertyTmplQTO.setCategoryId(1150L);
        skuPropertyTmplQTO.setId(10420L);

        request.setParam("skuPropertyTmplQTO", skuPropertyTmplQTO);
        request.setParam("needPropertyValue", true);


        Response<List<SkuPropertyTmplDTO>> response = itemService.execute(request);

        List<SkuPropertyTmplDTO> skuPropertyTmplDTOList = response.getModule();

        SkuPropertyTmplDTO skuPropertyTmplDTO = skuPropertyTmplDTOList.get(0);

        skuPropertyTmplDTO.setPropertyValues(null);


        Request request1 = RequestFactory.newRequest(AppKeyEnum.HAODADA, ActionEnum.UPDATE_SKU_PROPERTY_TMPL);


        request1.setParam("skuPropertyTmplDTO", skuPropertyTmplDTO);


        Response<Long> response1 = itemService.execute(request1);


        Long result = response1.getModule();

    }


    /**
     * 添加属性的测试
     */
    @Test
    public void test003() {

        Request request = RequestFactory.newRequest(AppKeyEnum.HAODADA, ActionEnum.QUERY_SKU_PROPERTY_TMPL);

        SkuPropertyTmplQTO skuPropertyTmplQTO = new SkuPropertyTmplQTO();
        //skuPropertyTmplQTO.setCategoryId(1150L);
        skuPropertyTmplQTO.setId(10420L);

        request.setParam("skuPropertyTmplQTO", skuPropertyTmplQTO);
        request.setParam("needPropertyValue", true);


        Response<List<SkuPropertyTmplDTO>> response = itemService.execute(request);

        List<SkuPropertyTmplDTO> skuPropertyTmplDTOList = response.getModule();

        SkuPropertyTmplDTO skuPropertyTmplDTO = skuPropertyTmplDTOList.get(0);

        List<SkuPropertyValueDTO> skuPropertyValueDTOList = Lists.newArrayList();

        SkuPropertyValueDTO skuPropertyValueDTO = new SkuPropertyValueDTO();


        skuPropertyValueDTO.setName("sss");
        skuPropertyValueDTO.setValue("xxxx");
        skuPropertyValueDTO.setSkuPropertyTmplId(skuPropertyTmplDTO.getId());

        skuPropertyValueDTOList.add(skuPropertyValueDTO);


        skuPropertyTmplDTO.setPropertyValues(skuPropertyValueDTOList);


        Request request1 = RequestFactory.newRequest(AppKeyEnum.HAODADA, ActionEnum.UPDATE_SKU_PROPERTY_TMPL);


        request1.setParam("skuPropertyTmplDTO", skuPropertyTmplDTO);


        Response<Long> response1 = itemService.execute(request1);


        Long result = response1.getModule();

    }

    /**
     * 修改值的测试
     */
    @Test
    public void test004() {

        Request request = RequestFactory.newRequest(AppKeyEnum.HAODADA, ActionEnum.QUERY_SKU_PROPERTY_TMPL);

        SkuPropertyTmplQTO skuPropertyTmplQTO = new SkuPropertyTmplQTO();
        //skuPropertyTmplQTO.setCategoryId(1150L);
        skuPropertyTmplQTO.setId(10421L);

        request.setParam("skuPropertyTmplQTO", skuPropertyTmplQTO);
        request.setParam("needPropertyValue", true);


        Response<List<SkuPropertyTmplDTO>> response = itemService.execute(request);

        List<SkuPropertyTmplDTO> skuPropertyTmplDTOList = response.getModule();


        SkuPropertyTmplDTO skuPropertyTmplDTO = skuPropertyTmplDTOList.get(0);

        skuPropertyTmplDTO.setName("尺寸xxx");


        SkuPropertyValueDTO skuPropertyValueDTO = skuPropertyTmplDTO.getPropertyValues().get(0);


        skuPropertyValueDTO.setName("星空黑");

        Request request1 = RequestFactory.newRequest(AppKeyEnum.HAODADA, ActionEnum.UPDATE_SKU_PROPERTY_TMPL);


        request1.setParam("skuPropertyTmplDTO", skuPropertyTmplDTO);


        Response<Long> response1 = itemService.execute(request1);


        Long result = response1.getModule();

    }
}
