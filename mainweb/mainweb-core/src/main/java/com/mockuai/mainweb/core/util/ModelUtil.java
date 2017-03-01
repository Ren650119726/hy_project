package com.mockuai.mainweb.core.util;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.mainweb.common.domain.dto.PageGridContentDTO;
import com.mockuai.mainweb.common.domain.dto.PageGridMergeCellsDTO;
import com.mockuai.mainweb.core.domain.PageGridContentDO;
import com.mockuai.mainweb.core.domain.PageGridMergeCellsDO;

public class ModelUtil {

	private static final Logger logger = LoggerFactory.getLogger(ModelUtil.class);
	
	public static PageGridContentDTO convertToPageGridContentDTO(PageGridContentDO pageGridContentDO){
		if(pageGridContentDO == null){
			return null;
		}
		PageGridContentDTO pageGridContentDTO = new PageGridContentDTO();
		BeanUtils.copyProperties(pageGridContentDO, pageGridContentDTO);
		return pageGridContentDTO;
	}
	
	
	public static List<PageGridContentDTO> convertToPageGridContentDTOs(List<PageGridContentDO> pageGridContentDOs){		
		if(CollectionUtils.isEmpty(pageGridContentDOs)){
			return Collections.EMPTY_LIST;
		}
		List<PageGridContentDTO> pageGridContentDTOs = new ArrayList<PageGridContentDTO>();
		for(PageGridContentDO pageGridContentDO:pageGridContentDOs){
			pageGridContentDTOs.add(convertToPageGridContentDTO(pageGridContentDO));
		}
		
		return pageGridContentDTOs;
	}
	
	public static List<PageGridMergeCellsDTO> convertToPageGridMergeCellsDTOs(List<PageGridMergeCellsDO> PageGridMergeCellsDOs){
		if(CollectionUtils.isEmpty(PageGridMergeCellsDOs)){
			return Collections.EMPTY_LIST;
		}
		List<PageGridMergeCellsDTO> pageGridMergeCellsDTOs = new ArrayList<PageGridMergeCellsDTO>();
		for(PageGridMergeCellsDO pageGridMergeCellsDO:PageGridMergeCellsDOs){
			pageGridMergeCellsDTOs.add(convertToPageGridMergeCellsDTO(pageGridMergeCellsDO));
		}
		return pageGridMergeCellsDTOs;
	}
	
	public static PageGridMergeCellsDTO convertToPageGridMergeCellsDTO(PageGridMergeCellsDO pageGridMergeCellsDO){
		if(pageGridMergeCellsDO==null){
			return null;
		}
		PageGridMergeCellsDTO pageGridMergeCellsDTO = new PageGridMergeCellsDTO();
		BeanUtils.copyProperties(pageGridMergeCellsDO, pageGridMergeCellsDTO);
		logger.info(" $$$$$$$$ pageGridMergeCellsDTO : "+JSONObject.toJSONString(pageGridMergeCellsDTO));
		return pageGridMergeCellsDTO;
	}
}
