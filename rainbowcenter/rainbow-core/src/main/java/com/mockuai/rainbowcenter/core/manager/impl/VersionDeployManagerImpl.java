package com.mockuai.rainbowcenter.core.manager.impl;
import com.mockuai.rainbowcenter.common.dto.VersionDeployDTO;
import com.mockuai.rainbowcenter.core.dao.VersionDeployDAO;
import com.mockuai.rainbowcenter.core.domain.VersionDeployDO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.manager.VersionDeployManager;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizg on 2016/12/20.
 */

@Service
public class VersionDeployManagerImpl implements VersionDeployManager {

    @Resource
    private VersionDeployDAO versionDeployDAO;

    @Override
    public List<VersionDeployDTO> getVersionDeploy() throws RainbowException {



        List<VersionDeployDO> versionDeployDOS = versionDeployDAO.getVersionDeploy();


        List<VersionDeployDTO> versionDeployDTOArrayList = new ArrayList<>();

        for (VersionDeployDO versionDeployDO : versionDeployDOS) {
            VersionDeployDTO versionDeployDTO = new VersionDeployDTO();
            BeanUtils.copyProperties(versionDeployDO, versionDeployDTO);
            versionDeployDTOArrayList.add(versionDeployDTO);
        }

        return versionDeployDTOArrayList;
    }
}
