import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "spring-dubbo-consumer.xml" });
		context.start();
		/*
		 * ExpressClient expressClient = (ExpressClient) context
		 * .getBean("expressClient"); // // 创建一个DTO并赋值 ExpressDTO expressDTO =
		 * new ExpressDTO(); expressDTO.setClassName("Sf");
		 * expressDTO.setCode("shunfeng"); expressDTO.setName("顺风快递");
		 * expressDTO.setPrintTemplate("顺风模板");
		 * 
		 * Response response = expressClient.addExpress(expressDTO);
		 */
//		ItemClient itemClient = (ItemClient) context.getBean("itemClient");
//		Response<ItemDTO> response = itemClient.getItem(22L, 1L);
//		System.out.println(response.getModule());

	}

}
