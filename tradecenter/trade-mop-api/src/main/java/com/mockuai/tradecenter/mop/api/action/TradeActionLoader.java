package com.mockuai.tradecenter.mop.api.action;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.Action;
import com.mockuai.mop.common.service.action.ActionLoader;
import com.mockuai.mop.common.service.action.Context;
import com.mockuai.tradecenter.common.api.TradeService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Created by zengzhangqiang on 4/27/15.
 */
public class TradeActionLoader implements ActionLoader{
    private TradeService tradeService;

    @Override
    public void init(Context context) {
        RegistryConfig registryConfig = (RegistryConfig)context.getAttribute("registry_config");

        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("trade-mop-api");

        //注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接
        // 引用远程服务
        //此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        ReferenceConfig reference = new ReferenceConfig();
        reference.setApplication(application);
        reference.setRegistry(registryConfig); // 多个注册中心可以用setRegistries()
        reference.setTimeout(10000);
        reference.setInterface(TradeService.class);
        reference.setCheck(false);
//        reference.setVersion(serviceVersion);
        tradeService = (TradeService)reference.get();
    }

    @Override
    public List<Action> load() {

        List<Action> actionList = new ArrayList<Action>();
//        FileInputStream fis = null;
//        try{
//            fis = new FileInputStream(actionConfPath);
//            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
//            String data = br.readLine();
//            while(StringUtils.isBlank(data) == false){
//                String actionClass = data;
//                BaseAction action = this.loadAction(actionClass);
//                if(action != null){
//                    actionList.add(action);
//                }
//                data = br.readLine();
//            }
//        }catch(Exception e){
//            //TODO error handle
//            e.printStackTrace();
//        }finally {
//            if(fis != null){
//                try{
//                    fis.close();
//                }catch(Exception e){
//                    //TODO error handle
//                }
//            }
//        }
        ServiceLoader<BaseAction> serviceLoader = ServiceLoader.load(BaseAction.class);
        for(BaseAction action: serviceLoader){
            if(action != null){
                action.setTradeService(tradeService);
                actionList.add(action);
            }
        }
        return actionList;
    }

    private BaseAction loadAction(String actionClass){
        try{
            Class baseActionClass = Class.forName(actionClass);
            BaseAction action = (BaseAction)baseActionClass.newInstance();
            action.setTradeService(tradeService);
            return action;
        }catch(Throwable t){
            //TODO error handle
            t.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args){
        TradeActionLoader tal = new TradeActionLoader();
        List<Action> actions = tal.load();
        System.out.println("size="+actions.size());
    }
    
    public ActionAuthLevel getAuthLevel() {
		// TODO Auto-generated method stub
		return ActionAuthLevel.AUTH_LOGIN;
	}

	public HttpMethodLimit getMethodLimit() {
		return HttpMethodLimit.NO_LIMIT;
	}
}
