//package  test.order;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//
//import org.springframework.beans.BeansException;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.mockuai.tradecenter.common.domain.OrderQTO;
//import com.mockuai.tradecenter.core.dao.OrderDAO;
//import com.mockuai.tradecenter.core.domain.OrderDO;
//import com.mockuai.tradecenter.core.exception.TradeException;
//import com.mockuai.tradecenter.core.manager.OrderManager;
//import com.mockuai.tradecenter.core.util.DateUtil;
//
////import com.mockuai.tradecenter.client.cart.CartItemClient;
//
//public class OrderDAOTest {
//
////	public static void doModifyOrderTotalAmount(ClassPathXmlApplicationContext ctx){
////		OrderManager dao = (OrderManager)ctx.getBean("orderManager");
////		try {
////			dao.updateOrderTotalAmountAndDeliveryFee(11l, 11l, -5l, 20l);
////		} catch (TradeException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////	}
////
////	public static void doSetMark(ClassPathXmlApplicationContext ctx){
////
////		OrderManager dao = (OrderManager)ctx.getBean("orderManager");
////
////		try {
////			dao.updateOrderAsteriskMark(11l, 11l, false);
////		} catch (TradeException e) {
////			// TODO Auto-generated catch blocks
////			e.printStackTrace();
////		}
////
////	}
//
////	public static void doQuery(ClassPathXmlApplicationContext ctx){
////		OrderManager dao = (OrderManager)ctx.getBean("orderManager");
////		OrderQTO query = new OrderQTO();
////		query.setOrderTimeStart(DateUtil.convertStringToDate("2015"));
////		query.setOrderTimeEnd(orderTimeEnd);
////		dao.querySellerOrders(query);
////	}
//
////	public static void doQueryTimeoutOrders(ClassPathXmlApplicationContext ctx) throws TradeException{
////		OrderManager dao = (OrderManager)ctx.getBean("orderManager");
////		OrderQTO query = new OrderQTO();
////		query.setTimeoutMinuteNumber(30);
////		dao.queryTimeoutUnpaidOrders(query);
////	}
////
//	public static void doQueryOvertimeOrders(ClassPathXmlApplicationContext ctx)throws TradeException{
//		try {
//			OrderManager dao = (OrderManager)ctx.getBean("orderManager");
//			int count = -1;
//
//			List<OrderDO> orders = dao.queryOverTimeOrder(count);
//
//			Iterator<OrderDO> iterator = orders.iterator();
//			while(iterator.hasNext()){
//				System.out.println(iterator.next().getId());
//			}
//		} catch (BeansException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public static void doGetTotalAmount(ClassPathXmlApplicationContext ctx){
//		OrderManager dao = (OrderManager)ctx.getBean("orderManager");
//
//		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
//		Date orderTimeStart=null;
//		Date orderTimeEnd=null;
//		try {
//			orderTimeStart = sdf.parse("2015-7-29");
//			orderTimeEnd = sdf.parse("2015-7-31");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		OrderQTO orderQTO = new OrderQTO();
//		orderQTO.setOrderTimeStart(orderTimeStart);
//		orderQTO.setOrderTimeEnd(orderTimeEnd);
//		orderQTO.setSellerId(91L);
//
//		System.out.println("成交额："+dao.getTotalAmount(orderQTO));
//	}
//
//	public static void getTotalOrderAmount(ClassPathXmlApplicationContext ctx){
//		OrderManager dao = (OrderManager)ctx.getBean("orderManager");
//
//		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
//		Date orderTimeStart=null;
//		Date orderTimeEnd=null;
//		try {
//			orderTimeStart = sdf.parse("2015-7-29");
//			orderTimeEnd = sdf.parse("2015-7-31");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		OrderQTO orderQTO = new OrderQTO();
////		orderQTO.setOrderTimeStart(orderTimeStart);
////		orderQTO.setOrderTimeEnd(orderTimeEnd);
//		orderQTO.setSellerId(91L);
//
//		System.out.println("订单总数："+dao.getTotalOrderAmount(orderQTO));
//	}
//
//	public static void getPaidOrderAmount(ClassPathXmlApplicationContext ctx){
//		OrderManager dao = (OrderManager)ctx.getBean("orderManager");
//
//		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
//		Date orderTimeStart=null;
//		Date orderTimeEnd=null;
//		try {
//			orderTimeStart = sdf.parse("2015-7-29");
//			orderTimeEnd = sdf.parse("2015-7-31");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		OrderQTO orderQTO = new OrderQTO();
//		orderQTO.setOrderTimeStart(orderTimeStart);
//		orderQTO.setOrderTimeEnd(orderTimeEnd);
//		orderQTO.setSellerId(91L);
//
//		System.out.println("已付订单数："+dao.getPaidOrderAmount(orderQTO));
//	}
//
//	public static void getTotalUserAmount(ClassPathXmlApplicationContext ctx){
//		OrderManager dao = (OrderManager)ctx.getBean("orderManager");
//
//		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
//		Date orderTimeStart=null;
//		Date orderTimeEnd=null;
//		try {
//			orderTimeStart = sdf.parse("2015-7-29");
//			orderTimeEnd = sdf.parse("2015-7-31");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		OrderQTO orderQTO = new OrderQTO();
//		orderQTO.setOrderTimeStart(orderTimeStart);
//		orderQTO.setOrderTimeEnd(orderTimeEnd);
//		orderQTO.setSellerId(91L);
//
//		System.out.println("用户总数："+dao.getTotalUserAmount(orderQTO));
//	}
//
//	public static void getPaidUserAmount(ClassPathXmlApplicationContext ctx){
//		OrderManager dao = (OrderManager)ctx.getBean("orderManager");
//
//		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
//		Date orderTimeStart=null;
//		Date orderTimeEnd=null;
//		try {
//			orderTimeStart = sdf.parse("2015-7-29");
//			orderTimeEnd = sdf.parse("2015-7-31");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		OrderQTO orderQTO = new OrderQTO();
//		orderQTO.setOrderTimeStart(orderTimeStart);
//		orderQTO.setOrderTimeEnd(orderTimeEnd);
//		orderQTO.setSellerId(91L);
//
//		System.out.println("已付用户数："+dao.getPaidUserAmount(orderQTO));
//	}
//
//	public static void main(String args[]) throws TradeException{
//
//		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//		System.out.println("start");
//		getTotalOrderAmount(ctx);
//		System.out.println("end");
//
////		OrderDAO dao = (OrderDAO)ctx.getBean("orderDAO");
////
////		OrderQTO query = new OrderQTO();
//////		query.setConsigneeMobile("15157188213");
//////		query.setConsignee("super");
////		query.setOffset(0);
////		query.setCount(20);
////		dao.queryUserOrders(query);
////
////		dao.getSellerOrdersTotalCount(query);
////		doModifyOrderTotalAmount(ctx);
////		doSetMark(ctx);
//
//
////		doQueryTimeoutOrders(ctx);
//
//	}
//}
