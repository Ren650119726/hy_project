package com.mockuai.rainbowcenter.core.service.action.version;

import com.mockuai.rainbowcenter.common.api.RainbowResponse;
import com.mockuai.rainbowcenter.common.constant.ActionEnum;
import com.mockuai.rainbowcenter.common.dto.VersionDeployDTO;
import com.mockuai.rainbowcenter.common.util.JsonUtil;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.manager.VersionDeployManager;
import com.mockuai.rainbowcenter.core.service.RequestContext;
import com.mockuai.rainbowcenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lizg on 2016/12/20.
 */

@Controller
public class VersionDeployAction implements Action {

    @Resource
    private VersionDeployManager versionDeployManager;

    private static final Logger log = LoggerFactory.getLogger(VersionDeployAction.class);


    @Override
    public RainbowResponse execute(RequestContext context) {

        List<VersionDeployDTO> versionDeployDTOS = null;

        String deployDTOMaps = null;

        try {
            versionDeployDTOS = versionDeployManager.getVersionDeploy();

            Map<String, VersionDeployDTO> deployDTOMap = new HashMap<>();

            for (VersionDeployDTO versionDeployDTO : versionDeployDTOS) {

                //处理不需要返回的元素
                versionDeployDTO.setId(Long.getLong(""));
                versionDeployDTO.setDeleteMark(null);
                versionDeployDTO.setGmtCreated(null);
                versionDeployDTO.setGmtModified(null);

                if (versionDeployDTO.getType() == 1) {
                    deployDTOMap.put("android", versionDeployDTO);
                }

                if (versionDeployDTO.getType() == 2) {
                    deployDTOMap.put("ios", versionDeployDTO);
                }

            }

            deployDTOMaps = JsonUtil.toJson(deployDTOMap);

            log.info("[{}] deployDTOMap:{}", deployDTOMaps);
        } catch (RainbowException e) {
            return new RainbowResponse(e.getResponseCode(), e.getMessage());
        }

        return new RainbowResponse(deployDTOMaps);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_VERSION_DEPLOY.getActionName();
    }
}
