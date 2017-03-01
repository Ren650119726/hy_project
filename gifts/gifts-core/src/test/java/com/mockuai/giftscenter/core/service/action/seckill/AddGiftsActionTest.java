package com.mockuai.giftscenter.core.service.action.seckill;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import com.mockuai.giftscenter.common.constant.ActionEnum;
import com.mockuai.giftscenter.common.domain.dto.GiftsPacketDTO;
import com.mockuai.giftscenter.core.BaseActionTest;

/**
 * Created by edgar.zr on 12/13/15.
 */
public class AddGiftsActionTest extends BaseActionTest {

    public AddGiftsActionTest() {
        super(AddGiftsActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.ADD_GIFTS.getActionName();
    }

    @Test
    public void test() {

    	GiftsPacketDTO giftsPacketDTO = new GiftsPacketDTO();
    	giftsPacketDTO.setGiftsStartTime(DateUtils.addDays(new Date(), 1));
    	giftsPacketDTO.setGiftsEndTime(DateUtils.addDays(new Date(), 2));
        request.setParam("giftsPacketDTO", giftsPacketDTO);
        doExecute();
    }
}