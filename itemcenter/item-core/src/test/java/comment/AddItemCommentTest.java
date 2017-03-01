package comment;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.domain.ItemCommentDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemCommentManager;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.BeanUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddItemCommentTest {
	@Resource
	private ItemService itemService;

    @Resource
    private ItemCommentManager itemCommentManager;

	/**
	 * 正确添加评论
	 */
	@Test
	public void test001() {
		Request request = new BaseRequest();
		ItemCommentDTO itemCommentDTO = new ItemCommentDTO();
		itemCommentDTO.setUserId(38766L);
		itemCommentDTO.setUserName("qqq");
		itemCommentDTO.setItemId(101081L);
		itemCommentDTO.setSkuId(842L);
		itemCommentDTO.setOrderId(2191L);
		itemCommentDTO.setSellerId(38699L);
		itemCommentDTO.setContent("再测测测试");
		
		List<ItemCommentDTO> list =new ArrayList<ItemCommentDTO>();
		list.add(itemCommentDTO);
        request.setParam("appKey","af7433eda4883d7139e2934dd8d035f1");
		request.setParam("itemCommentList",list);
		request.setCommand(ActionEnum.ADD_ITEMCOMMENT.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	@Test
	public void test00r1() {
		Request request = new BaseRequest();
		ItemCommentDTO itemCommentDTO = new ItemCommentDTO();
		itemCommentDTO.setUserId(38766L);
		itemCommentDTO.setUserName("qqq");
		itemCommentDTO.setItemId(101081L);
		itemCommentDTO.setSkuId(842L);
		itemCommentDTO.setOrderId(2191L);
		itemCommentDTO.setSellerId(38699L);
		itemCommentDTO.setContent("再测测测试");

		List<ItemCommentDTO> list =new ArrayList<ItemCommentDTO>();
		list.add(itemCommentDTO);

        ItemCommentDO itemCommentDO = new ItemCommentDO();

        BeanUtils.copyProperties(itemCommentDTO,itemCommentDO);


        try {
            itemCommentManager.addItemComment(itemCommentDO);
        } catch (ItemException e) {
            e.printStackTrace();
        }

        System.out.println("**************************************");

	}

	
	/**
	 * ItemCommentDTO 为空
	 */
	public void test002() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_ITEMCOMMENT.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * 评论内容为空
	 */
	public void test003() {
		Request request = new BaseRequest();
		ItemCommentDTO itemCommentDTO = new ItemCommentDTO();
		itemCommentDTO.setUserId(1L);
		itemCommentDTO.setUserName("hu6");
		itemCommentDTO.setItemId(999L);
		itemCommentDTO.setOrderId(3L);
		itemCommentDTO.setSellerId(4L);
		request.setParam("itemCommentDTO", itemCommentDTO);
		request.setCommand(ActionEnum.ADD_ITEMCOMMENT.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

}
