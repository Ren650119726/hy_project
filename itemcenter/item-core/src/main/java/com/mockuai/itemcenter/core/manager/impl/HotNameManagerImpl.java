package com.mockuai.itemcenter.core.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.higocenter.client.HigoClient;
import com.mockuai.higocenter.common.api.Response;
import com.mockuai.higocenter.common.domain.ItemHigoInfoDTO;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.HotNameDTO;
import com.mockuai.itemcenter.core.dao.HotNameDAO;
import com.mockuai.itemcenter.core.domain.HotNameDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.HotNameManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ItemUtil;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 *   
 * @author huangsiqian
 * @version 2016年9月19日 下午2:24:31 
 */
@Service
public class HotNameManagerImpl implements HotNameManager{
	private static final Logger log = LoggerFactory.getLogger(HotNameManagerImpl.class);
	@Autowired
	private HotNameDAO hotNameDAO;

	@Override
	public List<HotNameDTO> getHotNameList(HotNameDTO hotNameDTO) throws ItemException {
		try{
			List<HotNameDO> list = hotNameDAO.selectHotName(hotNameDTO);
			List<HotNameDTO> hotNameDTOs = new ArrayList<HotNameDTO>(); 
			if(list!=null){
				for(HotNameDO hotNameDO:list){
					HotNameDTO nameDTO = new HotNameDTO();
					nameDTO.setHotName(hotNameDO.getHotName());
					nameDTO.setId(hotNameDO.getId());
					nameDTO.setGmtCreated(hotNameDO.getGmtCreated());
					nameDTO.setGmtModified(hotNameDO.getGmtModified());
					nameDTO.setIndexSort(hotNameDO.getIndexSort());
					nameDTO.setClickVolume(hotNameDO.getClickVolume());
					hotNameDTOs.add(nameDTO);
				}
			}
			return hotNameDTOs;
		}catch (Exception e) {
			if (e.getCause().getCause() instanceof MySQLIntegrityConstraintViolationException) {
				throw new ItemException(ResponseCode.SYS_E_DEFAULT_ERROR,"热搜词查询出错");
			}
		}
		return null;
	}

	@Override
	public boolean updateHotName(HotNameDTO hotNameDTO)
			throws ItemException {

		HotNameDO hotNameDO  = new HotNameDO();
		ItemUtil.copyProperties(hotNameDTO,hotNameDO );
		int rows=hotNameDAO.updateHotName(hotNameDO);
		if(rows>0){
			return true;	
		}else {
			throw ExceptionUtil
			.getException(ResponseCode.SYS_E_DB_UPDATE, "更新数据失败");

		}
	}

	@Override
	public boolean insertHotName(HotNameDTO hotNameDTO)
			throws ItemException {

		HotNameDO hotNameDO  = new HotNameDO();
		ItemUtil.copyProperties(hotNameDTO,hotNameDO );
		Long rows=hotNameDAO.addHotName(hotNameDO);
		if(rows>0){
			return true;	
		}else {
			throw ExceptionUtil
			.getException(ResponseCode.SYS_E_DB_UPDATE, "添加数据失败");
		}
	}

	@Override
	public HotNameDTO getHotNameById(Long id) throws ItemException {
		HotNameDO hotNameDO = hotNameDAO.selectHotNameById(id);
		HotNameDTO hotNameDTO  = new HotNameDTO();
		if(null!=hotNameDO){
			ItemUtil.copyProperties(hotNameDO,hotNameDTO);
			return hotNameDTO;
		}
		return hotNameDTO;
	}

	@Override
	public boolean clickRateHotName(HotNameDTO hotNameDTO) throws ItemException {

		HotNameDO hotNameDO  = new HotNameDO();
		hotNameDO.setId(hotNameDTO.getId());
		hotNameDO.setHotName(hotNameDTO.getHotName());
		int rows=hotNameDAO.clickRateHotName(hotNameDO);
		if(rows>0){
			return true;	
		}else {
			throw ExceptionUtil
			.getException(ResponseCode.SYS_E_DB_UPDATE, "更新数据失败");

		}
	}

	@Override
	public HotNameDTO queryClimbObj(String climb ,Long indexSort) throws ItemException {
		try {
			HotNameDO hotNameDO =  hotNameDAO.queryClimbObj(climb, indexSort) ;
			HotNameDTO hotNameDTO = new HotNameDTO();
			BeanUtils.copyProperties(hotNameDO,hotNameDTO);
			return hotNameDTO;
		} catch (Exception e) {
			throw ExceptionUtil
					.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public Long hotNameCount() throws ItemException {
		try {
			Long count =  hotNameDAO.hotNameCount() ;
			return count;
		} catch (Exception e) {
			throw ExceptionUtil
					.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public HotNameDTO getHotNameByName(String hotName) throws ItemException {
		HotNameDO hotNameDO = hotNameDAO.selectHotNameByName(hotName);
		HotNameDTO hotNameDTO  = new HotNameDTO();
		if(null!=hotNameDO){
			ItemUtil.copyProperties(hotNameDO,hotNameDTO);
			return hotNameDTO;
		}
		return hotNameDTO;
	}

}
