package com.mockuai.mainweb.core.manager;

import java.util.List;

import com.mockuai.mainweb.common.domain.dto.PageGridContentDTO;
import com.mockuai.mainweb.common.domain.dto.PageGridDTO;
import com.mockuai.mainweb.common.domain.dto.PageGridElementDTO;
import com.mockuai.mainweb.common.domain.dto.PageGridMergeCellsDTO;
import com.mockuai.mainweb.common.domain.qto.PageGridQTO;
import com.mockuai.mainweb.core.domain.PageGridDO;
import com.mockuai.mainweb.core.exception.MainWebException;

public interface PageGridManager {

	void addGrid(PageGridElementDTO pageGridElementDTO) throws MainWebException;
	
	void addGridContent(PageGridContentDTO pageGridContentDTO) throws MainWebException;
	
	void addGridMergeCells(PageGridMergeCellsDTO pageGridMergeCellsDTO) throws MainWebException;
	
	void deleteGrid(PageGridQTO pageGridQTO) throws MainWebException;
	
	void deleteGridContent(PageGridQTO pageGridQTO) throws MainWebException;
	
	void deleteGridMergeCells(PageGridQTO pageGridQTO) throws MainWebException;
	
	PageGridDTO getGrid(PageGridQTO pageGridQTO) throws MainWebException;
	
	List<PageGridContentDTO> queryGridContent(PageGridQTO pageGridQTO) throws MainWebException;
	
	List<PageGridMergeCellsDTO> queryGridMergeCells(PageGridQTO pageGridQTO) throws MainWebException;
	
}
