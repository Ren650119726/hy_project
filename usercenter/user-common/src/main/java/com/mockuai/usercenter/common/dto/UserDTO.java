package com.mockuai.usercenter.common.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserDTO extends BaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
    private String bizCode;
    /**
     * 1正常注册的用户，2来自第三方开放平台的用户，3通过数据迁移导入的老用户记录
     */
    private Integer type;
    private String name;
    private String password;
    private String imgUrl;
    private Long experience;
    private String phone;
    private String mobile;
    private String email;
    private Integer status;
    private Long roleMark;
    private Long inviterId;
    private String invitationCode;
    private Integer invitationSource;
    private Integer deleteMark;
    private Date gmtCreated;
    /**
     * 注册来源
     * APP_IOS(1, "ios客户端"),
     * APP_ANDROID(2, "android客户端"),
     * APP_WAP(3, "h5站点"),
     * APP_PC_MALL(4, "pc商城"),
     * APP_SELLER_MANAGER(11, "店铺管理后台"),
     * APP_BOSS_MANAGER(12, "一站式商城管理后台"),
     * APP_OUT_SYSTEM(101, "外部系统");
     */
    private Integer sourceType;
    private List<Long> idList;
    private String payPassword;
	private String nickName;
    private Integer sex;
	private Date birthday;
	private Long lastDistributorId;
	private String wechat;
	private String qqCode;
    private Long fansCount;
    private Integer authonStatus;
    private String pictureFront;
    private String pictureBack;

    public Integer getAuthonStatus() {
        return authonStatus;
    }

    public void setAuthonStatus(Integer authonStatus) {
        this.authonStatus = authonStatus;
    }

    public String getPictureFront() {
        return pictureFront;
    }

    public void setPictureFront(String pictureFront) {
        this.pictureFront = pictureFront;
    }

    public String getPictureBack() {
        return pictureBack;
    }

    public void setPictureBack(String pictureBack) {
        this.pictureBack = pictureBack;
    }

    public Integer getInvitationSource() {
        return invitationSource;
    }

    public void setInvitationSource(Integer invitationSource) {
        this.invitationSource = invitationSource;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Long getExperience() {
        return experience;
    }

    public void setExperience(Long experience) {
        this.experience = experience;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getRoleMark() {
        return roleMark;
    }

    public void setRoleMark(Long roleMark) {
        this.roleMark = roleMark;
    }

    public Long getInviterId() {
        return inviterId;
    }

    public void setInviterId(Long inviterId) {
        this.inviterId = inviterId;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
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

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Long getLastDistributorId() {
		return lastDistributorId;
	}

	public void setLastDistributorId(Long lastDistributorId) {
		this.lastDistributorId = lastDistributorId;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getQqCode() {
		return qqCode;
	}

	public void setQqCode(String qqCode) {
		this.qqCode = qqCode;
	}

	public Long getFansCount() {
		return fansCount;
	}

	public void setFansCount(Long fansCount) {
		this.fansCount = fansCount;
	}
}
