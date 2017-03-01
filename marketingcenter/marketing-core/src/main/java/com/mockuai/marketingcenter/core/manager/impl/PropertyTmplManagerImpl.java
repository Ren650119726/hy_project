package com.mockuai.marketingcenter.core.manager.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.marketingcenter.common.constant.PropertyOwnerType;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.PropertyDTO;
import com.mockuai.marketingcenter.common.domain.qto.PropertyTmplQTO;
import com.mockuai.marketingcenter.core.dao.PropertyTmplDAO;
import com.mockuai.marketingcenter.core.domain.PropertyDO;
import com.mockuai.marketingcenter.core.domain.PropertyTmplDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.PropertyTmplManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyTmplManagerImpl implements PropertyTmplManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyTmplManager.class);

    @Resource
    private PropertyTmplDAO propertyTmplDAO;

    public long addPropertyTmpl(PropertyTmplDO propertyTmplDO) throws MarketingException {

        try {

            if (StringUtils.isBlank(propertyTmplDO.getName())) {
                propertyTmplDO.setName(propertyTmplDO.getPkey());
            }

            return this.propertyTmplDAO.addPropertyTmpl(propertyTmplDO);

        } catch (Exception e) {
            LOGGER.error("failed when adding propertyTmpl : {}", JsonUtil.toJson(propertyTmplDO), e);
            throw new MarketingException(ResponseCode.DB_OP_ERROR);
        }
    }

    public int deletePropertyTmpl(Long propertyTmplId, Long userId)
            throws MarketingException {

        try {

            return this.propertyTmplDAO.deletePropertyTmpl(propertyTmplId, userId);

        } catch (Exception e) {
            LOGGER.error("failed when deleting propertyTmpl, propertyTmplId :{}, userId : {}",
                    propertyTmplId, userId, e);
            throw new MarketingException(ResponseCode.DB_OP_ERROR);
        }
    }

    public int updatePropertyTmpl(PropertyTmplDO propertyTmplDO)
            throws MarketingException {

        try {

            return this.propertyTmplDAO.updatePropertyTmpl(propertyTmplDO);

        } catch (Exception e) {

            LOGGER.error("failed when updating propertyTmpl : {}", JsonUtil.toJson(propertyTmplDO), e);
            throw new MarketingException(ResponseCode.DB_OP_ERROR);
        }
    }

    public List<PropertyTmplDO> queryPropertyTmpl(PropertyTmplQTO propertyTmplQTO)
            throws MarketingException {

        try {

            return this.propertyTmplDAO.queryPropertyTmpl(propertyTmplQTO);

        } catch (Exception e) {
            LOGGER.error("failed when querying propertyTmpl : {}", JsonUtil.toJson(propertyTmplQTO), e);
            throw new MarketingException(ResponseCode.DB_OP_ERROR);
        }
    }

    public long queryPropertyTmplCount(PropertyTmplQTO propertyTmplQTO)
            throws MarketingException {

        try {

            return this.propertyTmplDAO.queryPropertyTmplCount(propertyTmplQTO);

        } catch (Exception e) {
            LOGGER.error("failed when querying count of propertyTmpl : {}", JsonUtil.toJson(propertyTmplQTO), e);
            throw new MarketingException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public void validateMustIncludeProperty(List<PropertyDTO> propertyDTOs, Long ownerId) throws MarketingException {

        PropertyTmplQTO propertyTmplQTO = new PropertyTmplQTO();
        propertyTmplQTO.setOwnerId(ownerId);
        propertyTmplQTO.setOwnerType(PropertyOwnerType.TOOL.getValue());

        Map<String, PropertyDO> propertyDOMap = new HashMap<String, PropertyDO>();
        if (propertyDTOs != null) {
            for (PropertyDTO propertyDTO : propertyDTOs) {
                propertyDOMap.put(propertyDTO.getPkey(), ModelUtil.genPropertyDO(propertyDTO));
            }
        }

        //查询营销活动属性模板列表
        List<PropertyTmplDO> propertyTmplDOs = queryPropertyTmpl(propertyTmplQTO);

        Map<String, PropertyTmplDO> propertyTmplMap = new HashMap<String, PropertyTmplDO>();
        if (propertyTmplDOs != null) {
            for (PropertyTmplDO propertyTmplDO : propertyTmplDOs) {
                propertyTmplMap.put(propertyTmplDO.getPkey(), propertyTmplDO);
            }
        }

        //必选属性校验
        for (Map.Entry<String, PropertyTmplDO> entry : propertyTmplMap.entrySet()) {

            //如果营销活动属性列表中没有涵盖所有必选的属性模板中的属性的话，则返回错误提示
            if (propertyDOMap.containsKey(entry.getKey()) == false) {
                if (entry.getValue().getRequiredMark().intValue() == 1) {
                    throw new MarketingException(ResponseCode.PARAMETER_MISSING, "the property " + entry.getKey() + " is missing");
                }
            } else {
                PropertyTmplDO propertyTmplDO = entry.getValue();
                //填充属性名称
                propertyDOMap.get(entry.getKey()).setName(propertyTmplDO.getName());
                //填充属性取值类型
                propertyDOMap.get(entry.getKey()).setValueType(propertyTmplDO.getValueType());
            }
        }
    }
}