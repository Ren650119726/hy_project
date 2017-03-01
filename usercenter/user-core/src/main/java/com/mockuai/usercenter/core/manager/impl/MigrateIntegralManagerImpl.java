//package com.mockuai.usercenter.core.manager.impl;
//
//import com.mockuai.usercenter.common.constant.ResponseCode;
//import com.mockuai.usercenter.core.exception.UserException;
//import com.mockuai.usercenter.core.external.hjsj.CRMClient;
//import com.mockuai.usercenter.core.employee.MigrateIntegralManager;
//import org.dom4j.Document;
//import org.dom4j.DocumentHelper;
//import org.dom4j.Element;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.Iterator;
//
///**
// * Created by duke on 15/11/18.
// */
//@Service
//public class MigrateIntegralManagerImpl implements MigrateIntegralManager {
//    private final static Logger log = LoggerFactory.getLogger(MigrateIntegralManagerImpl.class);
//
//    @Resource
//    private CRMClient crmClient;
//
//    @Override
//    public Long getIntergalByMobile(String mobile) throws UserException {
//        try {
//            String authCode = crmClient.getAuthCode();
//            if (crmClient.isMemberExists(mobile, authCode)) {
//                String memberId = getMemberId(mobile, authCode);
//                Long integral = crmClient.getPoint(memberId, authCode);
//                crmClient.newApp(mobile, authCode);
//                return integral;
//            } else {
//                // 如果会员不存在，则新增会员
//                crmClient.createMember(mobile, authCode);
//                // 如果没有查到，则返回积分为0
//                return 0L;
//            }
//        } catch (Exception e) {
//            log.error("migrate integral error, mobile: {}, errMsg: {}", mobile, e.getMessage());
//            throw new UserException(ResponseCode.B_MIGRATE_INTEGRAL_ERROR);
//        }
//    }
//
//    private String getMemberId(String mobile, String authCode) throws Exception {
//        String xmlString = crmClient.getMember(mobile, authCode);
//        Document document = DocumentHelper.parseText(xmlString);
//        Element root = document.getRootElement();
//        if (root == null) {
//            log.error("parse returned result error, result: {}", xmlString);
//            throw new Exception("parse returned result error");
//        }
//        Iterator<Element> rowIter = root.elementIterator();
//        if (rowIter.hasNext()) {
//            Element rowElement = rowIter.next();
//            Element memberIdElement = rowElement.element("memberid");
//            if (memberIdElement == null) {
//                log.error("parse returned result error, result: {}", xmlString);
//                throw new Exception("parse returned result error");
//            } else {
//                return memberIdElement.getText();
//            }
//        } else {
//            log.error("parse returned result error, result: {}", xmlString);
//            throw new Exception("parse returned result error");
//        }
//    }
//}
