package com.mockuai.rainbowcenter.core.manager;

import com.mockuai.rainbowcenter.common.dto.VersionDeployDTO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;

import java.util.List;

/**
 * Created by lizg on 2016/12/20.
 */

public interface VersionDeployManager {


    List<VersionDeployDTO> getVersionDeploy() throws RainbowException;


}
