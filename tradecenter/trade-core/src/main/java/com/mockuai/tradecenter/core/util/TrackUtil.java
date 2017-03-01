package com.mockuai.tradecenter.core.util;

import java.util.Map;

public class TrackUtil {
	private Map<String, String> trackInfoMap;

	private static TrackUtil trackUtil;

	public void init() {
		trackUtil = this;
		trackUtil.trackInfoMap = this.trackInfoMap;
	}
	
	public String getTrackInfo(String orderStatus){
		String trackInfo = trackInfoMap.get(orderStatus);
		return trackInfo;
	}

	public Map<String, String> getTrackInfoMap() {
		return trackInfoMap;
	}

	public void setTrackInfoMap(Map<String, String> trackInfoMap) {
		this.trackInfoMap = trackInfoMap;
	}
	
	
	

}
