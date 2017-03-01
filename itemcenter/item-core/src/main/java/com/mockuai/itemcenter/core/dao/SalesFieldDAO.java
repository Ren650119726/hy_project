package com.mockuai.itemcenter.core.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.qto.SalesFieldQTO;
import com.mockuai.itemcenter.core.domain.SalesFieldDO;

@Service
public interface SalesFieldDAO {

	/**
	 * 增加专场
	 * 
	 * @param salesFieldDO
	 * @return
	 */
	Integer addSalesField(SalesFieldDO salesFieldDO);

	/**
	 * 根据id专场信息
	 * 
	 * @param id
	 * @return
	 */
	SalesFieldDO getSalesField(Integer id);

	/**
	 * 根据id删除专场信息
	 * 
	 * @param id
	 * @return
	 */
	int deleteSalesField(Integer id);

	/**
	 * 更新专场信息
	 * 
	 * @param salesFieldDO
	 * @return
	 */
	int updateSalesField(SalesFieldDO salesFieldDO);

	/**
	 * 返回专场列表
	 * 
	 * @param salesFieldQTO
	 * @return
	 */
	List<SalesFieldDO> querySalesField(SalesFieldQTO salesFieldQTO);

}