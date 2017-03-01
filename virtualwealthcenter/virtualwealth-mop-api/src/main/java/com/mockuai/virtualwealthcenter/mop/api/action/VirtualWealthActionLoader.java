package com.mockuai.virtualwealthcenter.mop.api.action;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.mockuai.mop.common.service.action.Action;
import com.mockuai.mop.common.service.action.ActionLoader;
import com.mockuai.mop.common.service.action.Context;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthService;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class VirtualWealthActionLoader implements ActionLoader {
    public static VirtualWealthService virtualWealthService;

    public static void main(String[] args) {
        VirtualWealthActionLoader tal = new VirtualWealthActionLoader();
//        List actions = tal.load();
//        System.out.println("size=" + actions.size());
        
        BaseAction baseAction =  tal.loadAction("com.mockuai.virtualwealthcenter.mop.api.action.UserAuthonMopAction");
        baseAction.getVirtualWealthService();
    }

    public void init(Context context) {
        RegistryConfig registryConfig = (RegistryConfig) context.getAttribute("registry_config");

        ApplicationConfig application = new ApplicationConfig();
        application.setName("marketing-mop-api");

        ReferenceConfig reference = new ReferenceConfig();
        reference.setApplication(application);
        reference.setRegistry(registryConfig);
        reference.setRetries(0);
        reference.setInterface(VirtualWealthService.class);
        reference.setCheck(Boolean.valueOf(false));

        this.virtualWealthService = ((VirtualWealthService) reference.get());
    }

    public List<Action> load() {
        List actionList = new ArrayList();

        ServiceLoader<BaseAction> serviceLoader = ServiceLoader.load(BaseAction.class);
        for (BaseAction action : serviceLoader) {
            if (action != null) {
                action.setVirtualWealthService(this.virtualWealthService);
                actionList.add(action);
            }
        }
        return actionList;
    }

    public BaseAction loadAction(String actionClass) {
        try {
            Class baseActionClass = Class.forName(actionClass);
            BaseAction action = (BaseAction) baseActionClass.newInstance();
            action.setVirtualWealthService(this.virtualWealthService);
            return action;
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return null;
    }
}