package comment;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.qto.ItemCommentQTO;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 测试：商品评分
 * Created by wanghailong on 15-7-27.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QueryItemCommentGradeTest {
    @Resource
    private ItemService itemService;

    @Test
    public void test_score() {
        Request request = new BaseRequest();
        ItemCommentQTO itemCommentQTO = new ItemCommentQTO();
        itemCommentQTO.setCurrentPage(1);
        itemCommentQTO.setPageSize(3);
        itemCommentQTO.setItemId(118L);
        itemCommentQTO.setSellerId(82L);
        itemCommentQTO.setScore(5L);
//        itemCommentQTO.setNeedPaging(true);
        request.setParam("itemCommentQTO", itemCommentQTO);
        request.setParam("needPaging","xx");

        request.setParam("appKey", "af7433eda4883d7139e2934dd8d035f1");
        request.setCommand(ActionEnum.QUERY_ITEMCOMMENTGRADE.getActionName());
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Test
    public void test_count_score() {
        Request request = new BaseRequest();
        ItemCommentQTO itemCommentQTO = new ItemCommentQTO();
        itemCommentQTO.setItemId(118L);
        itemCommentQTO.setSellerId(82L);
        request.setParam("itemCommentQTO", itemCommentQTO);
        request.setCommand(ActionEnum.COUNT_ITEMCOMMENTGRADE.getActionName());
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }
}
