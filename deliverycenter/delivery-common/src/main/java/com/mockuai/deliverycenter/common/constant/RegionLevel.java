package com.mockuai.deliverycenter.common.constant;

public enum RegionLevel {
	/**
	 * 国
	 */
	COUNTRY(1),
	/**
	 * 省
	 */
	PROVINCE(2),
	/**
	 * 市
	 */

	CITY(3),
	/**
	 * 区，县
	 */
	AREA(4),
	/**
	 * 乡，镇
	 */
	TOWN(5),
	/**
	 * 村
	 */
	VILLAGE(6);

	private Integer value;

	private RegionLevel(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}
