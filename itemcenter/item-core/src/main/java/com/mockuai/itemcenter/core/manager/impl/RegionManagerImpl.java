package com.mockuai.itemcenter.core.manager.impl;

import com.mockuai.deliverycenter.client.RegionClient;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.deliverycenter.common.qto.fee.RegionQTO;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.area.AreaDTO;
import com.mockuai.itemcenter.common.domain.dto.area.CityDTO;
import com.mockuai.itemcenter.common.domain.dto.area.ProvinceDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.RegionManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by yindingyu on 15/10/23.
 */
@Service
public class RegionManagerImpl implements RegionManager{

    @Resource
    private RegionClient regionClient;

    @Override
    public List<AreaDTO> queryAreas(String appKey) throws ItemException {

        try {

            RegionQTO regionQTO = new RegionQTO();
            regionQTO.setParentCode("CN");

            Response<List<RegionDTO>> regionDTOs = regionClient.queryRegion(regionQTO, appKey);

            Map<String, AreaDTO> areaDTOMap = initAreaMap();

            for (RegionDTO regionDTO : regionDTOs.getModule()) {

                String name = regionDTO.getName();

                //将省份按地区分组
                ProvinceDTO provinceDTO = new ProvinceDTO();
                provinceDTO.setProvinceId(regionDTO.getCode());
                provinceDTO.setProvinceName(name);
                areaDTOMap.get(name).getProvinces().add(provinceDTO);

                RegionQTO regionQTOx = new RegionQTO();
                regionQTOx.setParentCode(regionDTO.getCode());


                Response<List<RegionDTO>> response = regionClient.queryRegion(regionQTOx, appKey);

                List<CityDTO> cityDTOs = new ArrayList<CityDTO>();
                for (RegionDTO regionDTOx : response.getModule()) {
                    CityDTO cityDTO = new CityDTO();
                    cityDTO.setCityId(regionDTOx.getCode());
                    cityDTO.setCityName(regionDTOx.getName());
                    cityDTOs.add(cityDTO);
                }

                provinceDTO.setCities(cityDTOs);


            }

            Set<AreaDTO> set = new TreeSet<AreaDTO>(areaDTOMap.values());

            List<AreaDTO> areaDTOs = new ArrayList<AreaDTO>();
            areaDTOs.addAll(set);

            return areaDTOs;
        }catch (Exception e){
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR,"查询地区列表时发生问题");
        }
    }

    private Map<String,AreaDTO> initAreaMap(){



        Map<String,AreaDTO> areaDTOMap = new TreeMap<String, AreaDTO>();

        AreaDTO huazhong = new AreaDTO("华中");
        AreaDTO huabei = new AreaDTO("华北");
        AreaDTO huadong = new AreaDTO("华东");
        AreaDTO huanan = new AreaDTO("华南");
        AreaDTO dongbei = new AreaDTO("东北");
        AreaDTO xibei = new AreaDTO("西北");
        AreaDTO xinan = new AreaDTO("西南");

        areaDTOMap.put("湖南",huazhong);
        areaDTOMap.put("湖北",huazhong);
        areaDTOMap.put("河南",huazhong);

        areaDTOMap.put("北京",huabei);
        areaDTOMap.put("天津",huabei);
        areaDTOMap.put("山东",huabei);
        areaDTOMap.put("山西",huabei);
        areaDTOMap.put("河北",huabei);
        areaDTOMap.put("内蒙古",huabei);


        areaDTOMap.put("上海",huadong);
        areaDTOMap.put("江苏",huadong);
        areaDTOMap.put("浙江",huadong);
        areaDTOMap.put("安徽",huadong);
        areaDTOMap.put("江西",huadong);

        areaDTOMap.put("广东",huanan);
        areaDTOMap.put("广西",huanan);
        areaDTOMap.put("福建",huanan);
        areaDTOMap.put("海南",huanan);


        areaDTOMap.put("陕西",xibei);
        areaDTOMap.put("新疆",xibei);
        areaDTOMap.put("甘肃",xibei);
        areaDTOMap.put("宁夏",xibei);
        areaDTOMap.put("青海",xibei);


        areaDTOMap.put("辽宁",dongbei);
        areaDTOMap.put("吉林",dongbei);
        areaDTOMap.put("黑龙江",dongbei);

        areaDTOMap.put("重庆",xinan);
        areaDTOMap.put("云南",xinan);
        areaDTOMap.put("贵州",xinan);
        areaDTOMap.put("西藏",xinan);
        areaDTOMap.put("四川",xinan);

        return areaDTOMap;
    }
}
