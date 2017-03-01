package com.mockuai.distributioncenter.common.domain.dto;

import java.io.Serializable;

/**
 * Created by duke on 16/5/19.
 */
public class TeamSummaryDTO implements Serializable {
    private Long levelOneCount;
    private Long levelTwoCount;
    private Long levelThreeCount;
    private Long totalCount;

    public Long getLevelOneCount() {
        return levelOneCount;
    }

    public void setLevelOneCount(Long levelOneCount) {
        this.levelOneCount = levelOneCount;
    }

    public Long getLevelTwoCount() {
        return levelTwoCount;
    }

    public void setLevelTwoCount(Long levelTwoCount) {
        this.levelTwoCount = levelTwoCount;
    }

    public Long getLevelThreeCount() {
        return levelThreeCount;
    }

    public void setLevelThreeCount(Long levelThreeCount) {
        this.levelThreeCount = levelThreeCount;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
