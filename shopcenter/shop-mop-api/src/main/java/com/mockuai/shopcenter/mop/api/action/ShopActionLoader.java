package com.mockuai.shopcenter.mop.api.action;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.mockuai.mop.common.service.action.Action;
import com.mockuai.mop.common.service.action.ActionLoader;
import com.mockuai.mop.common.service.action.Context;
import com.mockuai.shopcenter.api.ShopService;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Created by ziqi.
 */
public class ShopActionLoader implements ActionLoader{

    private ShopService shopService;

    public void init(Context context) {
        RegistryConfig registryConfig = (RegistryConfig)context.getAttribute("registry_config");
        ApplicationConfig application = new ApplicationConfig();
        application.setName("shop-mop-api");
        ReferenceConfig reference = new ReferenceConfig();
        reference.setApplication(application);
        reference.setRegistry(registryConfig);
        reference.setInterface(ShopService.class);
        reference.setCheck(false);
        shopService = (ShopService)reference.get();
    }

    public List<Action> load() {

        List<Action> actionList = new ArrayList<Action>();
        ServiceLoader<BaseAction> serviceLoader = ServiceLoader.load(BaseAction.class);
        for(BaseAction action: serviceLoader){
            if(action != null){
                action.setShopService(shopService);
                actionList.add(action);
            }
        }
        return actionList;
    }

    private BaseAction loadAction(String actionClass){
        try{
            Class baseActionClass = Class.forName(actionClass);
            BaseAction action = (BaseAction)baseActionClass.newInstance();
            action.setShopService(shopService);
            return action;
        }catch(Throwable t){
            t.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args){
        ShopActionLoader tal = new ShopActionLoader();
        List<Action> actions = tal.load();
        System.out.println("size="+actions.size());
    }
}
