package com.mockuai.mainweb.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.mainweb.common.constant.ResponseCode;
import com.mockuai.mainweb.common.domain.dto.PageGridContentDTO;
import com.mockuai.mainweb.common.domain.dto.PageGridDTO;
import com.mockuai.mainweb.common.domain.dto.PageGridElementDTO;
import com.mockuai.mainweb.common.domain.dto.PageGridMergeCellsDTO;
import com.mockuai.mainweb.common.domain.qto.PageGridQTO;
import com.mockuai.mainweb.core.dao.PageGridContentDAO;
import com.mockuai.mainweb.core.dao.PageGridDAO;
import com.mockuai.mainweb.core.dao.PageGridMergeCellsDAO;
import com.mockuai.mainweb.core.domain.PageGridContentDO;
import com.mockuai.mainweb.core.domain.PageGridDO;
import com.mockuai.mainweb.core.domain.PageGridMergeCellsDO;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.PageGridManager;
import com.mockuai.mainweb.core.util.JsonUtil;
import com.mockuai.mainweb.core.util.ModelUtil;

@Service
public class PageGridManagerImpl implements PageGridManager {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private PageGridDAO pageGridDAO;
	
	@Resource
	private PageGridContentDAO pageGridContentDAO; 
	
	@Resource
	private PageGridMergeCellsDAO pageGridMergeCellsDAO;
	
	@Override
	public void addGrid(PageGridElementDTO pageGridElementDTO) throws MainWebException {
		
		PageGridDO pageGridDO = new PageGridDO();
		
		try {
			BeanUtils.copyProperties(pageGridDO, pageGridElementDTO);
			
			pageGridDAO.addGrid(pageGridDO);
			pageGridElementDTO.setId(pageGridDO.getId());
		} catch (Exception e) {
			throw new MainWebException(ResponseCode.SYS_E_DEFAULT_ERROR,e);
		}
		
	}

	@Override
	public void addGridContent(PageGridContentDTO pageGridContentDTO) throws MainWebException{
		PageGridContentDO pageGridContentDO = new PageGridContentDO();
		try {
			 BeanUtils.copyProperties(pageGridContentDO, pageGridContentDTO);
			 logger.info(" ####### bofore pageGridContentDO:{} ",JsonUtil.toJson(pageGridContentDO));
			 pageGridContentDAO.addGridContent(pageGridContentDO);
			 logger.info(" ####### after pageGridContentDO:{} ",JsonUtil.toJson(pageGridContentDO));
			 pageGridContentDTO.setId(pageGridContentDO.getId());
		} catch (Exception e) {
			throw new MainWebException(ResponseCode.SYS_E_DEFAULT_ERROR, e);
		}
		
	}

	@Override
	public void addGridMergeCells(PageGridMergeCellsDTO pageGridMergeCellsDTO) throws MainWebException {		
		PageGridMergeCellsDO pageGridMergeCellsDO = new PageGridMergeCellsDO();
		try {
			BeanUtils.copyProperties(pageGridMergeCellsDO, pageGridMergeCellsDTO);
			pageGridMergeCellsDAO.addGridMergeCells(pageGridMergeCellsDO);
			pageGridMergeCellsDTO.setId(pageGridMergeCellsDO.getId());
		} catch (Exception e) {
			throw new MainWebException(ResponseCode.SYS_E_DEFAULT_ERROR, e);
		}
	}

	@Override
	public void deleteGrid(PageGridQTO pageGridQTO) throws MainWebException {
		try {
			pageGridQTO.setDeleteMark(1);
			logger.info(" @@@@@@@@@pageGridQTO : "+JSONObject.toJSONString(pageGridQTO));
			int result = pageGridDAO.updateGrid(pageGridQTO);
			/*if(result<1){
				throw new MainWebException(ResponseCode.SYS_E_DEFAULT_ERROR,"updateGrid : no result updated");
			}*/
		} catch (Exception e) {
			throw new MainWebException(ResponseCode.SYS_E_DEFAULT_ERROR, e);
		}
	}

	@Override
	public void deleteGridContent(PageGridQTO pageGridQTO)
			throws MainWebException {
		try {
			pageGridQTO.setDeleteMark(1);
			logger.info(" @@@@@@@@@pageGridQTO : "+JSONObject.toJSONString(pageGridQTO));
			int result = pageGridContentDAO.updateGridContent(pageGridQTO);
			/*if(result <1){
				throw new MainWebException(ResponseCode.SYS_E_DEFAULT_ERROR,"updateGridContent : no result updated");
			}*/
		} catch (Exception e) {
			throw new MainWebException(ResponseCode.SYS_E_DEFAULT_ERROR, e);
		}

	}

	@Override
	public void deleteGridMergeCells(PageGridQTO pageGridQTO)
			throws MainWebException {
		try {
			pageGridQTO.setDeleteMark(1);
			logger.info(" @@@@@@@@@pageGridQTO : "+JSONObject.toJSONString(pageGridQTO));
			int result = pageGridMergeCellsDAO.updateGridMergeCells(pageGridQTO);
			/*if(result < 1){
				throw new MainWebException(ResponseCode.SYS_E_DEFAULT_ERROR, "updateGridMergeCells : no result updated");
			}*/
		} catch (Exception e) {
			throw new MainWebException(ResponseCode.SYS_E_DEFAULT_ERROR, e);
		}
	}

	@Override
	public PageGridDTO getGrid(PageGridQTO pageGridQTO) throws MainWebException {
		try {
			PageGridDO pageGridDO =  pageGridDAO.getGrid(pageGridQTO);
			if(pageGridDO == null ){
				throw new MainWebException(ResponseCode.SYS_E_DEFAULT_ERROR.getCode(), "pageGridDAO.getGrid has no results");
			}
			PageGridDTO pageGridDTO = new PageGridDTO();
			BeanUtils.copyProperties(pageGridDTO, pageGridDO);
			return pageGridDTO;
		} catch (Exception e) {
			throw new MainWebException(ResponseCode.SYS_E_DEFAULT_ERROR, e);
		}
	}

	@Override
	public List<PageGridContentDTO> queryGridContent(PageGridQTO pageGridQTO)
			throws MainWebException {
		try {
			List<PageGridContentDO> resultGridContents = pageGridContentDAO.queryGridContent(pageGridQTO);
			if(CollectionUtils.isEmpty(resultGridContents)){
				throw new MainWebException(ResponseCode.SYS_E_DEFAULT_ERROR.getCode(), "pageGridContentDAO.queryGridContent has no results");
			}
			
			return ModelUtil.convertToPageGridContentDTOs(resultGridContents);
		} catch (Exception e) {
			throw new MainWebException(ResponseCode.SYS_E_DEFAULT_ERROR, e);
		}
	}

	@Override
	public List<PageGridMergeCellsDTO> queryGridMergeCells(
			PageGridQTO pageGridQTO) throws MainWebException {
		try {
			List<PageGridMergeCellsDO> pageGridMergeCellsDOs = pageGridMergeCellsDAO.queryGridMergeCells(pageGridQTO);
			logger.info(" $$$$$$$$$$$$ pageGridMergeCellsDOs : "+JSONObject.toJSONString(pageGridMergeCellsDOs));
			return ModelUtil.convertToPageGridMergeCellsDTOs(pageGridMergeCellsDOs);
		} catch (Exception e) {
			throw new MainWebException(ResponseCode.SYS_E_DEFAULT_ERROR, e);
		}		
	}

	
}
