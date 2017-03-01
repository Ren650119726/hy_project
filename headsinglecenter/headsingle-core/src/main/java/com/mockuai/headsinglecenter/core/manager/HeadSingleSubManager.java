package com.mockuai.headsinglecenter.core.manager;

import org.omg.CORBA.UserException;
import org.springframework.stereotype.Service;

import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleSubDTO;
import com.mockuai.headsinglecenter.core.exception.HeadSingleException;

/**
 * Created by csy 2016-07-13
 */
@Service
public interface HeadSingleSubManager {
	/**
     * 查询首单立减条件
     * 
     * @param CenterCopyWrittenDO
     * @return
     * @throws UserException
     */
    public HeadSingleSubDTO queryHeadSingleSub(Long id) throws HeadSingleException;
    
    /**
     * 新增首单立减条件
     * 
     * @param headSingleSubDTO
     * @return
     */
	public HeadSingleSubDTO addHeadSingleSub(HeadSingleSubDTO headSingleSubDTO) throws HeadSingleException;
	
	/**
	 * 修改首单立减条件
	 * 
	 * @param headSubDTO
	 * @return
	 * @throws HeadSingleException
	 */
	public HeadSingleSubDTO modifyHeadSingleSub(HeadSingleSubDTO headSingleSubDTO) throws HeadSingleException;
	
	/**
	 * 根据id查询首单立减条件
	 * 
	 * @param id
	 * @return
	 * @throws HeadSingleException
	 */
	public HeadSingleSubDTO queryHeadSingleSubById(Long id) throws HeadSingleException;
}
