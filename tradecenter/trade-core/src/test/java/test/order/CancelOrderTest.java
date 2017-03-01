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
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
//public class CancelOrderTest {
//
//	@Resource
//	private TradeService tradeService;
//
//	@Test
//	public void test(){
//		Request request = new BaseRequest();
//		request.setCommand(ActionEnum.CANCEL_ORDER.getActionName());
//
//		System.out.println("begin auditReturnApply");
//
//
//		Long orderId = 438L;
//		Long userId = 38790L;
//		request.setParam("appKey", "56ff3a489e4553987a56ea2e999a187f");
//		request.setParam("orderId", orderId);
//		request.setParam("userId", userId);
//
//		Response response = tradeService.execute(request);
//		System.out.println("end");
//		System.out.println(response);
//	}
//
////	@Test
//	public void test2(){
//		Request request = new BaseRequest();
//		request.setCommand(ActionEnum.CANCEL_ORDER.getActionName());
//
//		System.out.println("begin auditReturnApply");
//		ReturnOrderDTO returnOrderDTO = new ReturnOrderDTO();
//		returnOrderDTO.setOrderId(10L);
//		returnOrderDTO.setUserId(11L);
//		List<ReturnOrderItemDTO> list = new ArrayList<ReturnOrderItemDTO>();
//
//		ReturnOrderItemDTO item = new ReturnOrderItemDTO();
//
//
//		Long orderId = 10L;
//		Long userId = null;
//
//		request.setParam("orderId", orderId);
//		request.setParam("userId", userId);
//
//		Response response = tradeService.execute(request);
//		System.out.println("end");
//		System.out.println(response);
//	}
//
////	@Test
//	public void test3(){
//		Request request = new BaseRequest();
//		request.setCommand(ActionEnum.CANCEL_ORDER.getActionName());
//
//		System.out.println("begin auditReturnApply");
//		ReturnOrderDTO returnOrderDTO = new ReturnOrderDTO();
//		returnOrderDTO.setOrderId(10L);
//		returnOrderDTO.setUserId(11L);
//		List<ReturnOrderItemDTO> list = new ArrayList<ReturnOrderItemDTO>();
//
//		ReturnOrderItemDTO item = new ReturnOrderItemDTO();
//
//
//		Long orderId = 10L;
//		Long userId = 11L;
//
//		request.setParam("orderId", orderId);
//		request.setParam("userId", userId);
//
//		Response response = tradeService.execute(request);
//		System.out.println("end");
//		System.out.println(response);
//	}
//
////	@Test
//	public void test4(){
//		Request request = new BaseRequest();
//		request.setCommand(ActionEnum.CANCEL_ORDER.getActionName());
//
//		System.out.println("begin auditReturnApply");
//		ReturnOrderDTO returnOrderDTO = new ReturnOrderDTO();
//		returnOrderDTO.setOrderId(10L);
//		returnOrderDTO.setUserId(11L);
//		List<ReturnOrderItemDTO> list = new ArrayList<ReturnOrderItemDTO>();
//
//		ReturnOrderItemDTO item = new ReturnOrderItemDTO();
//
//
//		Long orderId = 20L;
//		Long userId = 21L;
//
//		request.setParam("orderId", orderId);
//		request.setParam("userId", userId);
//
//		Response response = tradeService.execute(request);
//		System.out.println("end");
//		System.out.println(response);
//	}
//}