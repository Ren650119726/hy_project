package com.mockuai.appcenter.web.action;

import com.mockuai.appcenter.client.AppClient;
import com.mockuai.appcenter.common.api.Response;
import com.mockuai.appcenter.common.constant.BizPropertyKey;
import com.mockuai.appcenter.common.constant.BizTypeEnum;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoQTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.appcenter.core.constant.UserLevel;
import com.mockuai.appcenter.core.domain.BizInfoDO;
import com.mockuai.appcenter.core.domain.UserDO;
import com.mockuai.appcenter.core.manager.AppInfoManager;
import com.mockuai.appcenter.core.manager.AppPropertyManager;
import com.mockuai.appcenter.core.manager.BizInfoManager;
import com.mockuai.appcenter.core.manager.BizPropertyManager;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 9/1/15.
 */
@Controller
public class LoginAction {
    public static final String USER_INFO = "user_info";

    private Map<String, UserDO> userMap = new HashMap<String, UserDO>();
    public LoginAction(){
        userMap.put("财神", new UserDO("财神", "047b166fc5ee84c715649882cdf743b1", UserLevel.ADMIN.getValue()));
        userMap.put("manager", new UserDO("manager", "3089e64fd39c53ade495303a72509a39", UserLevel.MANAGER.getValue()));
        userMap.put("developer", new UserDO("developer", "09c09c968484f10838d2eaca35370ae0", UserLevel.DEVELOPER.getValue()));
    }

    /**
     *
     * @throws Exception
     */
    @RequestMapping(value = "/login.html", produces = "application/json; charset=utf-8")
    public ModelAndView loginPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("screen/login");
    }

    /**
     *
     * @throws Exception
     */
    @RequestMapping(value = "/login.do", produces = "application/json; charset=utf-8")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        if(verifyUser(name, password) == true){
            HttpSession session = request.getSession();
            session.setAttribute(LoginAction.USER_INFO, userMap.get(name));
        }
        return new ModelAndView("redirect:/biz/manager.html");
    }

    /**
     *
     * @throws Exception
     */
    @RequestMapping(value = "/logout.do", produces = "application/json; charset=utf-8")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        if(session != null){
            session.removeAttribute(LoginAction.USER_INFO);
        }
        return new ModelAndView("redirect:/login.html");
    }

    private boolean verifyUser(String name, String password) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(password)) {
            return false;
        }

        if(userMap.containsKey(name)){
            if(DigestUtils.md5Hex(password).equals(userMap.get(name).getPassword())){
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args){
    }

}
