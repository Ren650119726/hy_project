package com.mockuai.headsinglecenter.mop.api.action;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.mockuai.headsinglecenter.common.api.HeadSingleService;
import com.mockuai.mop.common.service.action.Action;
import com.mockuai.mop.common.service.action.ActionLoader;
import com.mockuai.mop.common.service.action.Context;

public class HeadSingleActionLoader implements ActionLoader {
    @SuppressWarnings("rawtypes")
	private HeadSingleService headSingleService;

    @SuppressWarnings("rawtypes")
	public static void main(String[] args) {
        HeadSingleActionLoader tal = new HeadSingleActionLoader();
        List actions = tal.load();
        System.out.println("size=" + actions.size());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void init(Context context) {
        RegistryConfig registryConfig = (RegistryConfig) context.getAttribute("registry_config");

        ApplicationConfig application = new ApplicationConfig();
        application.setName("headsingle-mop-api");

        ReferenceConfig reference = new ReferenceConfig();
        reference.setApplication(application);
        reference.setRegistry(registryConfig);
        reference.setRetries(0);
        reference.setInterface(HeadSingleService.class);
        reference.setCheck(Boolean.valueOf(false));

        this.headSingleService = ((HeadSingleService) reference.get());
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Action> load() {
        List actionList = new ArrayList();

        ServiceLoader<BaseAction> serviceLoader = ServiceLoader.load(BaseAction.class);
        for (BaseAction action : serviceLoader) {
            if (action != null) {
                action.setHeadSingleService(this.headSingleService);
                actionList.add(action);
            }
        }
        return actionList;
    }

    @SuppressWarnings({ "unused", "rawtypes" })
	private BaseAction loadAction(String actionClass) {
        try {
            Class baseActionClass = Class.forName(actionClass);
            BaseAction action = (BaseAction) baseActionClass.newInstance();
            action.setHeadSingleService(this.headSingleService);
            return action;
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return null;
    }
}