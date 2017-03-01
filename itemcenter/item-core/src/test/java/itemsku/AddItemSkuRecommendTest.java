package itemsku;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuRecommendationDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuRecommendationQTO;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class AddItemSkuRecommendTest {

    @Resource
    private ItemService itemService;

    private final static String TEST_BIZCODE = "mockuai_demo";

    private final static String APP_KEY = "af7433eda4883d7139e2934dd8d035f1";

    private final static Long SELLER_ID = 38699L;

    @Test
    /**
     * 正常插入
     */
    public void test001() {
        Request request = new BaseRequest();

        ItemSkuRecommendationDTO itemSkuRecommendationDTO = new ItemSkuRecommendationDTO();

        itemSkuRecommendationDTO.setSellerId(SELLER_ID);

        itemSkuRecommendationDTO.setSkuCode("款式");


        request.setParam("appKey", APP_KEY);
        request.setParam("itemSkuRecommendationDTO",itemSkuRecommendationDTO);


        request.setCommand(ActionEnum.ADD_ITEM_SKU_RECOMMENDATION.getActionName());
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
     * 正常插入
     */
    public void test002() {
        Request request = new BaseRequest();

        ItemSkuRecommendationQTO itemSkuRecommendationQTO = new ItemSkuRecommendationQTO();

        itemSkuRecommendationQTO.setSellerId(SELLER_ID);

        itemSkuRecommendationQTO.setBizCode(TEST_BIZCODE);

        itemSkuRecommendationQTO.setNeedPaging(true);
        itemSkuRecommendationQTO.setPageSize(2);
        itemSkuRecommendationQTO.setCurrentPage(1);


        request.setParam("appKey", APP_KEY);
        request.setParam("itemSkuRecommendationQTO",itemSkuRecommendationQTO);


        request.setCommand(ActionEnum.QUERY_ITEM_SKU_RECOMMENDATION.getActionName());
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
     * 正常插入
     */
    public void test003() {
        Request request = new BaseRequest();


        request.setParam("appKey", APP_KEY);
        request.setParam("sellerId",SELLER_ID);
        request.setParam("id",1);
        request.setCommand(ActionEnum.DELETE_ITEM_SKU_RECOMMENDATION.getActionName());
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

}
