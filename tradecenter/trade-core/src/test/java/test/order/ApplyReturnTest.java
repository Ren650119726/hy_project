//package test.order;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.mockuai.tradecenter.common.api.BaseRequest;
//import com.mockuai.tradecenter.common.api.Request;
//import com.mockuai.tradecenter.common.api.Response;
//import com.mockuai.tradecenter.common.api.TradeService;
//import com.mockuai.tradecenter.common.constant.ActionEnum;
//import com.mockuai.tradecenter.common.domain.ReturnOrderDTO;
//import com.mockuai.tradecenter.common.domain.ReturnOrderItemDTO;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
//public class ApplyReturnTest {
//	@Resource
//	private TradeService tradeService;
//
//	@Test
//	public void test(){
//		Request request = new BaseRequest();
//		request.setCommand(ActionEnum.APPLY_RETURN.getActionName());
//
//		System.out.println("begin applyReturn");
//		ReturnOrderDTO returnOrderDTO = new ReturnOrderDTO();
//		returnOrderDTO.setOrderId(10L);
//		returnOrderDTO.setUserId(11L);
//		List<ReturnOrderItemDTO> list = new ArrayList<ReturnOrderItemDTO>();
//
//		ReturnOrderItemDTO item = new ReturnOrderItemDTO();
//		item.setItemSkuId(111L);
//		item.setNumber(1);
//		item.setReturnAttach("attach");
//		item.setReturnDesc("DESC");
//		item.setReturnReason("reason");
//
//		list.add(item);
//		ReturnOrderItemDTO item2 = new ReturnOrderItemDTO();
//		item2 = new ReturnOrderItemDTO();
//		item2.setItemSkuId(112L);
//		item2.setNumber(3);
//		item2.setReturnAttach("attach2");
//		item2.setReturnDesc("DESC2");
//		item2.setReturnReason("reason2");
//
//		list.add(item2);
//
//		returnOrderDTO.setReturnItems(list);
//
//		request.setParam("returnOrderDTO", returnOrderDTO);
//
//		Response response = tradeService.execute(request);
//		System.out.println(response);
//	}
//
//	/**
//	 * 防止重复申请测试
//	 */
////	@Test
//	public void test2(){
//		Request request = new BaseRequest();
//		request.setCommand(ActionEnum.APPLY_RETURN.getActionName());
//
//		System.out.println("begin applyReturn");
//		ReturnOrderDTO returnOrderDTO = new ReturnOrderDTO();
//		returnOrderDTO.setOrderId(10L);
//		returnOrderDTO.setUserId(11L);
//		List<ReturnOrderItemDTO> list = new ArrayList<ReturnOrderItemDTO>();
//
//		ReturnOrderItemDTO item = new ReturnOrderItemDTO();
//		item.setItemSkuId(111L);
//		item.setNumber(1);
//		item.setReturnAttach("attach");
//		item.setReturnDesc("DESC");
//		item.setReturnReason("reason");
//
//		list.add(item);
//		ReturnOrderItemDTO item2 = new ReturnOrderItemDTO();
//		item2 = new ReturnOrderItemDTO();
//		item2.setItemSkuId(112L);
//		item2.setNumber(3);
//		item2.setReturnAttach("attach2");
//		item2.setReturnDesc("DESC2");
//		item2.setReturnReason("reason2");
//
//		list.add(item2);
//
//		returnOrderDTO.setReturnItems(list);
//
//		request.setParam("returnOrderDTO", returnOrderDTO);
//
//		Response response = tradeService.execute(request);
//		System.out.println(response);
//	}
//
//
//}