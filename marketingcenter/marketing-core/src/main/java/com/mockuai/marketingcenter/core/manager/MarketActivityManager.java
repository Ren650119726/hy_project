package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.common.constant.ActivityStatus;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.qto.MarketActivityQTO;
import com.mockuai.marketingcenter.core.domain.MarketActivityDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

import java.util.List;

public interface MarketActivityManager {

	/**
	 * 新增活动主体
	 *
	 * @param marketActivityDTO
	 * @return
	 * @throws MarketingException
	 */
	void addActivity(MarketActivityDTO marketActivityDTO) throws MarketingException;

	/**
	 * 新增优惠活动
	 *
	 * @param marketActivityDO
	 * @return
	 * @throws MarketingException
	 */
	Long addActivity(MarketActivityDO marketActivityDO) throws MarketingException;

	/**
	 * 查询商家下所有正在进行的合法活动
	 * 目前只支持 shop/item 级别的活动查询
	 *
	 * @param marketActivityQTO
	 * @return
	 */
	List<MarketActivityDO> queryActivityForSeller(MarketActivityQTO marketActivityQTO) throws MarketingException;

	/**
	 * 更新指定优惠活动
	 *
	 * @param marketActivityDO
	 * @return
	 * @throws MarketingException
	 */
	int updateActivity(MarketActivityDO marketActivityDO) throws MarketingException;

	/**
	 * 更新优惠活动状态
	 *
	 * @param id
	 * @param activityStatus
	 * @param bizCode
	 * @return
	 * @throws MarketingException
	 */
	void updateActivityStatus(Long id, ActivityStatus activityStatus, String bizCode) throws MarketingException;

	List<MarketActivityDO> queryActivity(MarketActivityQTO marketActivityQTO) throws MarketingException;

	/**
	 * 根据结算商品查询粗粒度查询优惠活动
	 *
	 * @param marketActivityQTO
	 * @return
	 * @throws MarketingException
	 */
	List<MarketActivityDO> queryActivityForSettlement(MarketActivityQTO marketActivityQTO) throws MarketingException;

	int countOfQueryActivityForSettlement(MarketActivityQTO marketActivityQTO) throws MarketingException;

	/**
	 * 与指定时间重合的合法满减送列表
	 *
	 * @param marketActivityDO
	 * @return
	 */
	List<MarketActivityDO> overlappingByTimeActivity(MarketActivityDO marketActivityDO) throws MarketingException;

	/**
	 * 查询指定 id 活动主体
	 *
	 * @param id
	 * @param bizCode
	 * @return
	 * @throws MarketingException
	 */
	MarketActivityDO getActivity(Long id, String bizCode) throws MarketingException;

	int queryActivityCount(MarketActivityQTO marketActivityQTO) throws MarketingException;

	/**
	 * 关联优惠子活动
	 * 只支持同商家活动关联
	 *
	 * @param marketActivityDTOs
	 */
	void linkSubMarketActivity(List<MarketActivityDTO> marketActivityDTOs) throws MarketingException;
}