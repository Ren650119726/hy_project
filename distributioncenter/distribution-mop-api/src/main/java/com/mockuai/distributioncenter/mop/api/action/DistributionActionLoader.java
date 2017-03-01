package com.mockuai.distributioncenter.mop.api.action;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.mockuai.distributioncenter.common.api.DistributionService;
import com.mockuai.mop.common.service.action.Action;
import com.mockuai.mop.common.service.action.ActionLoader;
import com.mockuai.mop.common.service.action.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Created by duke on 15/10/28.
 */
public class DistributionActionLoader implements ActionLoader {
    private DistributionService distributionService;

    public void init(Context context) {
        RegistryConfig registryConfig = (RegistryConfig) context.getAttribute("registry_config");

        ApplicationConfig application = new ApplicationConfig();
        application.setName("distribution-mop-api");

        ReferenceConfig reference = new ReferenceConfig();
        reference.setApplication(application);
        reference.setRegistry(registryConfig);
        reference.setRetries(0);
        reference.setInterface(DistributionService.class);
        reference.setCheck(Boolean.valueOf(false));

        this.distributionService = ((DistributionService) reference.get());
    }

    public List<Action> load() {
        List actionList = new ArrayList();

        ServiceLoader<BaseAction> serviceLoader = ServiceLoader.load(BaseAction.class);
        for (BaseAction action : serviceLoader) {
            if (action != null) {
                action.setDistributionService(this.distributionService);
                actionList.add(action);
            }
        }
        return actionList;
    }
}
