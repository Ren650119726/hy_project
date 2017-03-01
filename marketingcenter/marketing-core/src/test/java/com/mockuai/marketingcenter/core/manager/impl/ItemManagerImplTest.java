package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.marketingcenter.core.BaseTest;
import com.mockuai.marketingcenter.core.manager.ItemManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edgar.zr on 11/16/15.
 */
public class ItemManagerImplTest extends BaseTest {

    @Autowired
    private ItemManager itemManager;

    @Test
    public void testQueryItem() throws Exception {
        ItemQTO itemQTO = new ItemQTO();
        itemQTO.setIdList(new ArrayList<Long>());
        itemQTO.getIdList().add(101445L);
        itemQTO.setSellerId(38699L);
        List<ItemDTO> list = itemManager.queryItem(itemQTO, "5b036edd2fe8730db1983368a122fb45");

        System.err.println("");
    }
}