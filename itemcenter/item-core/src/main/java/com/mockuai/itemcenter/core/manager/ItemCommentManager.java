package com.mockuai.itemcenter.core.manager;

import java.util.List;
import java.util.Map;

import com.mockuai.itemcenter.common.domain.dto.CountCommentDTO;
import com.mockuai.itemcenter.core.domain.ItemCommentDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCommentQTO;

@Service
public interface ItemCommentManager {

	/**
	 * 添加商品评论
	 * 
	 * @param itemCommentDO
	 * @return
	 * @throws com.mockuai.itemcenter.core.exception.ItemException
	 */
	public Long addItemComment(ItemCommentDO itemCommentDO) throws ItemException;

	/**
	 * 添加商品评论
	 *
	 * @param itemCommentDTO
	 * @return
	 * @throws com.mockuai.itemcenter.core.exception.ItemException
	 */
	public Boolean updateItemComment(ItemCommentDTO itemCommentDTO) throws ItemException;

	/**
	 * 查看商品评论
	 * 
	 * @param sellerId
	 * @param id
	 * @return
	 * @throws ItemException
	 */
	public ItemCommentDTO getItemComment(Long id, Long sellerId) throws ItemException;

	/**
	 * 获取商品评分等级
	 */
	public List<ItemCommentDTO> queryItemCommentGrade(ItemCommentQTO itemCommentDTO) throws ItemException;

	/**
	 * 分类统计所有商品评价等级
	 */
	public CountCommentDTO countItemCommentGrade(ItemCommentQTO itemCommentQTO) throws ItemException;

	/**
	 * 删除商品评论
	 * 
	 * @param sellerId
	 * @param id
	 * @return
	 * @throws ItemException
	 */
	public boolean deleteItemComment(Long id, Long sellerId) throws ItemException;

	/**
	 * 根据商品ID查询所有的评论
	 * 
	 * @param itemCommentQTO
	 * @return
	 * @throws ItemException
	 */
	public List<ItemCommentDO> queryItemComment(ItemCommentQTO itemCommentQTO) throws ItemException;


	public Long countItemComment(ItemCommentQTO itemCommentQTO);


	/**
	 * 将一条评论物理删除
	 * @param id
	 * @param sellerId
	 * @return
	 */
	boolean deleteItemCommentPhysically(Long id, Long sellerId) throws ItemException;
}
