package com.mockuai.usercenter.core.manager.impl;

import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.common.qto.UserConsigneeQTO;
import com.mockuai.usercenter.core.dao.UserConsigneeDAO;
import com.mockuai.usercenter.core.domain.UserConsigneeDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserConsigneeManager;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.message.listener.TradePayedMessageListener;
import com.mockuai.usercenter.core.util.UserUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserConsigneeManagerImpl implements UserConsigneeManager {
	private static final Logger log = LoggerFactory.getLogger(UserConsigneeManagerImpl.class);

    @Resource
    private UserManager userManager;

    @Resource
    private UserConsigneeDAO userConsigneeDAO;

    //update by cwr 没有用到
    //@Resource
    //private FeeClient feeClient;

    //@Resource
    //private AddressCheckManager addressCheckManager; -- update by cwr 不需要该验证

    @Override
    public UserConsigneeDTO addConsignee(UserConsigneeDTO userConsigneeDto)
            throws UserException {
        // updated by cwr 暂时先不用
        //addressCheckManager.addressCheck(consigneeDto);

        // 详细地址验证
        if (null == userConsigneeDto.getAddress()) {
            throw new UserException(ResponseCode.P_PARAM_NULL,
                    "address is null");
        }


        // 收件人手机号和座机号校验
//        UserUtil.checkMobileAndPhone(userConsigneeDto.getMobile());

        // 收件人手机号码验证
        UserUtil.checkMobile(userConsigneeDto.getMobile());

        // 座机号码验证
        if (null != userConsigneeDto.getPhone()) {
            UserUtil.checkPhoneNo(userConsigneeDto.getPhone());
        }

        // 收货人姓名不能为空
        if (null == userConsigneeDto.getConsignee()) {
            throw new UserException(ResponseCode.P_PARAM_NULL,
                    "consignee no format is null");
        }
        // 将dto转换为do
        UserConsigneeDO userConsigneeDo = new UserConsigneeDO();
        BeanUtils.copyProperties(userConsigneeDto, userConsigneeDo);

        // 添加收货地址
        Long id = userConsigneeDAO.addConsigee(userConsigneeDo);

        return getConsigneeById(userConsigneeDo.getUserId(), id);
    }

    @Override
    public int deleteConsignee(Long userId, Long consigneeId)
            throws UserException {
        if (null == consigneeId) {
            throw new UserException(ResponseCode.P_PARAM_NULL,
                    "consigneeId is null");
        }

        int result = userConsigneeDAO.deleteConsignee(userId, consigneeId);

        if (result != 1) {
            throw new UserException(ResponseCode.B_DELETE_ERROR, "delete error");
        }

        return result;
    }

    @Override
    public int updateConsignee(UserConsigneeDTO userConsigneeDto) throws UserException {

        // 收货地址验证
        //addressCheckManager.addressCheck(consigneeDto); -- updated by cwr不需要该验证

        // id验证
        if (null == userConsigneeDto.getId()) {
            throw new UserException(ResponseCode.P_PARAM_NULL, "id is null");
        }

        // 详细地址验证
        if (null == userConsigneeDto.getAddress()) {
            throw new UserException(ResponseCode.P_PARAM_NULL,
                    "address is null");
        }

        // 收件人手机号和座机号校验
//        UserUtil.checkMobileAndPhone(userConsigneeDto.getMobile());

        // 收件人手机号码验证
        UserUtil.checkMobile(userConsigneeDto.getMobile());

        // 座机号码验证
        if (null != userConsigneeDto.getPhone()) {
            UserUtil.checkPhoneNo(userConsigneeDto.getPhone());
        }

        // 验证收货人姓名
        if (null == userConsigneeDto.getConsignee()) {
            throw new UserException(ResponseCode.P_PARAM_NULL,
                    "consignee no format is null");
        }

        UserConsigneeDO userConsigneeDo = new UserConsigneeDO();
        BeanUtils.copyProperties(userConsigneeDto, userConsigneeDo);

        int result = userConsigneeDAO.updateConsigee(userConsigneeDo);

        if (result != 1) {
            throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
        }

        return result;
    }

    @Override
    public int setDefConsignee(Long userId, Long consigneeId)
            throws UserException {

        int result = userConsigneeDAO.setDefConsignee(userId, consigneeId);
        if (result != 1) {
            throw new UserException(ResponseCode.B_UPDATE_ERROR, "update error");
        }

        return result;
    }

    @Override
    public int updateUserDefaultConsignee(Long userId) throws UserException {

        int result = userConsigneeDAO.updateUserDefaultConsignee(userId);

        //updated by cwr  如果是本身没有默认地址 会出错
        /*
        if (result <= 0) {
			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
		}
		*/
        return result;
    }

    @Override
    public int getConsigneeCountByUserId(Long userId) throws UserException {

        int result = userConsigneeDAO.getConsigneeCountByUserId(userId);

        return result;
    }

    @Override
    public List<UserConsigneeDTO> queryConsignee(Long userId) throws UserException {

        UserDTO userDto = userManager.getUserById(userId);
        if (null == userDto) {
            throw new UserException(ResponseCode.B_SELECT_ERROR,
                    "user is not exist");
        }

        List<UserConsigneeDTO> userConsigneeDtos = new ArrayList<UserConsigneeDTO>();

        List<UserConsigneeDO> userConsigneeDos = userConsigneeDAO.queryConsignee(userId);

        // 将查询的do列表转换为dto
        for (UserConsigneeDO consignee : userConsigneeDos) {
            UserConsigneeDTO userConsigneeDto = new UserConsigneeDTO();
            BeanUtils.copyProperties(consignee, userConsigneeDto);
            userConsigneeDtos.add(userConsigneeDto);
            userConsigneeDto.setConsigneeUid(UserUtil.generateUid(consignee.getUserId(), consignee.getId()));
        }

        return userConsigneeDtos;
    }

    @Override
    public int deleteUserConsignee(Long userId) throws UserException {
        // TODO Auto-generated method stub

        UserDTO userDto = userManager.getUserById(userId);
        if (null == userDto) {
            throw new UserException(ResponseCode.B_SELECT_ERROR,
                    "user is not exist");
        }

        int result = userConsigneeDAO.deleteUserConsignee(userId);

        return result;
    }

    @Override
    public int restoreUserConsignee(Long userId) throws UserException {
        // TODO Auto-generated method stub

        int result = userConsigneeDAO.restoreUserConsignee(userId);

        return result;
    }

    @Override
    public UserConsigneeDTO getConsigneeById(Long userId, Long consigneeId)
            throws UserException {
        // TODO Auto-generated method stub
        if (null == consigneeId) {
            throw new UserException(ResponseCode.P_PARAM_NULL,
                    "consigneeId is null");
        }
        if (consigneeId < 0) {
            throw new UserException(ResponseCode.P_PARAM_NULL,
                    "consigneeId is error");
        }

        UserConsigneeDO userConsigneeDo = userConsigneeDAO.getConsigneeById(userId,
                consigneeId);

        UserConsigneeDTO userConsigneeDto = null;

        if (userConsigneeDo != null) {
            userConsigneeDto = new UserConsigneeDTO();
            BeanUtils.copyProperties(userConsigneeDo, userConsigneeDto);
        }

        return userConsigneeDto;
    }

    @Override
    public Boolean increaseConsigneeUseCount(Long consigneeId, Long userId, String bizCode) throws UserException {
    	log.info("消息地址添加次数");
        int n = this.userConsigneeDAO.increaseConsigneeUseCount(consigneeId, userId, bizCode);
        if (n != 1) {
            throw new UserException(ResponseCode.B_UPDATE_ERROR, "update consignee use count error");
        }
        return true;
    }

    @Override
    public List<UserConsigneeDTO> queryAllConsignee(UserConsigneeQTO userConsigneeQTO) {
        List<UserConsigneeDO> userConsigneeDOs = this.userConsigneeDAO.queryAllConsignee(userConsigneeQTO);
        List<UserConsigneeDTO> userConsigneeDTOs = new ArrayList<UserConsigneeDTO>();
        if (userConsigneeDOs != null) {
            for (UserConsigneeDO userConsigneeDO : userConsigneeDOs) {
                UserConsigneeDTO userConsigneeDTO = new UserConsigneeDTO();
                BeanUtils.copyProperties(userConsigneeDO, userConsigneeDTO);
                userConsigneeDTOs.add(userConsigneeDTO);
            }
        }
        return userConsigneeDTOs;
    }

    @Override
    public Long totalAllCount(UserConsigneeQTO userConsigneeQTO) {
        return this.userConsigneeDAO.totalAllCount(userConsigneeQTO);
    }
}
