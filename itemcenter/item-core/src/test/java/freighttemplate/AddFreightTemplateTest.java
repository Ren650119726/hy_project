package freighttemplate;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.FreightAreaTemplateDTO;
import com.mockuai.itemcenter.common.domain.dto.FreightTemplateAreaInfoDTO;
import com.mockuai.itemcenter.common.domain.dto.FreightTemplateDTO;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by yindingyu on 15/9/24.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddFreightTemplateTest {


    @Resource
    private ItemService itemService;

    private final  static String TEST_BIZCODE= "mockuai_demo";

    /**
     * 添加只有通用模板的运费模板
     */

    @Test
    public void test01(){

        Request request = new BaseRequest();
        request.setParam("appKey", "af7433eda4883d7139e2934dd8d035f1");


        FreightTemplateDTO freightTemplateDTO = new FreightTemplateDTO();
        freightTemplateDTO.setSellerId(996L);
        freightTemplateDTO.setFree(1);
        freightTemplateDTO.setBasicCharge(-1000L);
        freightTemplateDTO.setBasicCount(2);
        freightTemplateDTO.setExtraCharge(20L);
        freightTemplateDTO.setExtraCount(100);
        freightTemplateDTO.setLogisticsWay("宇宙快递");
        freightTemplateDTO.setName("运费模板，任性");
        freightTemplateDTO.setPricingMethod(8);

        freightTemplateDTO.setBizCode(TEST_BIZCODE);

        request.setCommand(ActionEnum.ADD_FREIGHT_TEMPLATE_ACTION.getActionName());
        //request.setCommand(ActionEnum.QUERY_FREIGHT_TEMPLATE_ACTION.getActionName());
        request.setParam("freightTemplateDTO", freightTemplateDTO);

        Response response = itemService.execute(request);

        int code = response.getCode();

        assertThat(code,is(10000));

        Boolean result = (Boolean)response.getModule();


    }

    @Test
    public void test02(){

        Request request = new BaseRequest();
        request.setParam("appKey", "af7433eda4883d7139e2934dd8d035f1");

        FreightTemplateDTO freightTemplateDTO = new FreightTemplateDTO();
        freightTemplateDTO.setSellerId(38699L);
        freightTemplateDTO.setFree(1);
        freightTemplateDTO.setBasicCharge(1000L);
        freightTemplateDTO.setBasicCount(2);
        freightTemplateDTO.setExtraCharge(20L);
        freightTemplateDTO.setExtraCount(400);
        freightTemplateDTO.setLogisticsWay("宇宙快递");
        freightTemplateDTO.setName("运费模板，又任性xx");
        freightTemplateDTO.setPricingMethod(0);
        freightTemplateDTO.setBizCode(TEST_BIZCODE);

        //添加十个地区模板

        List<FreightAreaTemplateDTO> templateDTOs = new ArrayList<FreightAreaTemplateDTO>();
        for(int i=0;i<1;i++){


            List<FreightTemplateAreaInfoDTO> areaInfoDTOs = new ArrayList<FreightTemplateAreaInfoDTO>();

            FreightTemplateAreaInfoDTO areaInfoDTO = new FreightTemplateAreaInfoDTO();
            areaInfoDTO.setLevel(1);
            areaInfoDTO.setCode("只是个测试名");

            areaInfoDTOs.add(areaInfoDTO);

            FreightAreaTemplateDTO dto = new FreightAreaTemplateDTO();
            dto.setLogisticsWay("银河快递"+ i +"号");
            dto.setBasicCount(3);
            dto.setBasicCharge(2000L);
            dto.setExtraCount(1);
            dto.setExtraCharge(100L);
            dto.setAreas(areaInfoDTOs);
            dto.setSellerId(38699L);

            templateDTOs.add(dto);
        }

        freightTemplateDTO.setFreightAreaTemplateList(templateDTOs);

        request.setCommand(ActionEnum.ADD_FREIGHT_TEMPLATE_ACTION.getActionName());
        request.setParam("freightTemplateDTO", freightTemplateDTO);

        Response response = itemService.execute(request);

        int code = response.getCode();

        Long result = (Long) response.getModule();

        assertThat(code, is(10000));

    }

}
