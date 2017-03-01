package com.mockuai.headsinglecenter.core.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.headsinglecenter.common.constant.ResponseCode;
import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleSubDTO;
import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleUserDTO;
import com.mockuai.headsinglecenter.core.dao.HeadSingleSubDAO;
import com.mockuai.headsinglecenter.core.dao.HeadSingleUserDAO;
import com.mockuai.headsinglecenter.core.domain.HeadSingleSubDO;
import com.mockuai.headsinglecenter.core.domain.HeadSingleUserDO;
import com.mockuai.headsinglecenter.core.exception.HeadSingleException;
import com.mockuai.headsinglecenter.core.manager.AppManager;
import com.mockuai.headsinglecenter.core.manager.HeadSingleUserManager;
import com.mockuai.headsinglecenter.core.util.HeadSingleUtils;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;


/**
 * Created by csy 2016-07-13
 */
@Service
public class HeadSingleUserManagerImpl implements HeadSingleUserManager {
    private static final Logger log = LoggerFactory.getLogger(HeadSingleUserManagerImpl.class);
    @Resource
    private HeadSingleUserDAO headSingleUserDAO;
    @Resource
    private HeadSingleSubDAO headSingleSubDAO;
    @Resource
    private AppManager appManager;
    
    /**
     * 校验并查询用户是否符合首单立减条件
     * 
     * @author csy
     * @Date 2016-07-20 
     */
	@Override
	public HeadSingleSubDTO queryJudgeHeadSingleUser(Long userId, List<MarketItemDTO> marketItems, String appKey) throws HeadSingleException {
		try {
			if(null == userId){
				throw new HeadSingleException(ResponseCode.QUERY_HEADSINGLE_USER_NULL,"查询首单立减用户id不可为空");
			}
			
			if(null == marketItems || marketItems.isEmpty()){
				throw new HeadSingleException(ResponseCode.QUERY_HEADSINGLE_USER_NULL,"查询首单立减marketItems不可为空");
			}
			
			if(StringUtils.isBlank(appKey)){
				throw new HeadSingleException(ResponseCode.QUERY_HEADSINGLE_USER_NULL,"查询首单立减appKey不可为空");
			}
			
			//判断营销信息是否符合首单立减活动(20开店礼包13秒杀14团购)
			for(MarketItemDTO marketItemDTO:marketItems){
				if(marketItemDTO.getItemType() ==20 || marketItemDTO.getItemType() ==13	|| marketItemDTO.getItemType() == 14){
					//如果不符合活动立减返回null
					return null;
				}
			}
			
			//查询此用户是否已经享受过首单活动
			HeadSingleUserDO headSingleUserDO = headSingleUserDAO.queryJudgeHeadSingleUser(userId);
			
			//如果为空说明此用户符合活动立减信息
			if(null != headSingleUserDO){
				return null;
			}
			
			//查询首单立减条件
			HeadSingleSubDTO headSingleSubDTO = getHeadSingleSubDTO();
			
			//如果首单立减数据为空返回null
			if(null == headSingleSubDTO){
				return null;
			}
			
			//判断首单立减是否关闭(0开启1关闭)
			if(1 == headSingleSubDTO.getOpenStatus()){
				return null;
			}
			
			//判断首单立减条件里面活动平台是否包含
			Boolean typeFlag= judgeHeadSingleSubType(headSingleSubDTO, appKey);
			
			if(true != typeFlag){
				return null;
			}
			
			return headSingleSubDTO;
		} catch (Exception e) {
			log.error("query head single user error:"+e);
			throw new HeadSingleException(ResponseCode.QUERY_HEADSINGLE_USER_ERROR);
		}
	}
	
	/**
	 * 获取首单立减的活动信息
	 * 
	 * @author csy
	 * @Date 2016-07-21
	 * @return
	 */
	private HeadSingleSubDTO getHeadSingleSubDTO(){
		HeadSingleSubDO headSingleSubDO = headSingleSubDAO.queryHeadSingleSub(null);
		HeadSingleSubDTO headSingleSubDTO = null;
		
		if(null != headSingleSubDO){
			headSingleSubDTO = new HeadSingleSubDTO();
			HeadSingleUtils.copyProperties(headSingleSubDO, headSingleSubDTO);
		}
		
		return headSingleSubDTO;
	}
	
	/**
	 * 判断传入平台类型是否符合立减活动
	 * 
	 * @author csy
	 * @param headSingleSubDTO
	 * @param appKey
	 * @return
	 * @throws HeadSingleException
	 */
	private Boolean judgeHeadSingleSubType(HeadSingleSubDTO headSingleSubDTO,String appKey) throws HeadSingleException{
		//获取平台类型(1 ios/2 android/3 h5)
		Integer appType = appManager.getAppInfo(appKey).getAppType();
		
		//解析首单立减平台类型
		String[] terTypes = headSingleSubDTO.getActivityTerminalType().split(",");
		
		if(null == terTypes || 0 >= terTypes.length){
			return false;
		}
		
		//转化终端类型
		List<Integer> typeList = new ArrayList<Integer>();
		
		for(String type:terTypes){
			if("ios".equalsIgnoreCase(type)){//ios端
				typeList.add(1);
			}else if("android".equalsIgnoreCase(type)){//android端
				typeList.add(2);
			}else if("web".equalsIgnoreCase(type)){//web端
				typeList.add(3);
			}
		}
		
		if(null == typeList || typeList.isEmpty()){
			return false;
		}
		
		//如果list中存在此类型说明符合首单立减条件
		if(typeList.contains(appType)){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 新增或更新用户享受首单记录
	 * 
	 * @author csy
	 * @Date 2016-07-22
	 */
	@Override
	public Long addHeadSingleUser(HeadSingleUserDTO headSingleUserDTO) throws HeadSingleException {
		if(null == headSingleUserDTO){
			return null;
		}
		
		//查询此用户是否已经存在
		Long userId = headSingleUserDTO.getUserId();
		HeadSingleUserDO headSingleDo = headSingleUserDAO.queryJudgeHeadSingle(userId);
		
		//如果此用户已经存在执行更新
		if(null != headSingleDo){
			if(headSingleDo.getOrderCount() == 0){
				Map<Object, Object> map = new HashMap<Object, Object>();
				map.put("id", headSingleDo.getId());
				map.put("orderCount", 1);
				
				//更新为已享受首单用户
				Integer result = headSingleUserDAO.updateHeadSingleOrderCount(map);
				
				if(null == result || 0 == result){
					log.error("首单立减用户存在更新count时失败");
				}
				
				return null;
			}
			
			return null;
		}
		
		//dto转换为do
		headSingleDo = new HeadSingleUserDO();
		HeadSingleUtils.copyProperties(headSingleUserDTO, headSingleDo);
		
		//进行数据保存
		Long headSingleUserId = headSingleUserDAO.addHeadSingleUser(headSingleDo);
		
		return headSingleUserId;
	}
	
	/**
	 * 更新记录用户享受首单个数
	 * 
	 * @author csy
	 * @Date 2016-06-28
	 */
	@Override
	public Integer updateHeadSingleOrderCount(HeadSingleUserDTO headSingleUserDTO)	throws HeadSingleException {
		if(null == headSingleUserDTO){
			return null;
		}
		
		//查询用户立减数据
		Long userId = headSingleUserDTO.getUserId();
		HeadSingleUserDO headSingleDo = headSingleUserDAO.queryJudgeHeadSingleUser(userId);
		
		if(null == headSingleDo){			
			return null;
		}
		
		//更新用户立减数据
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("id", headSingleDo.getId());
		map.put("orderCount", 0);
		
		Integer result = headSingleUserDAO.updateHeadSingleOrderCount(map);
		
		if(null == result || 0 >= result){
			return null;
		}
			
		return result;
	}
}