package com.mockuai.itemcenter.core.manager.impl; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SpecDTO;
import com.mockuai.itemcenter.common.domain.qto.SpecQTO;
import com.mockuai.itemcenter.core.dao.SpecDAO;
import com.mockuai.itemcenter.core.domain.SpecDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.SpecManager;
import com.mockuai.marketingcenter.common.domain.qto.PageQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by hy on 2016/8/30.
 */
@Service
public class SpecManagerImpl implements SpecManager {
    private static final Logger log = LoggerFactory.getLogger(SpecManagerImpl.class);


    @Autowired
    private SpecDAO specDAO;


    @Override
    public Long addSpec(SpecDTO specDTO) throws ItemException {
        SpecDO  specDO = new SpecDO();
        BeanUtils.copyProperties(specDTO,specDO);
        try {
            return specDAO.addSpec(specDO);
        }catch (Exception e){
            log.error("error to addSpec", e);
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "error to addSpec");

        }
    }

    @Override
    public List<SpecDTO> querySpec(SpecQTO specQTO) throws ItemException {
          try {
              List<SpecDO> specDOList = specDAO.querySpec(specQTO);
              List<SpecDTO> specDTOList = Lists.newArrayListWithCapacity(specDOList.size());
              for(SpecDO specDO : specDOList){
                  SpecDTO specDTO = new SpecDTO();
                  BeanUtils.copyProperties(specDO,specDTO);
                  specDTOList.add(specDTO);
              }
              return specDTOList;
          }catch (Exception e){
              log.error("error to querySpec", e);
              throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "error to querySpec");
          }


    }

    @Override
    public Integer updateSpec(SpecDTO specDTO) throws ItemException {
        try {
            SpecDO  specDO = new SpecDO();
            BeanUtils.copyProperties(specDTO,specDO);
            return specDAO.updateSpec(specDO);
        }catch (Exception e){
            log.error("error to updateSpec", e);
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "error to updateSpec");
        }

    }

    @Override
    public void deleteSpec(Long id) throws ItemException {
        try {
            specDAO.deleteSpec(id);

        }catch (Exception e){
            log.error("error to deleteSpec", e);
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "error to deleteSpec");
        }
    }

    @Override
    public SpecDTO getSpec(Long id) throws ItemException {
        try {
            SpecQTO specQTO = new SpecQTO();
            specQTO.setId(id);
            specQTO.setOffset(PageQTO.DEFAULT_OFFSET);
            specQTO.setPageSize(PageQTO.DEFAULT_COUNT);
            List<SpecDTO> specList =  querySpec(specQTO);
            if(specList == null || specList.isEmpty()){
                return null;
            }
            SpecDTO specDTO = new SpecDTO();
            BeanUtils.copyProperties(specList.get(0),specDTO);
            return  specDTO;
        }catch (Exception e){
            log.error("error to getSpec", e);
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "error to getSpec");
        }

    }

    @Override
    public Long countSpec(SpecQTO specQTO) throws ItemException {
        try {
            return specDAO.countSpec(specQTO);
        }catch (Exception e){
            log.error("error to countSpec", e);
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "error to countSpec");
        }
    }
}
