package com.mockuai.headsinglecenter.core.manager.impl;

import javax.annotation.Resource;

import org.logicalcobwebs.concurrent.FJTask.Par;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.headsinglecenter.common.constant.ResponseCode;
import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleSubDTO;
import com.mockuai.headsinglecenter.core.dao.HeadSingleSubDAO;
import com.mockuai.headsinglecenter.core.domain.HeadSingleSubDO;
import com.mockuai.headsinglecenter.core.exception.HeadSingleException;
import com.mockuai.headsinglecenter.core.manager.HeadSingleSubManager;
import com.mockuai.headsinglecenter.core.util.HeadSingleUtils;


/**
 * Created by csy 2016-07-13
 */
@Service
public class HeadSingleSubManagerImpl implements HeadSingleSubManager {
    private static final Logger log = LoggerFactory.getLogger(HeadSingleSubManagerImpl.class);
    
    @Resource
    private HeadSingleSubDAO headSingleSubDAO;
    
    /**
     * 查询首单立减
     * 
     * @author csy
     * @Date 2016-07-17
     * 
     */
	@Override
	public HeadSingleSubDTO queryHeadSingleSub(Long id) throws HeadSingleException {		
		try {
			HeadSingleSubDO headSingleSubDO = headSingleSubDAO.queryHeadSingleSub(id);
			HeadSingleSubDTO headSingleSubDTO = null;
			
			if(null != headSingleSubDO){
				headSingleSubDTO = new HeadSingleSubDTO();
				HeadSingleUtils.copyProperties(headSingleSubDO, headSingleSubDTO);
			}
			
			if(null == headSingleSubDTO){
				return null;
			}
			
			return headSingleSubDTO;
		} catch (Exception e) {
			log.error("query head single sub error:"+e);
			throw new HeadSingleException(ResponseCode.QUERY_HEADSINGLE_ERROR);
		}
	}
	
	/**
	 * 新增首单立减信息
	 * 
	 * @author csy
	 * @throws HeadSingleException 
	 * @Date 2016-07-19
	 * 
	 */
	@Override
	public HeadSingleSubDTO addHeadSingleSub(HeadSingleSubDTO headSingleSubDTO) throws HeadSingleException {
		HeadSingleSubDO headSingleSubDO = null;
		HeadSingleSubDTO subDto = null;
		
		try {
			if(null == headSingleSubDTO){
				throw new HeadSingleException(ResponseCode.ADD_HEADSINGLE_NULL);
			}
			
			//优惠金额：正整数，当选择限满时不大于限满金额
			if(headSingleSubDTO.getPrivilegeAmt() <= 0){
				throw new HeadSingleException(ResponseCode.HEADSINGLE_PRIVILEGE_AMT_ERROR);
			}
			
			if(1 == headSingleSubDTO.getSubType()){
				if(headSingleSubDTO.getPrivilegeAmt() > headSingleSubDTO.getLimitFullAmt()){
					throw new HeadSingleException(ResponseCode.HEADSINGLE_PRIVILIMIT_AMT_ERROR);
				}
			}	
			
			//把dto转换为do
			headSingleSubDO = new HeadSingleSubDO();
			HeadSingleUtils.copyProperties(headSingleSubDTO, headSingleSubDO);
			
			//添加之前逻辑删除首单条件
			Integer updateDeleteMark = headSingleSubDAO.updateHeadSingleDeleteMark();
			
			if(null == updateDeleteMark){
				throw new HeadSingleException(ResponseCode.ADD_HEADSINGLE_ERROR);
			}
			
			//保存首单立减条件
			Long subId = headSingleSubDAO.addHeadSingleSub(headSingleSubDO);
			
			if(0 >= subId || null == subId){
				throw new HeadSingleException(ResponseCode.ADD_HEADSINGLE_ERROR);
			}
			
			//根据返回的id查询首单立减信息
			subDto = queryHeadSingleSub(subId);
			
			if(null == subDto){
				throw new HeadSingleException(ResponseCode.ADD_HEADSINGLE_ERROR);
			}
			
			return subDto;
		} catch (Exception e) {
			log.error("add head single sub error:"+e);
			throw new HeadSingleException(ResponseCode.ADD_HEADSINGLE_ERROR);
		}		
	}
	
	/**
	 * 修改首单立减信息
	 * 
	 * @author csy
	 * @throws HeadSingleException 
	 * @Date 2016-07-19
	 * 
	 */
	@Override
	public HeadSingleSubDTO modifyHeadSingleSub(HeadSingleSubDTO headSingleSubDTO) throws HeadSingleException {
		HeadSingleSubDO headSingleSubDO = null;	
		
		try {
			if(null == headSingleSubDTO){
				throw new HeadSingleException(ResponseCode.MODIFY_HEADSINGLE_NULL);
			}
			
			if(null == headSingleSubDTO.getId()){
				throw new HeadSingleException(ResponseCode.MODIFY_HEADSINGLE_NULL,"修改首单立减id参数为空");
			}
			
			//优惠金额：正整数，当选择限满时不大于限满金额
			if(headSingleSubDTO.getPrivilegeAmt() <= 0){
				throw new HeadSingleException(ResponseCode.HEADSINGLE_PRIVILEGE_AMT_ERROR);
			}
			
			if(1 == headSingleSubDTO.getSubType()){
				if(headSingleSubDTO.getPrivilegeAmt() > headSingleSubDTO.getLimitFullAmt()){
					throw new HeadSingleException(ResponseCode.HEADSINGLE_PRIVILIMIT_AMT_ERROR);
				}
			}	
			
			//把dto转换为do
			headSingleSubDO = new HeadSingleSubDO();
			HeadSingleUtils.copyProperties(headSingleSubDTO, headSingleSubDO);
			
			//保存首单立减条件
			int result = headSingleSubDAO.modifyHeadSingleSub(headSingleSubDO);
			
			if (result != 1) {
				throw new HeadSingleException(ResponseCode.MODIFY_HEADSINGLE_ERROR);
			}
			
			//根据返回的id查询首单立减信息
			HeadSingleSubDTO subDto = queryHeadSingleSub(headSingleSubDTO.getId());
			
			if(null == subDto){
				throw new HeadSingleException(ResponseCode.MODIFY_HEADSINGLE_ERROR);
			}
			
			return subDto;
		} catch (Exception e) {
			log.error("add head single sub error:"+e);
			throw new HeadSingleException(ResponseCode.MODIFY_HEADSINGLE_ERROR);
		}
	}
	
	/**
	 * 根据id查询首单立减条件
	 * 
	 * @author csy
	 * @Date 2016-07-27
	 */
	@Override
	public HeadSingleSubDTO queryHeadSingleSubById(Long id)	throws HeadSingleException {
		try {
			if(null == id){
				throw new HeadSingleException(ResponseCode.PARAMETER_NULL);
			}
			
			HeadSingleSubDO headSingleSubDO = headSingleSubDAO.queryHeadSingleSubById(id);
			HeadSingleSubDTO headSingleSubDTO = null;
			
			if(null != headSingleSubDO){
				headSingleSubDTO = new HeadSingleSubDTO();
				HeadSingleUtils.copyProperties(headSingleSubDO, headSingleSubDTO);
			}
			
			if(null == headSingleSubDTO){
				throw new HeadSingleException(ResponseCode.QUERY_HEADSINGLE_ERROR);
			}
			
			return headSingleSubDTO;
		} catch (Exception e) {
			log.error("query head single sub error:"+e);
			throw new HeadSingleException(ResponseCode.QUERY_HEADSINGLE_ERROR);
		}
	}    
}