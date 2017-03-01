package com.mockuai.virtualwealthcenter.core.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.functors.ForClosure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.domain.dto.boss.BossUserAuthonDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopUserAuthonAppDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopUserAuthonAppQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.UserAuthonQTO;
import com.mockuai.virtualwealthcenter.core.dao.UserAuthonAppDAO;
import com.mockuai.virtualwealthcenter.core.domain.UserAuthonAppDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.UserAuthonAppManager;
import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class UserAuthonAppManagerImpl implements UserAuthonAppManager{
	  private static final Logger LOGGER = LoggerFactory.getLogger(UserManagerImpl.class.getName());
	@Autowired
	private UserAuthonAppDAO userAuthonAppDAO;
	
	/**
	 * 实名认证
	 */
	@Override
	public Long addUserAuthon(UserAuthonAppDO userAuthonAppDO) throws VirtualWealthException {
		try {
            return userAuthonAppDAO.addUserAuthon(userAuthonAppDO);
        } catch (Exception e) {
            if (e.getCause().getCause() instanceof MySQLIntegrityConstraintViolationException) {
                throw new VirtualWealthException(ResponseCode.DB_OP_ERROR_OF_DUPLICATE_ENTRY);
            }
            LOGGER.error("failed when adding wealthAccount : {}", JsonUtil.toJson(userAuthonAppDO), e);
            throw new VirtualWealthException(ResponseCode.USER_INFORMATION_SUBMISSION_FAILED);
        }
	}

	
	/**
	 * 获取实名认证信息
	 */
	@Override
	public UserAuthonAppDO selectUserAuton(Long userId)
			throws VirtualWealthException {
		try {
			return userAuthonAppDAO.selectUserAuton(userId);
        } catch (Exception e) {
            if (e.getCause().getCause() instanceof MySQLIntegrityConstraintViolationException) {
                throw new VirtualWealthException(ResponseCode.DB_OP_ERROR_OF_DUPLICATE_ENTRY);
            }
            LOGGER.error("failed when adding wealthAccount : {}", JsonUtil.toJson(userId), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
	}


	/**
	 * 根据userid获取用户信息(client接口)
	 */
	@Override
	public MopUserAuthonAppDTO selectMopUserAuthon(Long userId)
			throws VirtualWealthException {
		try {
			UserAuthonAppDO user = userAuthonAppDAO.selectUserAuton(userId);
			if(user!=null){
				MopUserAuthonAppDTO mopUserAuthonAppDTO = new MopUserAuthonAppDTO();
				BeanUtils.copyProperties(user , mopUserAuthonAppDTO);
				return mopUserAuthonAppDTO;
			}
			return null;
        } catch (Exception e) {
        	throw new VirtualWealthException(e);
        }
	}


	@Override
	public UserAuthonAppDO selectPersonalId(String authonPersonalId)
			throws VirtualWealthException {
		try {
			return userAuthonAppDAO.selectPersonalId(authonPersonalId);
        } catch (Exception e) {
            if (e.getCause().getCause() instanceof MySQLIntegrityConstraintViolationException) {
                throw new VirtualWealthException(ResponseCode.DB_OP_ERROR_OF_DUPLICATE_ENTRY);
            }
            LOGGER.error("failed when adding wealthAccount : {}", JsonUtil.toJson(authonPersonalId), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
	}

	/**
	 * 根据身份证号获取用户信息(client接口)
	 */
	@Override
	public MopUserAuthonAppDTO selectMopUserAuthonByPersonalId(
			String authonPersonalId) throws VirtualWealthException {
		try {
			MopUserAuthonAppDTO mopUserAuthonAppDTO = new MopUserAuthonAppDTO();
			UserAuthonAppDO user = userAuthonAppDAO.selectPersonalId(authonPersonalId);
			BeanUtils.copyProperties(user == null ? new UserAuthonAppDO() : user, mopUserAuthonAppDTO);
			return mopUserAuthonAppDTO;
        } catch (Exception e) {
        	throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
	}


	/**
	 * 根据用户idList获取用户信息
	 */
	@Override
	public List<MopUserAuthonAppDTO> selectMopUserAuthonList(
			List<Long> userIdList) throws VirtualWealthException {
		try {
			
			List<MopUserAuthonAppDTO> list = new ArrayList<MopUserAuthonAppDTO>();
			for(Long userid : userIdList){
				MopUserAuthonAppDTO mopUserAuthonAppDTO = new MopUserAuthonAppDTO();
				UserAuthonAppDO user = userAuthonAppDAO.selectUserAuton(userid);
				BeanUtils.copyProperties(user == null ? new UserAuthonAppDO() : user, mopUserAuthonAppDTO);
				
				list.add(mopUserAuthonAppDTO);
			}
			
			return list;
        } catch (Exception e) {
        	return new ArrayList<MopUserAuthonAppDTO>();
        }
	}


	@Override
	public List<MopUserAuthonAppDTO> selectUserAuthonByQto(
			MopUserAuthonAppQTO mopUserAuthonAppQTO)
			throws VirtualWealthException {
		try {
			List<MopUserAuthonAppDTO> list = new ArrayList<MopUserAuthonAppDTO>();
//			LOGGER.error("PUT:"+mopUserAuthonAppQTO.getUserIdList().toArray().toString());
			LOGGER.error("PUT ID:"+mopUserAuthonAppQTO.getAuthonPersonalid()+"PUT Name:"+mopUserAuthonAppQTO.getAuthonRealname());
			List<UserAuthonAppDO> userAuthonAppDOs = userAuthonAppDAO.selectUserAuthonByQto(mopUserAuthonAppQTO);
//			LOGGER.error("USERdos RESULT IS NULL:"+(null==userAuthonAppDOs));
			for(UserAuthonAppDO userAuthonAppDO : userAuthonAppDOs){
				MopUserAuthonAppDTO mud = new MopUserAuthonAppDTO();
				BeanUtils.copyProperties(userAuthonAppDO == null ? new UserAuthonAppDO() : userAuthonAppDO, mud);
				list.add(mud);
			}
			return list;
        } catch (Exception e) {
        	e.getMessage();
        	return new ArrayList<MopUserAuthonAppDTO>();
        }
	}


	/**
	 * 根据id查询用户信息
	 */
	@Override
	public MopUserAuthonAppDTO selectUserAuthonById(Long id)
			throws VirtualWealthException {
		try{
			MopUserAuthonAppDTO mopUserAuthonAppDTO = new MopUserAuthonAppDTO();
			UserAuthonAppDO user = userAuthonAppDAO.selectUserAuthonById(id);
			BeanUtils.copyProperties(user == null ? new UserAuthonAppDO() : user, mopUserAuthonAppDTO);
			return mopUserAuthonAppDTO;
		}catch (Exception e){
			return new MopUserAuthonAppDTO();
		}
	}

	/**
	 * 修改审核状态
	 */
	

	@Override
	public Integer modifyAuditStatus(UserAuthonAppDO userAuthonAppDO)
			throws VirtualWealthException {
		try{
			Integer result =userAuthonAppDAO.modifyAuditStatus(userAuthonAppDO);
			return result;
		}catch(Exception e){
			throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
		}
	}

	/**
	 * 查询所有审核用户信息
	 * @param mopUserAuthonAppQTO
	 * @return
	 * @throws VirtualWealthException
	 */

	@Override
	public List<BossUserAuthonDTO> selectUserAuthonAll(UserAuthonQTO userAuthonQTO)
			throws VirtualWealthException {
		try{
			List<BossUserAuthonDTO> list =  new ArrayList<BossUserAuthonDTO>();	

			List<UserAuthonAppDO> userAuthonAppDOs = userAuthonAppDAO.selectUserAuthonAll(userAuthonQTO);
			for(UserAuthonAppDO userAuthonAppDO : userAuthonAppDOs){
				BossUserAuthonDTO mud = new BossUserAuthonDTO();
				mud.setId(userAuthonAppDO.getId());
				mud.setAuthon_realname(userAuthonAppDO.getAuthonRealname());
				mud.setPicture_front(userAuthonAppDO.getPictureFront());
				mud.setPicture_back(userAuthonAppDO.getPictureBack());
				mud.setAuthon_bankname(userAuthonAppDO.getAuthonBankname());
				mud.setAuthon_text(userAuthonAppDO.getAuthonText());
				mud.setAuthon_mobile(userAuthonAppDO.getAuthonMobile());
				mud.setAuthon_no(userAuthonAppDO.getAuthonNo());
				mud.setAuthon_personalid(userAuthonAppDO.getAuthonPersonalid());
				mud.setAuthon_status(userAuthonAppDO.getAuthonStatus());
				mud.setAuthon_text(userAuthonAppDO.getAuthonText());
				mud.setAuthon_time(userAuthonAppDO.getAuthonTime());
				mud.setBiz_code(userAuthonAppDO.getBizCode());
				mud.setDelete_mark(userAuthonAppDO.getDeleteMark());
				
				mud.setGmt_created(userAuthonAppDO.getGmtCreated());
				mud.setGmt_modified(userAuthonAppDO.getGmtModified());
				mud.setUser_id(userAuthonAppDO.getUserId());
				mud.setAuthon_no(userAuthonAppDO.getAuthonNo());
				//LOGGER.info("mud:"+mud);
				list.add(mud);
			}
			return list;
		}catch (Exception e){
			return new ArrayList<BossUserAuthonDTO>();
		}

	}
	
	/**
	 * 查询所有待审核信息的总数
	 */
	@Override
	public Long selectUserAuthonAllCount(UserAuthonQTO userAuthonQTO)
			throws VirtualWealthException {
		Long count=userAuthonAppDAO.selectUserAuthonAllCount(userAuthonQTO);
		return count;
	}
	
	/**
	 * 根据用户id查审核状态
	 */

	@Override
	public MopUserAuthonAppDTO selectAuditStatus(Long userId)
			throws VirtualWealthException {
		try{
			MopUserAuthonAppDTO mopUserAuthonAppDTO = new MopUserAuthonAppDTO();
			UserAuthonAppDO user = userAuthonAppDAO.selectAuditStatus(userId);
			if(null!=user){
				mopUserAuthonAppDTO.setAuthonRealname(user.getAuthonRealname());
				mopUserAuthonAppDTO.setAuthonPersonalid(user.getAuthonPersonalid());
				mopUserAuthonAppDTO.setPictureFront(user.getPictureFront());
				mopUserAuthonAppDTO.setPictureBack(user.getPictureBack());
				mopUserAuthonAppDTO.setAuthonStatus(user.getAuthonStatus());
				return mopUserAuthonAppDTO;
			}
			return new MopUserAuthonAppDTO();
		}catch(Exception e){
			throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
		}
		
	}
/**
 * 审核失败后重新提交审核信息
 */

	@Override
	public Long updateUserAuton(UserAuthonAppDO userAuthonAppDO)
			throws VirtualWealthException {
		try{
			Long result =userAuthonAppDAO.updateUserAuton(userAuthonAppDO);
			return result;
		}catch(Exception e){
			throw new VirtualWealthException(ResponseCode.USER_INFORMATION_SUBMISSION_FAILED);
		}
	}

/**
 * 用户更改用户名时更到到实名表中
 */
@Override
public Long SyncPhoneNumber(UserAuthonAppDO userAuthonAppDO)
		throws VirtualWealthException {
	try{
		Long userId = userAuthonAppDO.getUserId();
		String authonMobile = userAuthonAppDO.getAuthonMobile();
		Long result = userAuthonAppDAO.syncPhoneNumber(userId, authonMobile);
		return result;
	}catch (Exception e){
		throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
	}
}



	
	
}
