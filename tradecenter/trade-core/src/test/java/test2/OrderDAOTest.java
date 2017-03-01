package test2;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.manager.OrderPaymentManager;

//import com.mockuai.tradecenter.client.cart.CartItemClient;

public class OrderDAOTest {
	
	public static void doModifyOrderTotalAmount(ClassPathXmlApplicationContext ctx){
		OrderManager dao = (OrderManager)ctx.getBean("orderManager");
		try {
			dao.updateOrderTotalAmountAndDeliveryFee(11l, 11l, -5l, 20l);
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void doSetMark(ClassPathXmlApplicationContext ctx){
	
		OrderManager dao = (OrderManager)ctx.getBean("orderManager");
		
		try {
			dao.updateOrderAsteriskMark(11l, 11l, false);
		} catch (TradeException e) {
			// TODO Auto-generated catch blocks
			e.printStackTrace();
		}
		
	}
	
	public static void updateMemo(ClassPathXmlApplicationContext ctx){
		OrderManager dao = (OrderManager)ctx.getBean("orderManager");
		
		try {
			dao.updateOrderMemo(11l, 11l, "111", 2);
		} catch (TradeException e) {
			// TODO Auto-generated catch blocks
			e.printStackTrace();
		}
	}
	
//	public static void doQuery(ClassPathXmlApplicationContext ctx){
//		OrderManager dao = (OrderManager)ctx.getBean("orderManager");
//		OrderQTO query = new OrderQTO();
//		query.setOrderTimeStart(DateUtil.convertStringToDate("2015"));
//		query.setOrderTimeEnd(orderTimeEnd);
//		dao.querySellerOrders(query);
//	}
	
	public static void doQueryTimeoutOrders(ClassPathXmlApplicationContext ctx) throws TradeException{
		OrderManager dao = (OrderManager)ctx.getBean("orderManager");
		OrderQTO query = new OrderQTO();
		query.setTimeoutMinuteNumber(30);
		dao.queryTimeoutUnpaidOrders(query);
	}
	
	public static void doQueryOvertimeOrders(ClassPathXmlApplicationContext ctx)throws TradeException{
		OrderManager dao = (OrderManager)ctx.getBean("orderManager");
		OrderQTO query = new OrderQTO();
		dao.getHasBuyCount(11l, 11l);
	}
	
//	public static void queryItemComment(ClassPathXmlApplicationContext ctx)throws TradeException{
//		com.mockuai.itemcenter.client.ItemClient itemClient = (ItemClient) ctx.getBean("itemClient");
//		com.mockuai.itemcenter.common.domain.qto.ItemCommentQTO  query = new com.mockuai.itemcenter.common.domain.qto.ItemCommentQTO ();
//		
//		query.setSellerId(1l);
//		
//		com.mockuai.itemcenter.common.api.Response response = itemClient.queryItemComment(query);
//		
//		List<ItemCommentDTO> list = (List<ItemCommentDTO>) response.getModule();
//		
//		for(ItemCommentDTO dto : list){
//			System.out.println("comment content:"+dto.getContent());
//			System.out.print("order id="+dto.getOrderId());
//		}
//		
////		System.out.println("response:"+response);
//		
//	}
	
	public static void updateSubOrdrePaySuccess(ClassPathXmlApplicationContext ctx)throws TradeException{
		OrderManager dao = (OrderManager)ctx.getBean("orderManager");
		//dao.orderPaySuccess(4732l, 38757l);
	}
	
	
	
	public static void main(String args[]) throws TradeException{
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		System.out.println("start");
		
		System.out.println();
		System.out.println("end");
		
//		OrderDAO dao = (OrderDAO)ctx.getBean("orderDAO");
//		
//		OrderQTO query = new OrderQTO();
////		query.setConsigneeMobile("15157188213");
////		query.setConsignee("super");
//		query.setOffset(0);
//		query.setCount(20);
//		dao.queryUserOrders(query);
//		
//		dao.getSellerOrdersTotalCount(query);
//		doModifyOrderTotalAmount(ctx);
//		doSetMark(ctx);
		
		
//		doQueryTimeoutOrders(ctx);
		
//		queryItemComment(ctx);
//		doQueryOvertimeOrders(ctx);
//		updateMemo(ctx);
		updateSubOrdrePaySuccess(ctx);
		
		OrderManager orderMng = (OrderManager)ctx.getBean("orderManager");
		 List<OrderDO> subOrderDOs = orderMng.querySubOrders(4732l);
		 
		 OrderPaymentManager orderPaymentMng = (OrderPaymentManager) ctx.getBean("orderPaymentManager");
		 
		 if(null!=subOrderDOs&&subOrderDOs.isEmpty()==false){
 			for(OrderDO order:subOrderDOs){
 				//更新支付单信息
                 OrderPaymentDO orderPaymentDO = orderPaymentMng.getOrderPayment(order.getId(),order.getUserId());
                 if(orderPaymentDO == null){
                     //TODO error handle
                 	continue;
                 }
                 
                 orderPaymentMng.paySuccess(orderPaymentDO.getId(), orderPaymentDO.getUserId(),
                 		orderPaymentDO.getPayAmount(), "2333");
                 

                
                 
                 
 			}
 			
         }
	}
}	
