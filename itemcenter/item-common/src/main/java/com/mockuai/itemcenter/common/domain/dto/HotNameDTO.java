package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *   
 * @author huangsiqian
 * @version 2016年9月19日 上午11:47:22 
 */
public class HotNameDTO implements Serializable {
	private Long id;

	private String hotName;

	private String hotType;

	private Long indexSort;
	
	private Long clickVolume;


	private Date gmtCreated;

	private Date gmtModified;

	private Integer deleteMark;
	

	public Long getClickVolume() {
		return clickVolume;
	}

	public void setClickVolume(Long clickVolume) {
		this.clickVolume = clickVolume;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHotName() {
		return hotName;
	}

	public void setHotName(String hotName) {
		this.hotName = hotName;
	}

	public String getHotType() {
		return hotType;
	}

	public void setHotType(String hotType) {
		this.hotType = hotType;
	}

	public Long getIndexSort() {
		return indexSort;
	}

	public void setIndexSort(Long indexSort) {
		this.indexSort = indexSort;
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


	public Integer getDeleteMark() {
		return deleteMark;
	}

	public void setDeleteMark(Integer deleteMark) {
		this.deleteMark = deleteMark;
	}

	
	
}
