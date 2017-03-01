package com.mockuai.marketingcenter.core.manager.impl.limitpurchase;

import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseGoodsDTO;
import com.mockuai.marketingcenter.core.dao.LimitedPurchaseGoodsDAO;
import com.mockuai.marketingcenter.core.domain.LimitedPurchaseGoodsDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.LimitedPurchaseGoodsManager;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
/**
 * Created by huangsiqian on 2016/10/12.
 */
public class LimtedPurchaseManagerImplTest {
    @Autowired
    private LimitedPurchaseGoodsManager limitedPurchaseGoodsManager;
    @Autowired
    private LimitedPurchaseGoodsDAO limitedPurchaseGoodsDAO;

   LimitedPurchaseGoodsDO limitedPurchaseGoodsDO = new LimitedPurchaseGoodsDO();


    @Test
    public void  testselectActivityGoods () throws MarketingException {
        limitedPurchaseGoodsDO.setActivityId(34L);
        limitedPurchaseGoodsDO.setItemId(39L);
        limitedPurchaseGoodsDO.setSkuId(253L);
        limitedPurchaseGoodsDAO.selectActivityGoods(limitedPurchaseGoodsDO);

    }

    @Test
    public void  testActivityGoods () throws MarketingException {
        limitedPurchaseGoodsDO.setActivityId(43L);
        limitedPurchaseGoodsDO.setItemId(1148L);
        limitedPurchaseGoodsDO.setGoodsQuantity(50L);
        limitedPurchaseGoodsDO.setSkuId(12801L);
        limitedPurchaseGoodsDO.setGoodsPrice(8000L);
        limitedPurchaseGoodsManager.addActivityGoods(limitedPurchaseGoodsDO);
    }
    @Test
    public void testActivityGoodsById() throws MarketingException {
        limitedPurchaseGoodsDO.setActivityId(34L);
        List<LimitedPurchaseGoodsDTO> list = limitedPurchaseGoodsManager.activityGoodsList(limitedPurchaseGoodsDO);
//        Iterator It = list.iterator();
//        ActivityGoodsDTO activityGoodsDTO = null;
//        while (It.hasNext()){
//             activityGoodsDTO = (ActivityGoodsDTO) It.next();
//
//            System.out.println(activityGoodsDTO.getSkuId());
//        }


    }
    @Test
    public  void updateActivityGoodsNum() throws MarketingException{
        limitedPurchaseGoodsDO.setActivityId(34L);
        limitedPurchaseGoodsDO.setGoodsQuantity(100L);
        limitedPurchaseGoodsDO.setItemId(32L);
        Boolean flag = limitedPurchaseGoodsManager.updateActivityGoodsNum(limitedPurchaseGoodsDO);
        System.out.println("FLAG :"+flag);
    }
    @Test
    public  void updateActivityGoodsPrice() throws MarketingException{
        limitedPurchaseGoodsDO.setActivityId(34L);
        limitedPurchaseGoodsDO.setGoodsPrice(800L);
        limitedPurchaseGoodsDO.setItemId(32L);
        limitedPurchaseGoodsDO.setSkuId(251L);
        Boolean flag = limitedPurchaseGoodsManager.updateActivityGoodsPrice(limitedPurchaseGoodsDO);
        System.out.println("FLAG :"+flag);
    }
    @Test
    public  void deleteActivityGoods() throws MarketingException{
        limitedPurchaseGoodsDO.setActivityId(34L);
//        limitedPurchaseGoodsDO.setGoodsPrice(300L);
//        limitedPurchaseGoodsDO.setGoodsQuantity(500L);
        limitedPurchaseGoodsDO.setItemId(32L);
//        limitedPurchaseGoodsDO.setSkuId(257L);
        Boolean flag = limitedPurchaseGoodsManager.deleteActivityGoods(limitedPurchaseGoodsDO);
        System.out.println("FLAG :"+flag);
    }
    @Test
    public  void ActivityGoodsById() throws MarketingException{
        List list = new ArrayList();
        list = (List) limitedPurchaseGoodsManager.selectGoodsItemId(34L);
        System.out.println("FLAG :"+list);
    }
    @Test
    public  void numGoodsById() throws MarketingException{
        LimitedPurchaseGoodsDO goodsDO = new LimitedPurchaseGoodsDO();
        goodsDO.setActivityId(34L);
        goodsDO.setItemId(33L);
        Long goodsNum = limitedPurchaseGoodsManager.selectGoodsQuantityById(goodsDO);
        System.out.println("FLAG :"+goodsNum);
    }
}
