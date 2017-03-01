package com.mockuai.usercenter.core.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.qto.UserQTO;
import com.mockuai.usercenter.core.dao.UserDAO;
import com.mockuai.usercenter.core.domain.UserDO;

@Service
public class UserDAOImpl extends SqlMapClientDaoSupport implements UserDAO {

    public Long addUser(UserDO userDo) {
        Long userId = (Long) getSqlMapClientTemplate().insert("user.insert", userDo);
        return userId;
    }


    public int updatePwd(Long userId, String newPwd) {
        UserDO key = new UserDO();
        key.setId(userId);
        key.setPassword(newPwd);
        int result = getSqlMapClientTemplate().update("user.updatePwd", key);
        return result;
    }


    public int updateEmail(Long userId, String email) {
        // TODO Auto-generated method stub
        UserDO key = new UserDO();
        key.setId(userId);
        key.setEmail(email);
        int result = getSqlMapClientTemplate().update("user.updateEmail", key);
        return result;
    }


    public int updateMobile(Long userId, String mobile) {
        UserDO userDO = new UserDO();
        userDO.setId(userId);
        userDO.setMobile(mobile);
        int result = getSqlMapClientTemplate().update("user.updateMobile", userDO);
        return result;
    }


    public int activeUser(Long id) {
        // TODO Auto-generated method stub
        int result = getSqlMapClientTemplate().update("user.activeUser", id);
        return result;
    }


    public UserDO getUserByName(Map map) {
        UserDO userDo = (UserDO) getSqlMapClientTemplate().queryForObject("user.selectByName", map);
        return userDo;
    }


    public UserDO getUserByEmail(Map map) {
        // TODO Auto-generated method stub
        UserDO userDo = (UserDO) getSqlMapClientTemplate().queryForObject(
                "user.selectByEmail", map);
        return userDo;
    }


    public UserDO getUserByMobile(Map map) {
        UserDO userDo = (UserDO) getSqlMapClientTemplate().queryForObject("user.selectByMobile", map);
        return userDo;
    }


    public UserDO getUserById(Long id) {
        // TODO Auto-generated method stub
        UserDO userDo = (UserDO) getSqlMapClientTemplate().queryForObject(
                "user.selectById", id);
        return userDo;
    }


    public int freezeUser(Long id) {
        // TODO Auto-generated method stub
        int result = getSqlMapClientTemplate().update("user.freezeUser", id);
        return result;
    }

    @Override
    public int thawUser(Long userId) {
        int result = this.getSqlMapClientTemplate().update("user.tharUser", userId);
        return result;
    }


    public int moveToRecycle(Long id) {
        int result = getSqlMapClientTemplate().update("user.moveToRecycle", id);
        return result;
    }


    public int deleteUser(Long id) {

        int result = getSqlMapClientTemplate().update("user.delete", id);
        return result;
    }


    @SuppressWarnings("unchecked")
	public List<UserDO> queryUser(UserQTO userQto) {

        List<UserDO> users = getSqlMapClientTemplate().queryForList("user.queryUser", userQto);

        return users;
    }


    @SuppressWarnings("unchecked")
	public List<UserDO> queryNormalAndOldUser(UserQTO userQTO) {
        List<UserDO> users = getSqlMapClientTemplate().queryForList("user.queryNormalAndOldUser", userQTO);
        return users;
    }


    @SuppressWarnings("unchecked")
	public List<UserDO> queryUserByDevice(UserQTO userQTO) {
        List<UserDO> users = getSqlMapClientTemplate().queryForList("user.queryUserByDevice", userQTO);
        return users;
    }


    public int setUserRoleMark(Long userId, Long roleMark) {
        //TODO 这里的roleMark的使用方式需要重构，重构成二进制打标的方式
        UserDO userDO = new UserDO();
        userDO.setId(userId);
        userDO.setRoleMark(roleMark);
        int result = getSqlMapClientTemplate().update("user.setRole", userDO);
        return result;
    }


    public Long getTotalCount(UserQTO userQTO) {

        Long totalCount = (Long) getSqlMapClientTemplate().queryForObject(
                "user.totalCount", userQTO);
        return totalCount;
    }


    public Long getNormalAndOldTotalCount(UserDO userDO) {
        Long totalCount = (Long) getSqlMapClientTemplate().queryForObject(
                "user.normalAndOldTotalCount", userDO);
        return totalCount;
    }


    public int restoreUser(Long id) {
        // TODO Auto-generated method stub
        int result = getSqlMapClientTemplate().update("user.restore", id);
        return result;
    }


    public UserDO getRecycleUser(Long id) {
        // TODO Auto-generated method stub
        UserDO userDo = (UserDO) getSqlMapClientTemplate().queryForObject(
                "user.recycle", id);
        return userDo;
    }


    public UserDO userLogin(String loginName, String loginPwd) {
        UserDO userDo = new UserDO();
        userDo.setName(loginName);
        userDo.setPassword(loginPwd);
        
        return (UserDO) getSqlMapClientTemplate().queryForObject("user.login", userDo);
    }


    public int updateName(Long userId, String name) {
        // TODO Auto-generated method stub
        UserDO userDo = new UserDO();
        userDo.setId(userId);
        userDo.setName(name);
        int result = getSqlMapClientTemplate()
                .update("user.updateName", userDo);
        return result;
    }


    public int updateUser(UserDO userDo) {
        // TODO Auto-generated method stub
        int result = getSqlMapClientTemplate().update("user.updateUser", userDo);
        return result;
    }


    public int updateHeadImg(Long userId, String imgUrl) {
        UserDO key = new UserDO();
        key.setId(userId);
        key.setImgUrl(imgUrl);
        int result = getSqlMapClientTemplate().update("user.updateHeadImg", key);
        return result;
    }

    public UserDO getByLoginName(String loginName) {
        UserQTO qto = new UserQTO();
        
        qto.setName(loginName);        
        UserDO userDo = (UserDO) getSqlMapClientTemplate().queryForObject("user.selectByLoginName", qto);
        
        return userDo;
    }


    public UserDO getByInvitationCode(String invitationCode) {
    	UserDO userDO = new UserDO();
    	userDO.setInvitationCode(invitationCode);
        UserDO userDo = (UserDO) getSqlMapClientTemplate().queryForObject(
                "user.selectByInvitationCode", userDO);
        return userDo;
    }


    public int updateInvitationCode(Long userId, String invitationCode) {
        UserDO key = new UserDO();
        key.setId(userId);
        key.setInvitationCode(invitationCode);
        try {
            return getSqlMapClientTemplate().update("user.updateInvitationCode", key);
        } catch (DataAccessException e) {
            return -1;
        }
    }


    public List<UserDO> queryFromIdList(List idList) {
        return getSqlMapClientTemplate().queryForList("user.queryFromIdList", idList);
    }


    public List<UserDO> queryByMobiles(List mobiles, String bizCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mobiles", mobiles);
        map.put("bizCode", bizCode);
        return getSqlMapClientTemplate().queryForList("user.queryByMobiles", map);
    }

    public void updateRoleType(Long userId, Long roleType, String bizCode) {
        UserDO userDO = new UserDO();
        userDO.setId(userId);
        userDO.setRoleMark(roleType);
        userDO.setBizCode(bizCode);
        this.getSqlMapClientTemplate().update("user.updateRoleType", userDO);

    }

    public void updateUserType(Long userId, Integer userType, String bizCode) {
        UserDO userDO = new UserDO();
        userDO.setId(userId);
        userDO.setType(userType);
        userDO.setBizCode(bizCode);
        this.getSqlMapClientTemplate().update("user.updateUserType", userDO);
    }

    @Override
    public List<UserDO> totalValidUsers(UserQTO userQTO) {

        List<UserDO> lists = this.getSqlMapClientTemplate().queryForList("user.totalValidUsers", userQTO);
        return lists;
    }

    @Override
    public long getTotalValidCount(UserQTO userQTO) {
        long total = (Long) this.getSqlMapClientTemplate().queryForObject("user.getTotalValidCount", userQTO);
        return total;
    }

	@Override
	public int resetUserPayPwd(Long userId, String pay_pwd) {
		UserDO userDO = new UserDO();
	    userDO.setId(userId);
	    userDO.setPayPassword(pay_pwd);
	    int result = getSqlMapClientTemplate().update("user.resetUserPayPwd", userDO);
	    return result;
	}


	@Override
	public int updateNickName(Long userId, String nickName) {
		UserDO userDO = new UserDO();
	    userDO.setId(userId);
	    userDO.setNickName(nickName);
	    int result = getSqlMapClientTemplate().update("user.updateNickName", userDO);
	    return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDO> getUserByNickName(String nick_name) {
		 UserDO userDOO = new UserDO();
		 userDOO.setNickName(nick_name);
		 List<UserDO> userDoList = getSqlMapClientTemplate().queryForList("user.getUserByNickName", userDOO);
	     return userDoList;
	}


	@Override
	public int updateSexAndBirthday(Map<Object, Object> map) {
		UserDO userDO = new UserDO();
	    userDO.setId((Long)map.get("userId"));
	    
	    if(null != map.get("sex") && !"".equals(map.get("sex"))){
	    	userDO.setSex(Integer.parseInt(map.get("sex").toString()));
	    }
	    
	    if(null != map.get("birthday") && !"".equals(map.get("birthday"))){
	    	Date date=null;
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				date = sdf.parse(map.get("birthday").toString());
			} catch (ParseException e) {
				e.printStackTrace();
			} 
	    	userDO.setBirthday(date);
	    }   
	    
	    int result = getSqlMapClientTemplate().update("user.updateSexAndBirthday", userDO);
	    return result;
	}


	@Override
	public int updateUserInvitationId(Long userId, Long invitationId) {
		UserDO userDO = new UserDO();
	    userDO.setId(userId);
	    userDO.setInviterId(invitationId);
	    int result = getSqlMapClientTemplate().update("user.updateUserInvitationId", userDO);
	    return result;
	}


	@Override
	public UserDO getByInvitationId(Long invitationId) {
		UserDO userDO = new UserDO();
    	userDO.setInviterId(invitationId);
        UserDO userDo = (UserDO) getSqlMapClientTemplate().queryForObject("user.selectByInvitationId", userDO);
        return userDo;
	}


	@Override
	public int updateLastDistributorId(Long userId, Long sellerId) {
		UserDO userDO = new UserDO();
	    userDO.setId(userId);
	    userDO.setLastDistributorId(sellerId);
	    int result = getSqlMapClientTemplate().update("user.updateLastDistributorId", userDO);
	    return result;
	}


	@Override
	public int updateInviterId(Long userId, Long inviterId) {
		UserDO userDO = new UserDO();
	    userDO.setId(userId);
	    userDO.setInviterId(inviterId);
	    int result = getSqlMapClientTemplate().update("user.updateInviterId", userDO);
	    return result;
	}

    @Override
    public int addFansToInviterId(Long userId, Long inviterId) {
        UserDO userDO = new UserDO();
        userDO.setId(userId);
        userDO.setInviterId(inviterId);
        return getSqlMapClientTemplate().update("user.addFansToInviterId",userDO);

    }

    @SuppressWarnings("unchecked")
	@Override
	public List<UserDO> queryInviterListByUserId(UserQTO userQTO) {
		List<UserDO> userDoList = getSqlMapClientTemplate().queryForList("user.queryInviterListByUserId",userQTO);
		return userDoList;
	}

	@Override
	public int updateWxAndQq(Map<Object, Object> map) {
		UserDO userDO = new UserDO();
	    userDO.setId((Long)map.get("userId"));
	    
	    if(null != map.get("wechat") && !"".equals(map.get("wechat"))){
	    	userDO.setWechat(map.get("wechat").toString());
	    }
	    
	    if(null != map.get("qqCode") && !"".equals(map.get("qqCode"))){	    	
	    	userDO.setQqCode(map.get("qqCode").toString());
	    }
	    
	    int result = getSqlMapClientTemplate().update("user.updateWxAndQq", userDO);
	    return result;
	}
}
