package com.mockuai.distributioncenter.common.domain.qto;

import java.io.Serializable;

/**
 * Created by duke on 15/10/28.
 */
public abstract class BaseQTO implements Serializable {
    private Long offset;
    private Integer count;
    private Long totalCount;
    
    
    

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
