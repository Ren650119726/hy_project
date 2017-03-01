package com.mockuai.itemcenter.mop.api.action;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.mop.common.service.action.Action;
import com.mockuai.mop.common.service.action.ActionLoader;
import com.mockuai.mop.common.service.action.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Created by zengzhangqiang on 4/27/15.
 */
public class ItemActionLoader implements ActionLoader{
    private ItemService itemService;

    public void init(Context context) {
        RegistryConfig registryConfig = (RegistryConfig)context.getAttribute("registry_config");

        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("item-mop-api");

        //注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接
        // 引用远程服务
        //此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        ReferenceConfig reference = new ReferenceConfig();
        reference.setApplication(application);
        reference.setRegistry(registryConfig); // 多个注册中心可以用setRegistries()
        reference.setInterface(ItemService.class);
        reference.setCheck(false);
//        reference.setVersion("2.0.0");
        itemService = (ItemService)reference.get();
    }

    public List<Action> load() {

        List<Action> actionList = new ArrayList<Action>();
        ServiceLoader<BaseAction> serviceLoader = ServiceLoader.load(BaseAction.class);
        for(BaseAction action: serviceLoader){
            if(action != null){
                action.setItemService(itemService);
                actionList.add(action);
            }
        }
        return actionList;
    }

    private BaseAction loadAction(String actionClass){
        try{
            Class baseActionClass = Class.forName(actionClass);
            BaseAction action = (BaseAction)baseActionClass.newInstance();
            action.setItemService(itemService);
            return action;
        }catch(Throwable t){
            //TODO error handle
            t.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args){
        ItemActionLoader tal = new ItemActionLoader();
        List<Action> actions = tal.load();
        System.out.println("size="+actions.size());
    }
}
