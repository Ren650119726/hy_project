/**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.giftscenter.common.domain.dto.ActionGiftDTO;
import com.mockuai.giftscenter.common.domain.qto.GrantCouponRecordQTO;
import com.mockuai.giftscenter.core.domain.GrantCouponRecordDO;
import com.mockuai.giftscenter.core.exception.GiftsException;
import com.mockuai.giftscenter.core.manager.ActionGiftManager;
import com.mockuai.giftscenter.core.manager.GrantCouponRecordManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hy on 2016/7/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestActionGift {
    @Autowired
    private ActionGiftManager actionGiftManager;
    @Autowired
    private GrantCouponRecordManager grantCouponRecordManager;

  private String appKey = "";
        @Test
    public void testSave(){
        ActionGiftDTO actionGiftDTO = new ActionGiftDTO();
        actionGiftDTO.setActionType(1);
        actionGiftDTO.setAppType("1,2,3");
        actionGiftDTO.setOpenFlag(1);
        actionGiftDTO.setMarketingIds("13,5,51,13");
        //actionGiftManager.save(actionGiftDTO);

    }
    @Test
    public void testGetActionGift() throws GiftsException {
          actionGiftManager.queryByActionType(1,appKey);
    }


    @Test
    public void testUpdate(){
        ActionGiftDTO actionGiftDTO = new ActionGiftDTO();
        actionGiftDTO.setId(6L);
        actionGiftDTO.setActionType(1);
        actionGiftDTO.setAppType("1,2,3");
        actionGiftDTO.setOpenFlag(1);
        actionGiftDTO.setMarketingIds("13,5,51,13");
        //actionGiftManager.update(actionGiftDTO);
    }
    @Test
    public void testQuery() throws GiftsException {
        GrantCouponRecordDO grantCouponRecordDO = new GrantCouponRecordDO();
        grantCouponRecordDO.setUserId(14523L);
        grantCouponRecordDO.setMobile("张天一");
        grantCouponRecordDO.setCouponId(34213L);
        grantCouponRecordDO.setCouponName("满400减120");
        grantCouponRecordDO.setAmount(12000L);
        grantCouponRecordDO.setAppType(1);
        grantCouponRecordDO.setActionType(1);
        GrantCouponRecordDO grantCouponRecordDO2 = new GrantCouponRecordDO();
        grantCouponRecordDO2.setUserId(12578L);
        grantCouponRecordDO2.setMobile("1886750");
        grantCouponRecordDO2.setCouponId(34213L);
        grantCouponRecordDO2.setCouponName("满200减90");
        grantCouponRecordDO2.setAmount(9000L);
        grantCouponRecordDO2.setAppType(2);
        grantCouponRecordDO2.setActionType(2);
        List<GrantCouponRecordDO> data = new ArrayList<>();
        data.add(grantCouponRecordDO);
        data.add(grantCouponRecordDO2);
        grantCouponRecordManager.save(data);
    }
  @Test
    public void testQueryTotalCount() throws GiftsException {
        GrantCouponRecordQTO grantCouponRecordQTO = new GrantCouponRecordQTO();
        grantCouponRecordQTO.setActionType(2);
        grantCouponRecordQTO.setStartDate(new Date());
        grantCouponRecordQTO.setOffset(0);
        grantCouponRecordQTO.setCount(20);
        grantCouponRecordManager.queryAll(grantCouponRecordQTO);
        grantCouponRecordManager.queryTotalCount(grantCouponRecordQTO);
    }





    }
