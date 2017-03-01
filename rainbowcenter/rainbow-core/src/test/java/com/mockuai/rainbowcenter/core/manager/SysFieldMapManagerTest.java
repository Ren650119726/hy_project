package com.mockuai.rainbowcenter.core.manager;

import com.mockuai.rainbowcenter.common.constant.SysFieldNameEnum;
import com.mockuai.rainbowcenter.common.constant.SysFieldTypeEnum;
import com.mockuai.rainbowcenter.common.constant.SysFieldValueTypeEnum;
import com.mockuai.rainbowcenter.common.dto.SysFieldMapDTO;
import com.mockuai.rainbowcenter.common.qto.SysFieldMapQTO;
import com.mockuai.rainbowcenter.common.util.JsonUtil;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by yeliming on 16/3/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContext.xml"})
public class SysFieldMapManagerTest {
    @Resource
    private SysFieldMapManager sysFieldMapManager;

    @Test
    public void addSysFieldMapTest() throws RainbowException {
        SysFieldMapDTO sysFieldMapDTO = new SysFieldMapDTO();
        sysFieldMapDTO.setBizCode("haihuan");
        sysFieldMapDTO.setType(SysFieldTypeEnum.EDB.getValue());
        sysFieldMapDTO.setFieldName(SysFieldNameEnum.EDB_ORDER.getValue());
        sysFieldMapDTO.setValue("6349");
        Long id = sysFieldMapManager.addSysFieldMap(sysFieldMapDTO);
        Assert.assertNotNull(id);
        System.out.println(id);
    }

    @Test
    public void addSysFieldMapTest1() throws RainbowException {
        SysFieldMapDTO sysFieldMapDTO = new SysFieldMapDTO();
        sysFieldMapDTO.setBizCode("edb_test1");
        sysFieldMapDTO.setType(SysFieldTypeEnum.EDB.getValue());
        sysFieldMapDTO.setFieldName(SysFieldNameEnum.USER.getValue());
        sysFieldMapDTO.setOutValue("edbOutValue1");
        sysFieldMapDTO.setOutValueType(SysFieldValueTypeEnum.TYPE_STRING.getValue());
        Long id = sysFieldMapManager.addSysFieldMap(sysFieldMapDTO);
        Assert.assertNotNull(id);
        System.out.println(id);
    }

    @Test
    public void getSysFieldMapTest() throws RainbowException {
        SysFieldMapQTO sysFieldMapQTO = new SysFieldMapQTO();
        sysFieldMapQTO.setBizCode("haihuan");//bizCode
        sysFieldMapQTO.setType(SysFieldTypeEnum.EDB.getValue());
        sysFieldMapQTO.setFieldName(SysFieldNameEnum.SUPPLIER.getValue());
        sysFieldMapQTO.setOutValue("1");
        SysFieldMapDTO sysFieldMapDTO = sysFieldMapManager.getSysFieldMap(sysFieldMapQTO, false);
        System.out.println(JsonUtil.toJson(sysFieldMapDTO));
    }

    @Test
    public void getSysFieldMapTest1() throws RainbowException {
        SysFieldMapQTO sysFieldMapQTO = new SysFieldMapQTO();
        sysFieldMapQTO.setBizCode("mockuai_demo");//bizCode
        sysFieldMapQTO.setType(SysFieldTypeEnum.EDB.getValue());
        sysFieldMapQTO.setFieldName(SysFieldNameEnum.SUPPLIER.getValue());
        sysFieldMapQTO.setValue("1001");
        SysFieldMapDTO sysFieldMapDTO = sysFieldMapManager.getSysFieldMap(sysFieldMapQTO, false);
        System.out.println(JsonUtil.toJson(sysFieldMapDTO));
    }

    @Test
    public void querySysFieldMapTest() throws RainbowException {
        SysFieldMapQTO sysFieldMapQTO = new SysFieldMapQTO();
        sysFieldMapQTO.setBizCode("edb_test");
        sysFieldMapQTO.setType(SysFieldTypeEnum.EDB.getValue());
        sysFieldMapQTO.setFieldName(SysFieldNameEnum.USER.getValue());
        List<SysFieldMapDTO> sysFieldMapDTOs = this.sysFieldMapManager.querySysFieldMap(sysFieldMapQTO, true);
        System.out.println(JsonUtil.toJson(sysFieldMapDTOs));
    }

    @Test
    public void updateSysFieldMapTest() throws RainbowException {
        SysFieldMapDTO sysFieldMapDTO = new SysFieldMapDTO();
        sysFieldMapDTO.setFieldName(SysFieldNameEnum.USER.getValue());
        sysFieldMapDTO.setType(SysFieldTypeEnum.EDB.getValue());
        sysFieldMapDTO.setBizCode("edb_test");
        sysFieldMapDTO.setOutValue("edbOutValue1");

        sysFieldMapDTO.setValue("value1");
        sysFieldMapDTO.setValueType(SysFieldValueTypeEnum.TYPE_STRING.getValue());

        Integer result = this.sysFieldMapManager.updateSysFieldMapByOutValue(sysFieldMapDTO);
        Assert.assertTrue(result >= 1);
    }

    @Test
    public void updateSysFieldMapTest1() throws RainbowException {
        SysFieldMapDTO sysFieldMapDTO = new SysFieldMapDTO();
        sysFieldMapDTO.setFieldName(SysFieldNameEnum.USER.getValue());
        sysFieldMapDTO.setType(SysFieldTypeEnum.EDB.getValue());
        sysFieldMapDTO.setBizCode("edb_test");
//        sysFieldMapDTO.setValue("value1");

        sysFieldMapDTO.setOutValue("edbOutValue1");
        sysFieldMapDTO.setOutValueType(SysFieldValueTypeEnum.TYPE_STRING.getValue());

        Integer result = this.sysFieldMapManager.updateSysFieldMapByValue(sysFieldMapDTO);
        Assert.assertTrue(result >= 1);
    }

    @Test
    public void uniqueSysTest() throws RainbowException {
        SysFieldMapQTO sysFieldMapQTO = new SysFieldMapQTO();
        sysFieldMapQTO.setBizCode("haihuan");
        sysFieldMapQTO.setType("edb");
        List<SysFieldMapDTO> sysFieldMapDTOs = this.sysFieldMapManager.querySysFieldMap(sysFieldMapQTO,true);
        for (SysFieldMapDTO sysFieldMapDTO : sysFieldMapDTOs) {
            if(sysFieldMapDTO.getUniqueSys() == null){
                sysFieldMapDTO.setUniqueSys(DigestUtils.md5Hex(sysFieldMapDTO.getFieldName()+sysFieldMapDTO.getOutValue()+sysFieldMapDTO.getValue()+sysFieldMapDTO.getBizCode()+sysFieldMapDTO.getType()));
                this.sysFieldMapManager.updateSysFieldMapByValue(sysFieldMapDTO);
            }
        }
    }


    @Test
    public void updateRemoveByOutValueTest() throws RainbowException {
        SysFieldMapDTO sysFieldMapDTO = new SysFieldMapDTO();
        sysFieldMapDTO.setType(SysFieldTypeEnum.GYERP.getValue());
        sysFieldMapDTO.setOutValue("2016062417274827900000001038");
        Integer result = this.sysFieldMapManager.updateRemoveByOutValue(sysFieldMapDTO);
        System.out.print("result:{}"+result);
        Assert.assertTrue(result >= 1);
    }
}