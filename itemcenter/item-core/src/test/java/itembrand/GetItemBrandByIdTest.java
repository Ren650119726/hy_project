package itembrand;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.SellerBrandDTO;
import com.mockuai.itemcenter.core.api.impl.ItemServiceImpl;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by huangsiaian on 2016/8/31.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class GetItemBrandByIdTest {
    @Resource
    ItemService itemService;
    @Test
    public void test00001(){
        Request request = new BaseRequest();
        request.setParam("brandId",58L);
        request.setParam("appKey","6562b5ddf0aed2aad8fe471ce2a2c8a0");
        request.setCommand(ActionEnum.GET_BRAND.getActionName());
        Response response = itemService.execute(request);
        SellerBrandDTO sellerBrandDTO = (SellerBrandDTO) response.getModule();
        System.out.println("sellerBrandDTO"+sellerBrandDTO);
        System.out.println("getCode"+response.getCode());
        System.out.println("getModule"+response.getModule().toString());
        System.out.println("getMessage"+response.getMessage());
    }

}
