package com.mockuai.marketingcenter.mop.api.action;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.mockuai.marketingcenter.common.api.MarketingService;
import com.mockuai.mop.common.service.action.Action;
import com.mockuai.mop.common.service.action.ActionLoader;
import com.mockuai.mop.common.service.action.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class MarketingActionLoader implements ActionLoader {
    private MarketingService marketingService;

    public static void main(String[] args) {
        MarketingActionLoader tal = new MarketingActionLoader();
        List actions = tal.load();
        System.out.println("size=" + actions.size());
    }

    public void init(Context context) {
        RegistryConfig registryConfig = (RegistryConfig) context.getAttribute("registry_config");

        ApplicationConfig application = new ApplicationConfig();
        application.setName("marketing-mop-api");

        ReferenceConfig reference = new ReferenceConfig();
        reference.setApplication(application);
        reference.setRegistry(registryConfig);
        reference.setRetries(0);
        reference.setInterface(MarketingService.class);
        reference.setCheck(Boolean.valueOf(false));

        this.marketingService = ((MarketingService) reference.get());
    }

    public List<Action> load() {
        List actionList = new ArrayList();

        ServiceLoader<BaseAction> serviceLoader = ServiceLoader.load(BaseAction.class);
        for (BaseAction action : serviceLoader) {
            if (action != null) {
                action.setMarketingService(this.marketingService);
                actionList.add(action);
            }
        }
        return actionList;
    }

    private BaseAction loadAction(String actionClass) {
        try {
            Class baseActionClass = Class.forName(actionClass);
            BaseAction action = (BaseAction) baseActionClass.newInstance();
            action.setMarketingService(this.marketingService);
            return action;
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return null;
    }
}