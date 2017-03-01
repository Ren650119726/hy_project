package com.mockuai.messagecenter.mop.api.action;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.mockuai.messagecenter.common.api.MessageDispatchService;
import com.mockuai.mop.common.service.action.Action;
import com.mockuai.mop.common.service.action.ActionLoader;
import com.mockuai.mop.common.service.action.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class MessageActionLoader implements ActionLoader{
    private MessageDispatchService messageDispatchService;

    public void init(Context context) {
        RegistryConfig registryConfig = (RegistryConfig)context.getAttribute("registry_config");

        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("message-mop-api");

        //注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接
        // 引用远程服务
        //此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        ReferenceConfig reference = new ReferenceConfig();
        reference.setApplication(application);
        reference.setRegistry(registryConfig); // 多个注册中心可以用setRegistries()
        reference.setInterface(MessageDispatchService.class);
        reference.setCheck(false);
        messageDispatchService = (MessageDispatchService)reference.get();
    }

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
                action.setMessageDispatchService(messageDispatchService);
                actionList.add(action);
            }
        }
        return actionList;
    }

    private BaseAction loadAction(String actionClass){
        try{
            Class baseActionClass = Class.forName(actionClass);
            BaseAction action = (BaseAction)baseActionClass.newInstance();
            action.setMessageDispatchService(messageDispatchService);
            return action;
        }catch(Throwable t){
            //TODO error handle
            t.printStackTrace();
        }

        return null;
    }

   

    public MessageDispatchService getMessageDispatchService() {
		return messageDispatchService;
	}

	public void setMessageDispatchService(
			MessageDispatchService messageDispatchService) {
		this.messageDispatchService = messageDispatchService;
	}

	public static void main(String[] args){
        MessageActionLoader tal = new MessageActionLoader();
        List<Action> actions = tal.load();
        System.out.println("size="+actions.size());
    }
}