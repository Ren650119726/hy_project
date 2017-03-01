package com.mockuai.seckillcenter.core.manager;

import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;
import com.mockuai.seckillcenter.common.domain.qto.SeckillQTO;
import com.mockuai.seckillcenter.core.domain.SeckillDO;
import com.mockuai.seckillcenter.core.exception.SeckillException;

import java.util.List;

/**
 * Created by edgar.zr on 12/4/15.
 */
public interface SeckillManager {

    /**
     * 创建 秒杀
     *
     * @param seckillDO
     * @throws SeckillException
     */
    Long addSeckill(SeckillDO seckillDO) throws SeckillException;

    /**
     * @param seckillDO
     * @return
     * @throws SeckillException
     */
    SeckillDTO getSeckill(SeckillDO seckillDO) throws SeckillException;

    /**
     * 更新 seckill
     *
     * @param seckillDO
     * @return
     * @throws SeckillException
     */
    int updateSeckill(SeckillDO seckillDO) throws SeckillException;

    /**
     * 使秒杀失效
     *
     * @param seckillDO
     * @throws SeckillException
     */
    void invalidSeckill(SeckillDO seckillDO) throws SeckillException;

    /**
     * 查询秒杀
     *
     * @param seckillQTO
     * @return
     * @throws SeckillException
     */
    List<SeckillDTO> querySeckill(SeckillQTO seckillQTO) throws SeckillException;

    /**
     * 查询秒杀，不封装
     *
     * @param seckillQTO
     * @return
     * @throws SeckillException
     */
    List<SeckillDO> querySeckillSimple(SeckillQTO seckillQTO) throws SeckillException;

    /**
     * 根据 itemId 查询相关活动
     *
     * @param itemId
     * @param bizCode
     * @return
     * @throws SeckillException
     */
//    SeckillDTO getSeckillByItemId(Long itemId, String bizCode) throws SeckillException;

    /**
     * 填充活动的 lifecycle
     *
     * @param seckillDTOs
     * @throws SeckillException
     */
    void fillUpSeckillDTO(List<SeckillDTO> seckillDTOs) throws SeckillException;
}