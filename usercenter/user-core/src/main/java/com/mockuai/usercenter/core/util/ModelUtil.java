package com.mockuai.usercenter.core.util;

import java.util.ArrayList;

import com.mockuai.usercenter.common.dto.EnterpriseAuthExtendDTO;
import com.mockuai.usercenter.common.dto.UserAuthInfoDTO;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.common.dto.UserOpenInfoDTO;
import com.mockuai.usercenter.common.dto.datamigrate.MigrateUserDTO;
import com.mockuai.usercenter.common.dto.datamigrate.MigrateUserOpenInfoDTO;
import com.mockuai.usercenter.core.domain.EnterpriseAuthExtendDO;
import com.mockuai.usercenter.core.domain.UserAuthInfoDO;
import com.mockuai.usercenter.core.domain.UserDO;
import com.mockuai.usercenter.core.domain.UserOpenInfoDO;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * Created by zengzhangqiang on 6/4/15.
 */
public class ModelUtil {
    public static UserDO convertToUserDO(MigrateUserDTO migrateUserDTO){
        if(migrateUserDTO == null){
            return null;
        }

        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(migrateUserDTO, userDO);
        return userDO;
    }

    public static UserDO convertToUserDO(UserDTO userDTO){
        if(userDTO == null){
            return null;
        }

        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userDTO, userDO);
        return userDO;
    }

    public static UserDTO convertToUserDTO(UserDO userDO){
        if(userDO == null){
            return null;
        }

        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userDO, userDTO);
        return userDTO;
    }

    public static MigrateUserDTO convertToMigrateUserDTO(UserDO userDO){
        if(userDO == null){
            return null;
        }

        MigrateUserDTO migrateUserDTO = new MigrateUserDTO();
        BeanUtils.copyProperties(userDO, migrateUserDTO);
        return migrateUserDTO;
    }

    public static UserOpenInfoDO convertToUserOpenInfoDO(MigrateUserOpenInfoDTO migrateUserOpenInfoDTO){
        if(migrateUserOpenInfoDTO == null){
            return null;
        }

        UserOpenInfoDO userOpenInfoDO = new UserOpenInfoDO();
        BeanUtils.copyProperties(migrateUserOpenInfoDTO, userOpenInfoDO);

        return userOpenInfoDO;
    }

    public static UserOpenInfoDO convertToUserOpenInfoDO(UserOpenInfoDTO userOpenInfoDTO){
        if(userOpenInfoDTO == null){
            return null;
        }

        UserOpenInfoDO userOpenInfoDO = new UserOpenInfoDO();
        BeanUtils.copyProperties(userOpenInfoDTO, userOpenInfoDO);

        return userOpenInfoDO;
    }

    public static UserOpenInfoDTO convertToUserOpenInfoDTO(UserOpenInfoDO userOpenInfoDO){
        if(userOpenInfoDO == null){
            return null;
        }
        UserOpenInfoDTO userOpenInfoDTO = new UserOpenInfoDTO();
        BeanUtils.copyProperties(userOpenInfoDO, userOpenInfoDTO);

        return userOpenInfoDTO;
    }

    public static List<UserAuthInfoDTO> convertToUserAuthInfoDTOList(List<UserAuthInfoDO> userAuthInfoDOs){
        if(userAuthInfoDOs == null){
            return null;
        }

        List<UserAuthInfoDTO> userAuthInfoDTOs = new ArrayList<UserAuthInfoDTO>();

        for(UserAuthInfoDO userAuthInfoDO: userAuthInfoDOs){
            userAuthInfoDTOs.add(convertToUserAuthInfoDTO(userAuthInfoDO));
        }

        return userAuthInfoDTOs;
    }

    public static UserAuthInfoDTO convertToUserAuthInfoDTO(UserAuthInfoDO userAuthInfoDO){
        if(userAuthInfoDO == null){
            return null;
        }

        UserAuthInfoDTO userAuthInfoDTO = new UserAuthInfoDTO();
        BeanUtils.copyProperties(userAuthInfoDO, userAuthInfoDTO);

        return userAuthInfoDTO;
    }

    public static UserAuthInfoDO convertToUserAuthInfoDO(UserAuthInfoDTO userAuthInfoDTO){
        if(userAuthInfoDTO == null){
            return null;
        }

        UserAuthInfoDO userAuthInfoDO = new UserAuthInfoDO();
        BeanUtils.copyProperties(userAuthInfoDTO, userAuthInfoDO);

        return userAuthInfoDO;
    }

    public static EnterpriseAuthExtendDTO convertToEnterpriseAuthExtendDTO(
            EnterpriseAuthExtendDO enterpriseAuthExtendDO){
        if(enterpriseAuthExtendDO == null){
            return null;
        }

        EnterpriseAuthExtendDTO enterpriseAuthExtendDTO = new EnterpriseAuthExtendDTO();
        BeanUtils.copyProperties(enterpriseAuthExtendDO, enterpriseAuthExtendDTO);
        return enterpriseAuthExtendDTO;
    }

    public static EnterpriseAuthExtendDO convertToEnterpriseAuthExtendDO(
            EnterpriseAuthExtendDTO enterpriseAuthExtendDTO){
        if(enterpriseAuthExtendDTO == null){
            return null;
        }

        EnterpriseAuthExtendDO enterpriseAuthExtendDO = new EnterpriseAuthExtendDO();
        BeanUtils.copyProperties(enterpriseAuthExtendDTO, enterpriseAuthExtendDO);
        return enterpriseAuthExtendDO;
    }
}
