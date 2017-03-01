package com.mockuai.usercenter.core.user;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mockuai.usercenter.client.UserClient;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.UserDTO;

public class Test {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "spring-dubbo-consumer.xml" });
		context.start();
		/*
		 * ExpressClient expressClient = (ExpressClient) context
		 * .getBean("expressClient"); // // 创建一个DTO并赋值 ExpressDTO expressDTO =
		 * new ExpressDTO(); expressDTO.setClassName("Sf");
		 * expressDTO.setValue("shunfeng"); expressDTO.setName("顺风快递");
		 * expressDTO.setPrintTemplate("顺风模板");
		 * 
		 * Response response = expressClient.addExpress(expressDTO);
		 */
		UserClient userClient = (UserClient) context.getBean("userClient");
		/* Response<UserDTO> response = userClient.getUserById(29L); */
		Response<UserDTO> response = userClient.userLogin("yezhenglei",
				"zl123456", "test");
		System.out.println(response.getModule().getName());

	}
}
