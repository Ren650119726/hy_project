package com.mockuai.appcenter.core.util;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.AppPropertyDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.appcenter.core.domain.AppInfoDO;
import com.mockuai.appcenter.core.domain.AppPropertyDO;
import com.mockuai.appcenter.core.domain.BizInfoDO;
import com.mockuai.appcenter.core.domain.BizPropertyDO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengzhangqiang on 6/8/15.
 */
public class ModelUtil {
    public static AppInfoDO convertToAppInfoDO(AppInfoDTO appInfoDTO){
        if(appInfoDTO == null){
            return null;
        }

        AppInfoDO appInfoDO = new AppInfoDO();
        BeanUtils.copyProperties(appInfoDTO, appInfoDO);

        return appInfoDO;
    }

    public static BizInfoDO convertToBizInfoDO(BizInfoDTO bizInfoDTO){
        if(bizInfoDTO == null){
            return null;
        }

        BizInfoDO bizInfoDO = new BizInfoDO();
        BeanUtils.copyProperties(bizInfoDTO, bizInfoDO);

        return bizInfoDO;
    }

    public static List<AppPropertyDO> convertToAppPropertyDOList(
            List<AppPropertyDTO> appPropertyDTOList){
        if(appPropertyDTOList == null){
            return null;
        }

        List<AppPropertyDO> appPropertyDOs = new ArrayList<AppPropertyDO>();
        for(AppPropertyDTO appPropertyDTO: appPropertyDTOList){
            appPropertyDOs.add(convertToAppPropertyDO(appPropertyDTO));
        }

        return appPropertyDOs;
    }

    public static AppPropertyDO convertToAppPropertyDO(AppPropertyDTO appPropertyDTO){
        if(appPropertyDTO == null){
            return null;
        }

        AppPropertyDO appPropertyDO = new AppPropertyDO();
        BeanUtils.copyProperties(appPropertyDTO, appPropertyDO);
        return appPropertyDO;
    }

    public static List<BizPropertyDO> convertToBizPropertyDOList(
            List<BizPropertyDTO> bizPropertyDTOList){
        if(bizPropertyDTOList == null){
            return null;
        }

        List<BizPropertyDO> bizPropertyDOs = new ArrayList<BizPropertyDO>();
        for(BizPropertyDTO bizPropertyDTO: bizPropertyDTOList){
            bizPropertyDOs.add(convertToBizPropertyDO(bizPropertyDTO));
        }

        return bizPropertyDOs;
    }

    public static BizPropertyDO convertToBizPropertyDO(BizPropertyDTO bizPropertyDTO){
        if(bizPropertyDTO == null){
            return null;
        }

        BizPropertyDO bizPropertyDO = new BizPropertyDO();
        BeanUtils.copyProperties(bizPropertyDTO, bizPropertyDO);
        return bizPropertyDO;
    }

    public static AppInfoDTO convertToAppInfoDTO(AppInfoDO appInfoDO){
        if(appInfoDO == null){
            return null;
        }

        AppInfoDTO appInfoDTO = new AppInfoDTO();
        BeanUtils.copyProperties(appInfoDO, appInfoDTO);

        return appInfoDTO;
    }

    public static BizInfoDTO convertToBizInfoDTO(BizInfoDO bizInfoDO){
        if(bizInfoDO == null){
            return null;
        }

        BizInfoDTO bizInfoDTO = new BizInfoDTO();
        BeanUtils.copyProperties(bizInfoDO, bizInfoDTO);

        return bizInfoDTO;
    }

    public static List<AppPropertyDTO> convertToAppPropertyDTOList(
            List<AppPropertyDO> appPropertyDOList){
        if(appPropertyDOList == null){
            return null;
        }

        List<AppPropertyDTO> appPropertyDTOs = new ArrayList<AppPropertyDTO>();
        for(AppPropertyDO appPropertyDO: appPropertyDOList){
            appPropertyDTOs.add(convertToAppPropertyDTO(appPropertyDO));
        }

        return appPropertyDTOs;
    }

    public static AppPropertyDTO convertToAppPropertyDTO(AppPropertyDO appPropertyDO){
        if(appPropertyDO == null){
            return null;
        }

        AppPropertyDTO appPropertyDTO = new AppPropertyDTO();
        BeanUtils.copyProperties(appPropertyDO, appPropertyDTO);
        return appPropertyDTO;
    }

    public static List<BizPropertyDTO> convertToBizPropertyDTOList(
            List<BizPropertyDO> bizPropertyDOList){
        if(bizPropertyDOList == null){
            return null;
        }

        List<BizPropertyDTO> bizPropertyDTOs = new ArrayList<BizPropertyDTO>();
        for(BizPropertyDO bizPropertyDO: bizPropertyDOList){
            bizPropertyDTOs.add(convertToBizPropertyDTO(bizPropertyDO));
        }

        return bizPropertyDTOs;
    }

    public static BizPropertyDTO convertToBizPropertyDTO(BizPropertyDO bizPropertyDO){
        if(bizPropertyDO == null){
            return null;
        }

        BizPropertyDTO bizPropertyDTO = new BizPropertyDTO();
        BeanUtils.copyProperties(bizPropertyDO, bizPropertyDTO);
        return bizPropertyDTO;
    }
}
