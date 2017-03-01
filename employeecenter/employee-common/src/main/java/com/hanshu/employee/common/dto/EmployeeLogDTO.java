package com.hanshu.employee.common.dto;

import com.hanshu.employee.common.constant.OpActionEnum;
import com.hanshu.employee.common.constant.OpObjTypeEnum;

import java.util.Date;
import java.util.List;

/**
 * Created by yeliming on 16/5/19.
 */
public class EmployeeLogDTO extends BaseDTO {
    private Long id;
    private Long operatorId;
    private String operator;
    private Integer opAction;
    private String opActionStr;
    private Integer objType;
    private String objTypeStr;
    private Long objId;
    private String objName;
    private Integer opResult;
    private String operateJson;
    private String ipAddr;
    private String comments;
    private Date gmtCreated;
    private Date gmtModified;
    private Integer deleteMark;

    private List<Long> ObjIdList;
    private List<String> objNameList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getOpAction() {
        return opAction;
    }

    public void setOpAction(Integer opAction) {
        this.opAction = opAction;
    }

    public String getOpActionStr() {
        return opActionStr;
    }

    public void setOpActionStr(String opActionStr) {
        this.opActionStr = opActionStr;
    }

    public Integer getObjType() {
        return objType;
    }

    public void setObjType(Integer objType) {
        this.objType = objType;
    }

    public String getObjTypeStr() {
        return objTypeStr;
    }

    public void setObjTypeStr(String objTypeStr) {
        this.objTypeStr = objTypeStr;
    }

    public Long getObjId() {
        return objId;
    }

    public void setObjId(Long objId) {
        this.objId = objId;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public Integer getOpResult() {
        return opResult;
    }

    public void setOpResult(Integer opResult) {
        this.opResult = opResult;
    }

    public String getOperateJson() {
        return operateJson;
    }

    public void setOperateJson(String operateJson) {
        this.operateJson = operateJson;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public List<Long> getObjIdList() {
        return ObjIdList;
    }

    public void setObjIdList(List<Long> objIdList) {
        ObjIdList = objIdList;
    }

    public List<String> getObjNameList() {
        return objNameList;
    }

    public void setObjNameList(List<String> objNameList) {
        this.objNameList = objNameList;
    }
}
