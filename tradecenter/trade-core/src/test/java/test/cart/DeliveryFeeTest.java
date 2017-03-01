//package test.cart;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.mockuai.tradecenter.core.manager.DeliveryManager;
//
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
//public class DeliveryFeeTest {
//	//@Resource
//	private DeliveryManager deliveryManager;
//
//	/*static class TestThread extends Thread{
//		private DeliveryManager deliveryManager;
//
//		public TestThread(DeliveryManager d){
//			this.deliveryManager = d;
//		}
//
//		public void run(){
//			long r = this.deliveryManager.getDeliveryFee(null, null);
//			System.out.println(r);
//		}
//	}*/
//
//	@Test
//	public void test(){
//		System.out.println("!!!!");
//		long fee ;
//		int i = 0;
//		fee = this.deliveryManager.getDeliveryFee(null, null);
//		System.out.println("end");
//		System.out.println(fee);
//
//		/*while(i++ < 1000){
////			new TestThread(this.deliveryManager).start();
//			System.out.println(fee);
//		}*/
//
//	}
//
//}
