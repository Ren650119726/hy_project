package com.mockuai.usercenter.mop.api.util;

import com.mockuai.usercenter.common.dto.UserAuthInfoDTO;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.common.dto.UserMessageDTO;
import com.mockuai.usercenter.mop.api.domain.*;
import org.apache.commons.lang.StringUtils;

import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
import org.springframework.beans.BeanUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MopApiUtil {

	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static String UID_SEPERATOR = "_";
	
	private static String FIELD_IS_NULL = " field is null";
	
	private static String FIELD_INVALID = "field is invalid";

	public static <T> MopResponse<T> transferResp(Response<T> userResp){
        //TODO refactor the code
        if(userResp.getCode() != ResponseCode.REQUEST_SUCCESS.getValue()){
        	//TODO 封装错误信息
        	return new MopResponse(userResp.getCode(),userResp.getMessage());
            //return new MopResponse<T>(MopRespCode.S_E_SERVICE_ERROR);
        }else{
            return new MopResponse<T>(userResp.getModule());
        }
    }

	public static MessageUidDTO parseMessageUid(String messageUid){
		if(StringUtils.isEmpty(messageUid)){
			return null;
		}

		String ids[]  = messageUid.split(UID_SEPERATOR);
		if(ids.length != 2){
			//TODO error handle
		}
		MessageUidDTO dto = new MessageUidDTO();
		dto.setUserId(Long.valueOf(ids[0]));
		dto.setId(Long.valueOf(ids[1]));
		return dto;
	}
	
	public static ConsigneeUidDTO parseConsigneeUid(String consigneeUid){
		if(StringUtils.isEmpty(consigneeUid)){
			//throw new ParamException("consignee_uid",FIELD_IS_NULL);
		}
		
		String ids[]  = consigneeUid.split(UID_SEPERATOR);
		if(ids.length != 2){
			//throw new ParamException(""); 
		}
		ConsigneeUidDTO dto = new ConsigneeUidDTO();
		dto.setUserId(Long.valueOf(ids[0]));
		dto.setConsigneeId(Long.valueOf(ids[1]));
		return dto;
	}

	public static List<MopMessageDTO> genMopMessageList(List<UserMessageDTO> userMessageDTOs){
		if(userMessageDTOs == null){
			return null;
		}

		List<MopMessageDTO> mopMessageDTOs = new ArrayList<MopMessageDTO>();
		for(UserMessageDTO userMessageDTO: userMessageDTOs){
			mopMessageDTOs.add(genMopMessage(userMessageDTO));
		}

		return mopMessageDTOs;
	}

	public static MopMessageDTO genMopMessage(UserMessageDTO userMessageDTO){
		if(userMessageDTO == null){
			return null;
		}

		MopMessageDTO mopMessageDTO = new MopMessageDTO();
		mopMessageDTO.setTitle(userMessageDTO.getTitle());
		mopMessageDTO.setContent(userMessageDTO.getContent());
		mopMessageDTO.setStatus(userMessageDTO.getStatus());
		mopMessageDTO.setMessageUid(genMessageUid(userMessageDTO));
		mopMessageDTO.setReceiveTime(dateFormat.format(userMessageDTO.getGmtCreated()));

		return mopMessageDTO;
	}

	public static String genMessageUid(UserMessageDTO userMessageDTO){
		if(userMessageDTO == null){
			return null;
		}

		return userMessageDTO.getReceiverId()+UID_SEPERATOR+userMessageDTO.getId();
	}

	public static List<MopInviteeDTO> genMopInviteeList(List<UserDTO> userDTOs){
		if(userDTOs == null){
			return null;
		}

		List<MopInviteeDTO> mopInviteeDTOs = new ArrayList<MopInviteeDTO>();
		for(UserDTO userDTO: userDTOs){
			mopInviteeDTOs.add(genMopInviteeDTO(userDTO));
		}

		return mopInviteeDTOs;
	}

	public static MopInviteeDTO genMopInviteeDTO(UserDTO userDTO){
		if(userDTO == null){
			return null;
		}
		MopInviteeDTO mopInviteeDTO = new MopInviteeDTO();
		mopInviteeDTO.setId(userDTO.getId());
		mopInviteeDTO.setMobile(userDTO.getMobile());
		mopInviteeDTO.setUserName(userDTO.getName());
		mopInviteeDTO.setInviteTime(dateFormat.format(userDTO.getGmtCreated()));
		return mopInviteeDTO;
	}

	public static MopAuthInfoDTO genMopAuthInfo(UserAuthInfoDTO authInfo){
		if(authInfo == null){
			return null;
		}
		MopAuthInfoDTO mopAuthInfoDTO = new MopAuthInfoDTO();
		mopAuthInfoDTO.setType(authInfo.getType());
		mopAuthInfoDTO.setRealName(authInfo.getRealName());
		mopAuthInfoDTO.setIdcardNo(authInfo.getIdcardNo());
		mopAuthInfoDTO.setIdcardFrontImg(authInfo.getIdcardFrontImg());
		mopAuthInfoDTO.setIdcardReverseImg(authInfo.getIdcardReverseImg());
		return mopAuthInfoDTO;
	}
	
	public static MopConsigneeDTO genMopConsignee(UserConsigneeDTO consignee){
		if(consignee == null){
			return null;
		}
		MopConsigneeDTO mopDto = new MopConsigneeDTO();
		
		mopDto.setConsigneeUid(consignee.getConsigneeUid());
		mopDto.setId(consignee.getId());
		mopDto.setCountryCode(consignee.getCountryCode());
		mopDto.setIdCardNo(consignee.getIdCardNo());
		mopDto.setProvinceCode(consignee.getProvinceCode());
		mopDto.setCityCode(consignee.getCityCode());
		mopDto.setAreaCode(consignee.getAreaCode());
		mopDto.setTownCode(consignee.getTownCode());
		mopDto.setCountry(consignee.getCountry());
		mopDto.setProvince(consignee.getProvince());
		mopDto.setCity(consignee.getCity());
		mopDto.setArea(consignee.getArea());
		mopDto.setTown(consignee.getTown());
		mopDto.setAddress(consignee.getAddress());
		mopDto.setMobile(consignee.getMobile());
		mopDto.setConsignee(consignee.getConsignee());
		mopDto.setLatitude(consignee.getLatitude());
		mopDto.setLongitude(consignee.getLongitude());
		Integer isDefault = consignee.getIsDefault();
		if(isDefault != null){
			mopDto.setIsDefault((isDefault==1));
		}else{
			mopDto.setIsDefault(false);
		}
		return mopDto;
	}

	public static MopUserDTO genMopUserDTO(UserDTO userDTO){
		MopUserDTO mopUserDTO = new MopUserDTO();
		BeanUtils.copyProperties(userDTO, mopUserDTO);
		mopUserDTO.setMobile(userDTO.getMobile());
		mopUserDTO.setUserName(userDTO.getName());
		mopUserDTO.setInvitationCode(userDTO.getInvitationCode());
		return mopUserDTO;
	}
	
	public static String genUid(Long userId,Long id){
		return String.valueOf(userId) + UID_SEPERATOR + String.valueOf(id);
	}
	
	public static void main(String args[]){
		
	}
	
}
