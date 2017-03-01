package com.mockuai.itemcenter.core.dao;

import java.util.List;

import com.mockuai.itemcenter.common.domain.dto.CountCommentDTO;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.qto.ItemCommentQTO;
import com.mockuai.itemcenter.core.domain.ItemCommentDO;

@Service
public interface ItemCommentDAO {

	/**
	 * 增加商品评论
	 * 
	 * @param itemCommentDO
	 * @return
	 */
	Long addItemComment(ItemCommentDO itemCommentDO);

	/**
	 * 获取商品评论
	 * 
	 * @param id
	 * @return
	 */
	ItemCommentDO getItemComment(Long id, Long sellerId);

	/**
	 * 获取商品评价等级
	 */
	List<ItemCommentDO> queryItemCommentGrade(ItemCommentQTO itemCommentQTO);

	/**
	 * 分类统计所有商品评价等级
	 */
	CountCommentDTO countItemCommentGrade(ItemCommentQTO itemCommentQTO);

	/**
	 * 更新商品评论
	 * 
	 * @param itemCommentDO
	 * @return
	 */
	int updateItemComment(ItemCommentDO itemCommentDO);

	/**
	 * 删除商品评论
	 * 
	 * @param sellerId
	 * @param id
	 * @return
	 */
	int deleteItemComment(Long id, Long sellerId);

	/**
	 * 根据商品ID查询所有的评论
	 * 
	 * @param itemCommentQTO
	 * @return
	 */
	List<ItemCommentDO> queryItemComment(ItemCommentQTO itemCommentQTO);

	Long countItemComment(ItemCommentQTO itemCommentQTO);

	/**
	 * 物理删除商品评论
	 *
	 * @param sellerId
	 * @param id
	 * @return
	 */
	int deleteItemCommentPhysically(Long id, Long sellerId);
}