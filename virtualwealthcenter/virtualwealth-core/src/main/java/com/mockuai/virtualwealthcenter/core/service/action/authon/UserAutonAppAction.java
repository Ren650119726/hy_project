package com.mockuai.virtualwealthcenter.core.service.action.authon;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanshu.imagecenter.client.ImageClient;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.util.JsonUtil;
import com.mockuai.distributioncenter.client.DistributionClient;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.imagecenter.common.domain.dto.ImageDTO;
import com.mockuai.usercenter.client.UserClient;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopUserAuthonAppDTO;
import com.mockuai.virtualwealthcenter.core.domain.BankInfoAppDO;
import com.mockuai.virtualwealthcenter.core.domain.UserAuthonAppDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.BankInfoAppManager;
import com.mockuai.virtualwealthcenter.core.manager.CacheManager;
import com.mockuai.virtualwealthcenter.core.manager.UserAuthonAppManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.service.action.TransAction;
import com.mockuai.virtualwealthcenter.core.util.IdCardCheckUtil;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthUtils;
/**
 * 前端传入待审核信息
 * 审核失败后重新传入审核信息
 * @author Administrator
 *
 */
@Service
public class UserAutonAppAction extends TransAction{
	private static final Logger log = LoggerFactory.getLogger(UserAutonAppAction.class);
 
	@Autowired
	private UserAuthonAppManager userAuthonAppManager;
	
	@Autowired
	private BankInfoAppManager bankInfoAppManager;
	
	@Autowired
	private UserClient userClient;
	
	@Autowired
	private DistributionClient distributionClient;
	
	@Autowired
	private ImageClient imageClient;
	
	@Resource
    private CacheManager cacheManager;
	
	private static Map<String,String> cacheAuthon = new HashMap<String, String>();
	
	//private static Map<Object,Object> cacheAuthonMobile = new HashMap<Object, Object>();
	
//	static{
//		//该银行卡不支持实名认证，请更换银行卡
////	    SHYH_BANK_INFO(04012900,"上海银行"),
//		cacheAuthon.put("上海银行", "04012900");
////	    BJYH_BANK_INFO(04031000,"北京银行"),
//		cacheAuthon.put("北京银行", "04031000");
////	    YCYH_BANK_INFO(01000000,"邮储银行"),
//		cacheAuthon.put("邮储银行", "01000000");
////	    JTYH_BANK_INFO(03010000,"交通银行"),
//		cacheAuthon.put("交通银行", "03010000");
////	    PFYH_BANK_INFO(03100000,"浦发银行"),
//		cacheAuthon.put("浦发银行", "03100000");
////	    GDYH_BANK_INFO(03030000,"光大银行"),
//		cacheAuthon.put("中国光大银行", "03030000");
////	    XYYH_BANK_INFO(03090000,"兴业银行"),
//		cacheAuthon.put("兴业银行", "03090000");
////	    PAYH_BANK_INFO(03070000,"平安银行"),
//		cacheAuthon.put("平安银行", "03070000");
////	    GFYH_BANK_INFO(03060000,"广发银行"),
//		cacheAuthon.put("广发银行", "03060000");
////	    MSYH_BANK_INFO(03050000,"民生银行"),
//		cacheAuthon.put("民生银行", "03050000");
////	    HXYH_BANK_INFO(03040000,"华夏银行"),
//		cacheAuthon.put("华夏银行", "03040000");
////	    ZXYH_BANK_INFO(03020000,"中信银行"),
//		cacheAuthon.put("中信银行", "03020000");
////	    ZSYH_BANK_INFO(03080000,"招商银行"),
//		cacheAuthon.put("招商银行", "03080000");
////	    GSYH_BANK_INFO(01020000,"工商银行"),
//		cacheAuthon.put("中国工商银行", "01020000");
////	    ZGYH_BANK_INFO(01040000,"中国银行"),
//		cacheAuthon.put("中国银行", "01040000");
////	    JSYH_BANK_INFO(01050000,"建设银行"),
//		cacheAuthon.put("中国建设银行", "01050000");
////	    NYYH_BANK_INFO(01030000,"农业银行"),
//		cacheAuthon.put("中国农业银行", "01030000");
//	}
//	
//	{
//		userAuthonTimer();
//	}
//	
//	public void userAuthonTimer() {  
//        TimerTask task = new TimerTask() {  
//            @Override  
//            public void run() {
//            	
//            	try {
//            		log.info("userauthon_error_key开始执行");
//            		if(cacheManager.get("userauthon_error_key") != null){
//            			log.info("userauthon_error_key 不等于null");
//               		 Map<Object,Object> cachemobile = (Map<Object, Object>) cacheManager.get("userauthon_error_key");
//               		 if(cachemobile != null){
//               			 for (Object v : cachemobile.keySet()) {
//           				   if(v != null){
//           					   log.info("需要重置和清空的userId"+v);
//           					    try {
//           					    	cacheManager.remove(String.valueOf(v));
//								} catch (Exception e) {
//									log.info("清空的userId不存在");
//								}
//           				   }
//           				 }
//               		 }
//               	}
//				} catch (Exception e) {
//					log.info("实名认证定时任务报错");
//				}
//            	log.info("userauthon_error_key执行结束");
//            }  
//        };  
//  
//        Calendar calendar = Calendar.getInstance();  
//        int year = calendar.get(Calendar.YEAR);  
//        int month = calendar.get(Calendar.MONTH);  
//        int day = calendar.get(Calendar.DAY_OF_MONTH);  
//        
//        /*** 定制每日00：00：00执行方法 ***/  
//        calendar.set(year, month, day, 24, 00, 00);  
//        Date date = calendar.getTime();  
//        Timer timer = new Timer(); 
//        long period = 1000*60*60*24; 
//        timer.schedule(task,date,period);  
//    }  
//	
	@Override
    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {

        Long userId = (Long) context.getRequest().getParam("userId");
        
        VirtualWealthPreconditions.checkNotNull(userId, "userId");
        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");

        //发卡银行
        //String authon_bankname = (String)context.getRequest().getParam("bank_name");
        //银行卡号
        //String authon_no = (String)context.getRequest().getParam("bank_no");
        //VirtualWealthPreconditions.checkNotNull(authon_no, "bank_no");
        //银行卡认证信息
        //String authon_text = (String)context.getRequest().getParam("bank_authon_text");
        //持卡人姓名
        String authon_realname = (String)context.getRequest().getParam("bank_realname");
        if(authon_realname==null||authon_realname.equals("")){
        	return new VirtualWealthResponse(ResponseCode.NAME_NO_INPUT);
        }
        VirtualWealthPreconditions.checkNotNull(authon_realname, "bank_realname");
        //身份证号
        String authon_personalid = (String)context.getRequest().getParam("bank_personal_id");
        authon_personalid = authon_personalid.toUpperCase();
        VirtualWealthPreconditions.checkNotNull(authon_personalid, "bank_personal_id");
        //手机号
        //String authon_mobile =(String)context.getRequest().getParam("bank_mobile"); 
        //VirtualWealthPreconditions.checkNotNull(userId, "bank_mobile");
        
        try {
        	String checkIdCard = IdCardCheckUtil.IDCardValidate(authon_personalid);
            //身份证格式不正确
            if(!checkIdCard.equals("0000")&&!checkIdCard.equals("")){
            	return new VirtualWealthResponse(ResponseCode.ERROR_IDCARD_INFO);
           }
		} catch (Exception e) {
			return new VirtualWealthResponse(ResponseCode.ERROR_IDCARD_INFO);
		}
        String pictureFront = (String)context.getRequest().getParam("picture_front");
        String pictureBack = (String)context.getRequest().getParam("picture_back");
        if(pictureFront==null||pictureFront.equals("")){
       	return new VirtualWealthResponse(ResponseCode.POSTIVE_PHOTO_OF_ID_card_DOES_NOT_EXIST);
        }
        if(pictureBack==null||pictureBack.equals("")){
        	return new VirtualWealthResponse(ResponseCode.THE_BACK_OF_ID_card_DOES_NOT_EXIST);
        }
        
       com.mockuai.usercenter.common.api.Response<UserDTO> userclient = userClient.getUserById(userId, appInfo.getAppKey());
        if(userclient.getModule() == null){
        	//该用户不存在
        	return new VirtualWealthResponse(ResponseCode.NOT_EXIST_USER);
       }
        String authon_mobile = userclient.getModule().getMobile();
        //log.info("authon_mobile:"+authon_mobile+"**************************************userid:"+userId);
       
//        

//	    log.info("实名认证参数银行卡号:"+authon_no+"持卡人姓名:"+authon_realname+"身份证号:"+authon_personalid+"手机号:"+authon_mobile);
//        String result = HttpAuthonUtil.authonClient(authon_personalid,authon_realname,authon_mobile,authon_no);
//        log.info("实名认证结果:"+result);
//        AuthonResult authonresult =  JsonUtil.parseJson(result, AuthonResult.class);
//        //实名认证
        UserAuthonAppDO userAuthonApp = new UserAuthonAppDO();
        userAuthonApp.setUserId(userId);
        userAuthonApp.setAuthonPersonalid(authon_personalid);
//        userAuthonApp.setAuthonBankname(authon_bankname);
//        userAuthonApp.setAuthonNo(authon_no);
        userAuthonApp.setAuthonMobile(authon_mobile);
        userAuthonApp.setAuthonRealname(authon_realname);
//        userAuthonApp.setAuthonText(authon_text);
        userAuthonApp.setPictureFront(pictureFront);
        userAuthonApp.setPictureBack(pictureBack);
        MopUserAuthonAppDTO user=null;
        user = userAuthonAppManager.selectMopUserAuthon(userId);
//        log.info("通过id查的：user"+user);
        Long result;
        if(user==null){
//        	log.info("进入添加方法");
        	userAuthonApp.setAuthonStatus(0);
        	result = userAuthonAppManager.addUserAuthon(userAuthonApp);
        }else if(new Integer(2).equals(user.getAuthonStatus())){
//        	log.info("进入拒绝重新提交方法");
        	userAuthonApp.setAuthonText(null);
        	userAuthonApp.setAuthonStatus(0);
        	result = userAuthonAppManager.updateUserAuton(userAuthonApp);
        }
        return VirtualWealthUtils.getSuccessResponse(true);
//        if(authonresult == null){r
//        	return new VirtualWealthResponse(ResponseCode.ERROR_NULL_AUTHON);
//        }
//        if(authonresult.getRet_code().equals("0000") || authonresult.getRet_code().equals("9926")){
//        	//认证状态(待确认0 成功1 失败2)
        	
//        }else{
//        	return new VirtualWealthResponse(ResponseCode.ERROR_AUTHON);
//        }
	}
	@Override
	public String getName() {
		return com.mockuai.virtualwealthcenter.common.constant.ActionEnum.USER_AUTHON_ADD.getActionName();
	}

}
