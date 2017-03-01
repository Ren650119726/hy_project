package test.order;

import com.google.common.collect.Lists;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.MarketingManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QueryMarketingTest {

	@Resource
	private MarketingManager marketingManager;

    private String appKey = "1b0044c3653b89673bc5beff190b68a1";


     @Test
    public void test5(){


    }


	@Test
	public void test4() throws TradeException {
        List<MarketItemDTO> data = Lists.newArrayList();
        MarketItemDTO marketItemDTO = new MarketItemDTO();
        marketItemDTO.setItemId(28954L);
        marketItemDTO.setItemType(1);
        //marketItemDTO.setBrandId(2616L);
        marketItemDTO.setSellerId(1841254L);
        marketItemDTO.setCategoryId(4830L);
        marketItemDTO.setItemSkuId(43214L);
        data.add(marketItemDTO);
       // List<DiscountInfo> discountInfoList = marketingManager.getCartDiscountInfo(data,appKey);
      //  discountInfoList.isEmpty();
    }



}
