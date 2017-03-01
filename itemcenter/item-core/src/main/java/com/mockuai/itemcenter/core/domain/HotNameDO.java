package com.mockuai.itemcenter.core.domain;

import java.util.Date;

/**
 *   
 * @author huangsiqian
 * @version 2016年9月19日 下午12:10:47 
 */
public class HotNameDO {
	private Long id;

	private String hotName;

	private String hotType;

	private Long indexSort;
	
	private Long clickVolume;


	private Date gmtCreated;

	private Date gmtModified;

	private Integer deleteMark;

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

	

	public Long getClickVolume() {
		return clickVolume;
	}

	public void setClickVolume(Long clickVolume) {
		this.clickVolume = clickVolume;
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
