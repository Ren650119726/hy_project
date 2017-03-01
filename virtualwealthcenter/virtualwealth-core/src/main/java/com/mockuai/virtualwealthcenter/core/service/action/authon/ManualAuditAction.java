package com.mockuai.virtualwealthcenter.core.service.action.authon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.usercenter.client.UserClient;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.domain.dto.boss.BossUserAuthonDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.PageQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.UserAuthonQTO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.UserAuthonAppManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.service.action.TransAction;

/**
 * 查询所有审核 列表 操作
 * @author Administrator
 *
 */
@Service
public class ManualAuditAction extends TransAction {
	private static final Logger log = LoggerFactory.getLogger(UserAutonAppAction.class);
	 
	@Autowired
	private UserAuthonAppManager userAuthonAppManager;
	
	@Autowired
	private UserClient userClient;
	
	
	private UserAuthonQTO userAuthonQTO;
	private static Map<String,String> cacheAuthon = new HashMap<String, String>();
	
	//private static Map<Object,Object> cacheAuthonMobile = new HashMap<Object, Object>();
	

	   
	@Override
	protected VirtualWealthResponse doTransaction(RequestContext context)
			throws VirtualWealthException {
		
//        String authon_realname = (String)context.getRequest().getParam("bank_realname");
//        String authon_personalid = (String)context.getRequest().getParam("bank_personal_id");
//        String authon_mobile = (String)context.getRequest().getParam("bank_mobile");
//        String picture_front = (String)context.getRequest().getParam("picture_front");
//        String picture_back = (String)context.getRequest().getParam("picture_back"); 
//        String  id =  (String)context.getRequest().getParam("id");
//        Integer authonStatus = Integer.valueOf((String)context.getRequest().getParam("authon_status"));
		VirtualWealthResponse response ;
		userAuthonQTO = (UserAuthonQTO) context.getRequest().getObject("userAuthonQTO");
		Integer offset = userAuthonQTO.getOffset();
		if (offset == null) {
            offset = 0;
        }
		Integer count = userAuthonQTO.getCount();
        if (count == null || count > 200) {
            count = 20;
        }
        userAuthonQTO.setOffset(offset);
        userAuthonQTO.setCount(count);
        String authonMobile = userAuthonQTO.getAuthonMobile();
        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
        //如果前端传入用户名（手机号）则从user表中取userid
        if(authonMobile!=null){
        	com.mockuai.usercenter.common.api.Response<UserDTO> userclient = userClient.getUserByMobile(authonMobile, appInfo.getAppKey());
        	Long userId = null;
        	if(userclient.getModule()!= null){
      		  userId = userclient.getModule().getId();
          }else{
        	  //如果通过手机号没有对应的用户,则返回0条
        	  response =   new VirtualWealthResponse(new UserAuthonQTO());
              response.setTotalCount(0);
              return response; 
          }
        	
        	//手机号不传入实名表中
        	userAuthonQTO.setAuthonMobile(null);
        	userAuthonQTO.setUserId(userId);
        }
        List<BossUserAuthonDTO> userAuthonDTOs =  userAuthonAppManager.selectUserAuthonAll(userAuthonQTO); 
        
        for(BossUserAuthonDTO user:userAuthonDTOs){
        	Long userId = user.getUser_id();
        	
        	com.mockuai.usercenter.common.api.Response<UserDTO> userclient = userClient.getUserById(userId, appInfo.getAppKey());
        	String authon_mobile=null; 
        	if(userclient.getModule()!= null){
        		  authon_mobile = userclient.getModule().getMobile();
            }
        	user.setAuthon_mobile(authon_mobile);
        	if(user.getPicture_front()!=null&&user.getPicture_back()!=null){
        		String front = user.getPicture_front();
				if(front.contains("@")){
					String picturefront =front.substring(0,front.lastIndexOf("@"));
					user.setPicture_front(picturefront);
				}
				String back = user.getPicture_back();
				if(back.contains("@")){
					String pictureback = back.substring(0,back.lastIndexOf("@"));
					user.setPicture_back(pictureback);
				}
				log.info("身份证图片："+front+"-------"+back);
        	}
        }
        
        Long totalCount= userAuthonAppManager.selectUserAuthonAllCount(userAuthonQTO);
        if(null==userAuthonDTOs){
        	response =   new VirtualWealthResponse(new UserAuthonQTO());
        	totalCount=0l;
        }
        response =   new VirtualWealthResponse(userAuthonDTOs);
        response.setTotalCount(totalCount);
        return response; 
       
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ActionEnum.USER_AUTHON_ALL.getActionName();
	}

}
