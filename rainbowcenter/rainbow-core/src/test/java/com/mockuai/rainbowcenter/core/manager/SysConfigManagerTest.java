package com.mockuai.rainbowcenter.core.manager;

import com.mockuai.rainbowcenter.common.constant.SysFieldNameEnum;
import com.mockuai.rainbowcenter.common.constant.SysFieldTypeEnum;
import com.mockuai.rainbowcenter.common.dto.SysConfigDTO;
import com.mockuai.rainbowcenter.common.qto.SysConfigQTO;
import com.mockuai.rainbowcenter.common.util.JsonUtil;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yeliming on 16/3/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContext.xml"})
public class SysConfigManagerTest {
    @Resource
    private SysConfigManager sysConfigManager;

    @Test
    public void getSysConfigByValueTest() throws RainbowException {
        SysConfigQTO sysConfigQTO = new SysConfigQTO();
        sysConfigQTO.setBizCode("haihuan");
        sysConfigQTO.setValue("39481");
        sysConfigQTO.setFieldName(SysFieldNameEnum.SHOP_ID.getValue());
        sysConfigQTO.setType(SysFieldTypeEnum.EDB.getValue());
        sysConfigQTO.setSellerId(39481L);
        SysConfigDTO sysConfigDTO = sysConfigManager.getSysConfigByValue(sysConfigQTO);
        System.out.println(JsonUtil.toJson(sysConfigDTO));
    }

    @Test
    public void querySysConfig() throws RainbowException {
        SysConfigQTO sysConfigQTO = new SysConfigQTO();
//        sysConfigQTO.setBizCode("mockuai_demo");
        sysConfigQTO.setType("edb");
//        sysConfigQTO.setAccount("edb_123");
        sysConfigQTO.setFieldName("shopId");
        List<SysConfigDTO> sysConfigDTOs = this.sysConfigManager.queryConfig(sysConfigQTO);
        System.out.println(JsonUtil.toJson(sysConfigDTOs));
    }

    @Test
    public void querySysConfigOfAccount() throws RainbowException {
        String type = "edb";
        String fieldName = "shopId";
        List<SysConfigDTO> sysConfigDTOs = this.sysConfigManager.queryConfigOfAccount(fieldName, type);
        System.out.println(JsonUtil.toJson(sysConfigDTOs));
    }
}