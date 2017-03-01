package com.mockuai.distributioncenter.core.domain;

import java.util.Date;

/**
 * Created by yeliming on 16/5/18.
 */
public class SellerConfigDO {
    private Long id;
    private Integer level;
    private String levelName;
    private Integer underCount;
    private Integer teamCount;
    private Date gmtCreated;
    private Date gmtModified;
    private Integer deleteMark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Integer getUnderCount() {
        return underCount;
    }

    public void setUnderCount(Integer underCount) {
        this.underCount = underCount;
    }

    public Integer getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(Integer teamCount) {
        this.teamCount = teamCount;
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
