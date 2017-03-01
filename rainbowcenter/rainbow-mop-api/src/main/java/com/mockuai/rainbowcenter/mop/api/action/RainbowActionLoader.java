package com.mockuai.rainbowcenter.mop.api.action;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.mockuai.mop.common.service.action.Action;
import com.mockuai.mop.common.service.action.ActionLoader;
import com.mockuai.mop.common.service.action.Context;
import com.mockuai.rainbowcenter.common.api.RainbowService;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Created by lizg on 2016/7/20.
 */
public class RainbowActionLoader implements ActionLoader {

    private RainbowService rainbowService;
    @Override
    public void init(Context context) {
        RegistryConfig registryConfig = (RegistryConfig) context.getAttribute("registry_config");

        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("rainbow-mop-api");


        //注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接
        // 引用远程服务
        //此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        ReferenceConfig reference = new ReferenceConfig();
        reference.setApplication(application);
        reference.setRegistry(registryConfig);
        reference.setRetries(0);
        reference.setInterface(RainbowService.class);
        reference.setCheck(Boolean.valueOf(false));

        this.rainbowService = ((RainbowService) reference.get());
    }

    @Override
    public List<Action> load() {
        List actionList = new ArrayList();

        ServiceLoader<BaseAction> serviceLoader = ServiceLoader.load(BaseAction.class);
        for (BaseAction action : serviceLoader) {
            if (action != null) {
                action.setRainbowService(this.rainbowService);
                actionList.add(action);
            }
        }
        return actionList;
    }
}
