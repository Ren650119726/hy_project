//package test.order;
//
//import javax.annotation.Resource;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.mockuai.tradecenter.core.exception.TradeException;
//import com.mockuai.tradecenter.core.manager.OrderSeqManager;
//import com.mockuai.tradecenter.core.service.action.order.AddOrder;
//
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
//public class OrderSeqManagerTest2 {
//
//	@Resource
//	private OrderSeqManager orderSeqManager;
//
//	@Resource
//	private AddOrder addOrder;
//
//	@Test
//	public void test()throws TradeException{
//		/*System.out.println("begin");
//		OrderSeqDO o = null;
//		int i =0;
//		try{
////			i = orderSeqManager.updateOrderSeq();
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		String orderSn = addOrder.getPaymentSn(1);
//		System.out.println(orderSn);
//		System.out.println("end");
//		*/
//		/*System.out.println("begin");
//		int affectedRow = 0;
//		try{
//			affectedRow  = orderSeqManager.updateOrderSeq();
//		}catch(Exception e){
//			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
//		}
//		OrderSeqDO daySeq=null;
//		if(affectedRow > 0){
//			daySeq = orderSeqManager.getOrderSeq();
//		}else{
//			OrderSeqDO orderSeqDO = new OrderSeqDO();
//			orderSeqDO.setSeq(1);
//			daySeq = orderSeqManager.addOrderSeq(orderSeqDO);
//		}
//		String orderSn =  1 + daySeq.getDay().toString() + daySeq.getSeq();
//		System.out.println("end");
//		System.out.println(orderSn);*/
//
//		String orderSn = this.orderSeqManager.getOrderSn(75L);
//		System.out.println(orderSn);
//	}
//}
