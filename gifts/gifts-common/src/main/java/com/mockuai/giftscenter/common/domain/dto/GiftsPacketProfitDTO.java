package com.mockuai.giftscenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by edgar.zr on 12/4/15.
 */
public class GiftsPacketProfitDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
    private Long giftsPacketId;
    private Long levelId;
    private Long levelMoney;
    private Long levelScore;
    private Integer deleteMark;
    private Date gmtCreated;
    private Date gmtModified;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGiftsPacketId() {
		return giftsPacketId;
	}
	public void setGiftsPacketId(Long giftsPacketId) {
		this.giftsPacketId = giftsPacketId;
	}
	
	public Long getLevelId() {
		return levelId;
	}
	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}
	public Long getLevelMoney() {
		return levelMoney;
	}
	public void setLevelMoney(Long levelMoney) {
		this.levelMoney = levelMoney;
	}
	public Long getLevelScore() {
		return levelScore;
	}
	public void setLevelScore(Long levelScore) {
		this.levelScore = levelScore;
	}
	public Integer getDeleteMark() {
		return deleteMark;
	}
	public void setDeleteMark(Integer deleteMark) {
		this.deleteMark = deleteMark;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	
    
    
   
}