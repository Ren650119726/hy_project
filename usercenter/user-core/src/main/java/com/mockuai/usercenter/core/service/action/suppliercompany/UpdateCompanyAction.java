//package com.mockuai.usercenter.core.service.action.suppliercompany;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.common.action.ActionEnum;
//import com.mockuai.usercenter.common.api.UserResponse;
//import com.mockuai.usercenter.common.dto.SupplierCompanyDTO;
//import com.mockuai.usercenter.core.exception.UserException;
//import com.mockuai.usercenter.core.employee.CompanyManager;
//import com.mockuai.usercenter.core.service.RequestContext;
//import com.mockuai.usercenter.core.service.UserRequest;
//import com.mockuai.usercenter.core.service.action.Action;
//
//@Service
//public class UpdateCompanyAction implements Action {
//
//	@Resource
//	private CompanyManager companyManager;
//
//	@Override
//	public UserResponse execute(RequestContext context) throws UserException {
//
//		UserRequest userRequest = context.getRequest();
//		SupplierCompanyDTO supplierCompanyDto = (SupplierCompanyDTO) userRequest
//				.getParam("supplierCompanyDTO");
//
//		try {
//			companyManager.updateCompany(supplierCompanyDto);
//		} catch (UserException e) {
//			log.error(e.getMessage(), e);
//			return new UserResponse(e.getResponseCode(),
//					e.getMessage());
//		}
//		return new UserResponse(true);
//	}
//
//	@Override
//	public String getName() {
//
//		return ActionEnum.UPDATE_SUPPLIER_COMPANY.getActionName();
//	}
//
//}
