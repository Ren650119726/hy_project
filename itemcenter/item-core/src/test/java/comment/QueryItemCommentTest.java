package comment;

import javax.annotation.Resource;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.CountCommentDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO;
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
import com.mockuai.itemcenter.common.domain.qto.ItemCommentQTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QueryItemCommentTest {
	@Resource
	private ItemService itemService;

	@Test
	/**
	 * 正确地查询
	 */
	public void test001() {
		Request request = new BaseRequest();
		ItemCommentQTO itemCommentQTO = new ItemCommentQTO();
		itemCommentQTO.setCurrentPage(1);
		itemCommentQTO.setPageSize(3);
		itemCommentQTO.setItemId(118L);
		itemCommentQTO.setSellerId(82L);
		itemCommentQTO.setNeedPaging(true);
		request.setParam("itemCommentQTO", itemCommentQTO);
		request.setCommand(ActionEnum.QUERY_ITEMCOMMENT.getActionName());
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
	 * itemCommentQTO 为空
	 */
	public void test002() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_ITEMCOMMENT.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}



    @Test
	public void test004(){

		String offsetStr = "0";
		String countStr = "20";
		String appKey = "af7433eda4883d7139e2934dd8d035f1";


		ItemCommentQTO itemCommentQTO = new ItemCommentQTO();
		itemCommentQTO.setSellerId(38699L);
		itemCommentQTO.setItemId(101082L);
		if(StringUtils.isBlank(offsetStr) == false){
			itemCommentQTO.setOffset(Integer.valueOf(offsetStr));
		}
		if(StringUtils.isBlank(countStr) == false){
			itemCommentQTO.setPageSize(Integer.valueOf(countStr));
		}



		com.mockuai.itemcenter.common.api.Request itemCommentReq = new BaseRequest();
		itemCommentReq.setCommand(ActionEnum.COUNT_ITEMCOMMENTGRADE.getActionName());
		itemCommentReq.setParam("itemCommentQTO", itemCommentQTO);
		itemCommentReq.setParam("appKey", appKey);

		CountCommentDTO countCommentDTO = null;
		// 分两次查询;
		Response<CountCommentDTO> response = itemService.execute(itemCommentReq);


		itemCommentReq.setCommand(ActionEnum.QUERY_ITEMCOMMENT.getActionName());
		itemCommentReq.setParam("itemCommentQTO", itemCommentQTO);
		itemCommentReq.setParam("appKey", appKey);

		Response<ItemCommentDTO> itemResp = this.itemService.execute(itemCommentReq);

		if(itemResp.getCode() != ResponseCode.SUCCESS.getCode()){

		}
	}
}
