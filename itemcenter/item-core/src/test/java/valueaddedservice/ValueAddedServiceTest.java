package valueaddedservice;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ValueAddedServiceDTO;
import com.mockuai.itemcenter.common.domain.dto.ValueAddedServiceTypeDTO;
import com.mockuai.itemcenter.common.domain.qto.ValueAddedServiceQTO;
import com.mockuai.itemcenter.common.domain.qto.ValueAddedServiceTypeQTO;
import com.mockuai.itemcenter.core.dao.ValueAddedServiceDAO;
import com.mockuai.itemcenter.core.domain.ValueAddedServiceDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ValueAddedServiceManager;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by yindingyu on 15/9/24.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class ValueAddedServiceTest {


    @Resource
    private ItemService itemService;


    @Resource
    private ValueAddedServiceManager valueAddedServiceManager;

    @Resource
    private ValueAddedServiceDAO valueAddedServiceDAO;


    private final  static String BIZ_CODE= "mockuai_demo";

    private final static String APP_KEY = "af7433eda4883d7139e2934dd8d035f1";

    private final static Long SELLER_ID = 38699L;

    /**
     * 添加增值服务
     */

    @Test
    public void test01(){

        ValueAddedServiceQTO valueAddedServiceQTO = new ValueAddedServiceQTO();
        valueAddedServiceQTO.setTypeId(34L);
        valueAddedServiceQTO.setSellerId(38699L);
        valueAddedServiceQTO.setBizCode(BIZ_CODE);


            List<ValueAddedServiceDO> valueAddedServiceDTOList = valueAddedServiceDAO.queryValueAddedService(valueAddedServiceQTO);

        try {
            List<ValueAddedServiceDTO> x = valueAddedServiceManager.queryValueAddedService(valueAddedServiceQTO);
            System.currentTimeMillis();

        } catch (ItemException e) {
            e.printStackTrace();
        }





    }

    /**
     * 添加增值服务
     */

    @Test
    public void test02(){

        ValueAddedServiceTypeQTO valueAddedServiceTypeQTO = new ValueAddedServiceTypeQTO();
        valueAddedServiceTypeQTO.setIdList(Lists.newArrayList(9L,29L));
        valueAddedServiceTypeQTO.setSellerId(35L);

        try {
            List<ValueAddedServiceTypeDTO> valueAddedServiceTypeDTOList = valueAddedServiceManager.queryValueAddedService(valueAddedServiceTypeQTO);

            System.currentTimeMillis();
        } catch (ItemException e) {
            e.printStackTrace();
        }



    }



}
