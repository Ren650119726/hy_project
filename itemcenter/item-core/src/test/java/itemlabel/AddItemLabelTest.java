package itemlabel;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemLabelDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.dao.RItemLabelDAO;
import com.mockuai.itemcenter.core.domain.RItemLabelDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemLabelManager;
import com.mockuai.itemcenter.core.manager.ItemManager;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class AddItemLabelTest {

    private Logger logger = LoggerFactory.getLogger(AddItemLabelTest.class);
    @Resource
    private ItemService itemService;
    @Resource
    private ItemLabelManager itemLabelManager;
    @Resource
    private RItemLabelDAO rItemLabelDAO;
    @Resource
    private ItemManager itemManager;

    ItemQTO itemQTO = new ItemQTO();

    public AddItemLabelTest() {
        itemQTO.setOffset(0);
        itemQTO.setSellerId(1841254L);
        itemQTO.setNeedPaging(true);
        itemQTO.setPageSize(PAGE_SIZE);
    }

    private final static String BIZ_CODE = "mockuai_demo";

    private final static String APP_KEY = "1b0044c3653b89673bc5beff190b68a1";

    private final static Long SELLER_ID = 38699L;
    private final static Integer PAGE_SIZE = 500;
    private final static Long[] labelIdList = {4L, 5L, 6L};


    public void addLabel(long labelId) throws ItemException {
        int addCount = 0;
        itemQTO.setOffset(0);
        while (true) {

            List<ItemDTO> itemDTOList = itemManager.queryItem(itemQTO);
            if (itemDTOList.isEmpty()) {
                break;
            }
            logger.info("offset {},pagesize:{}", itemQTO.getOffset(), itemQTO.getPageSize());
            itemQTO.setOffset(itemQTO.getOffset() + PAGE_SIZE);


            List<RItemLabelDO> itemLabelList = new ArrayList<RItemLabelDO>(itemDTOList.size());
            for (ItemDTO itemDTO : itemDTOList) {
                RItemLabelDO itemLabelDO = new RItemLabelDO();
                itemLabelDO.setLabelId(labelId);
                itemLabelDO.setItemId(itemDTO.getId());
                itemLabelDO.setSellerId(1841254L);
                itemLabelDO.setBizCode("hanshu");
                itemLabelList.add(itemLabelDO);

            }
            addCount += itemLabelList.size();
            logger.info("insert into item size:{},labelId:{}", itemLabelList.size(), labelId);
            rItemLabelDAO.addRList(itemLabelList);

        }
        logger.info("addCount:{}", addCount);
    }

    @Test
    public void test111() throws ItemException {

        for (long labelId : labelIdList) {
            addLabel(labelId);
        }


    }

    @Test
    public void test112() throws ItemException {

        int addCount = 0;

        //查询已上架的商品
        ItemQTO itemQTO = new ItemQTO();
        itemQTO.setSellerId(1841254L);
        itemQTO.setOffset(0);
        itemQTO.setItemStatus(4);
        while (true) {


            List<ItemDTO> itemDTOList = itemManager.queryItem(itemQTO);

            if (itemDTOList.isEmpty()) {
                break;
            }
            logger.info("offset {},pagesize:{}", itemQTO.getOffset(), itemQTO.getPageSize());
            itemQTO.setOffset(itemQTO.getOffset() + PAGE_SIZE);

            for (ItemDTO itemDTO : itemDTOList) {
                Long itemId = itemDTO.getId();
                logger.info("[{}] itemId:{}", itemId);

            }
            addCount += itemDTOList.size();


        }

        logger.info("addCount:{}", addCount);

    }

    /**
     * 添加增值服务
     */

    @Test
    public void test01() {

        Request request = new BaseRequest();
        request.setParam("appKey", APP_KEY);

        request.setCommand(ActionEnum.ADD_ITEM_LABEL.getActionName());

        ItemLabelDTO itemLabelDTO = new ItemLabelDTO();

        itemLabelDTO.setSellerId(SELLER_ID);
        itemLabelDTO.setIconUrl("xxx");
        itemLabelDTO.setLabelName("宇宙第一");
        itemLabelDTO.setLabelDesc("不用解释");
        itemLabelDTO.setScope(1);

        request.setParam("itemLabelDTO", itemLabelDTO);

        Response response = itemService.execute(request);

        int code = response.getCode();

        assertThat(code, is(10000));

    }


    @Test
    public void test02() {

        Request request = new BaseRequest();
        request.setParam("appKey", APP_KEY);

        request.setCommand(ActionEnum.ADD_ITEM_LABEL.getActionName());

        ItemLabelDTO itemLabelDTO = new ItemLabelDTO();

        itemLabelDTO.setSellerId(SELLER_ID);
        itemLabelDTO.setIconUrl("xxx");
        itemLabelDTO.setLabelName("爱咋咋地");
        itemLabelDTO.setLabelDesc("不用解释1");
        itemLabelDTO.setScope(2);

        request.setParam("itemLabelDTO", itemLabelDTO);

        itemLabelDTO.setItemIdList(Lists.newArrayList(101598L, 101596L));

        Response response = itemService.execute(request);

        int code = response.getCode();

        assertThat(code, is(10000));

    }

    @Test
    public void test333() throws ItemException {
        Request request = new BaseRequest();
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(226L);
        itemDTO.setBizCode("hanshu");
        itemDTO.setSellerId(0L);
        List<ItemLabelDTO> labelList = new ArrayList<ItemLabelDTO>();
        ItemLabelDTO itemLabelDTO = new ItemLabelDTO();
        itemLabelDTO.setId(54L);
        itemLabelDTO.setSellerId(1841254L);
        itemLabelDTO.setLabelName("专业打人");
        itemLabelDTO.setBizCode("hanshu");
        labelList.add(itemLabelDTO);

        ItemLabelDTO itemLabelDTO1 = new ItemLabelDTO();
        itemLabelDTO1.setId(55L);
        itemLabelDTO1.setSellerId(1841254L);
        itemLabelDTO1.setLabelName("2016 brz");
        itemLabelDTO1.setBizCode("hanshu");

        labelList.add(itemLabelDTO1);

        ItemLabelDTO itemLabelDTO2 = new ItemLabelDTO();
        itemLabelDTO2.setId(56L);
        itemLabelDTO2.setSellerId(1841254L);
        itemLabelDTO2.setLabelName("全场包邮");
        itemLabelDTO2.setBizCode("hanshu");

        labelList.add(itemLabelDTO2);
        itemDTO.setItemLabelDTOList(labelList);

        itemLabelManager.addRItemLabelList(itemDTO);


    }


}
