package com.mockuai.itemcenter.core.dao;

import java.util.List;

import com.mockuai.itemcenter.core.domain.GlobalPropertyDO;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.qto.GlobalPropertyQTO;

@Service
public interface GlobalPropertyDAO {

	/**
	 * 增加全局属性
	 * 
	 * @param globalPropertyDO
	 * @return
	 */
	Long addGlobalProperty(GlobalPropertyDO globalPropertyDO);

	/**
	 * 根据id获取全局属性
	 * 
	 * @param id
	 * @return
	 */
	GlobalPropertyDO getGlobalProperty(Long id);
	
	/**
	 * 根据id删除全局属性
	 * 
	 * @param id
	 * @return
	 */
	int deleteGlobalProperty(Long id);

	/**
	 * 更新全局属性信息
	 * 
	 * @param globalPropertyDO
	 * @return
	 */
	int updateGlobalProperty(GlobalPropertyDO globalPropertyDO);

	/**
	 * 返回全局属性列表
	 * 
	 * @param globalPropertyQTO
	 * @return
	 */
	List<GlobalPropertyDO> queryGlobalProperty(GlobalPropertyQTO globalPropertyQTO);

}