package com.mockuai.deliverycenter.mop.api.util;

import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.deliverycenter.mop.api.dto.MopRegionDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengzhangqiang on 5/31/15.
 */
public class ModelUtil {
    public static List<MopRegionDTO> genMopRegionDTOList(List<RegionDTO> regionDTOs){
        if(regionDTOs == null){
            return null;
        }

        List<MopRegionDTO> mopRegionDTOs = new ArrayList<MopRegionDTO>();
        for(RegionDTO regionDTO: regionDTOs){
            mopRegionDTOs.add(genMopRegionDTO(regionDTO));
        }
        return mopRegionDTOs;
    }

    public static MopRegionDTO genMopRegionDTO(RegionDTO regionDTO){
        MopRegionDTO mopRegionDTO = new MopRegionDTO();
        mopRegionDTO.setId(regionDTO.getId());
        mopRegionDTO.setCode(regionDTO.getCode());
        mopRegionDTO.setName(regionDTO.getName());

        return mopRegionDTO;
    }
}
